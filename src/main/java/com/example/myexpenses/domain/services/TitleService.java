package com.example.myexpenses.domain.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.myexpenses.common.PageableCommon;
import com.example.myexpenses.domain.enums.Type;
import com.example.myexpenses.domain.exception.ResourceBadRequestException;
import com.example.myexpenses.domain.exception.ResourceNotFoundException;
import com.example.myexpenses.domain.model.CreditCard;
import com.example.myexpenses.domain.model.CreditCardInvoice;
import com.example.myexpenses.domain.model.Title;
import com.example.myexpenses.domain.model.User;
import com.example.myexpenses.domain.repository.CreditCardInvoiceRepository;
import com.example.myexpenses.domain.repository.CreditCardRepository;
import com.example.myexpenses.domain.repository.TitleRepository;
import com.example.myexpenses.domain.repository.UserRepository;
import com.example.myexpenses.dto.title.TitleRequestDto;
import com.example.myexpenses.dto.title.TitleResponseDto;

@Service
public class TitleService implements ICRUDService<TitleRequestDto, TitleResponseDto> {

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private CreditCardInvoiceRepository creditCardInvoiceRepository;

    @Autowired
    private CreditCardInvoiceService cardInvoiceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<TitleResponseDto> getAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Title> titles = titleRepository.findByUser(user);

        return titles.stream()
                .map(title -> mapper.map(title, TitleResponseDto.class))
                .collect(Collectors.toList());
    }

    public Page<TitleResponseDto> getAllPaginated(Integer page, Integer size, Sort.Direction sort,
            String property, String initialDate, String finalDate) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date sqlDate1 = new Date();
        Date sqlDate2 = new Date();
        try {
            java.util.Date utilDate1 = inputFormatter.parse(initialDate);
            java.util.Date utilDate2 = inputFormatter.parse(finalDate);

            sqlDate1 = new Date(utilDate1.getTime());
            sqlDate2 = new Date(utilDate2.getTime());

        } catch (ParseException e) {
            System.out.println("Failed to parse date: " + e.getMessage());
        }

        Pageable pageable = PageableCommon.create(page, size, sort, property);

        return titleRepository.findAllByUserPaginatedBetween(user.getId(), pageable, sqlDate1, sqlDate2);
    }

    @Override
    public TitleResponseDto getById(Long id) {

        Optional<Title> optTitle = titleRepository.findById(id);

        if (optTitle.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o título com o id: " + id);
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!optTitle.get().getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Não foi possível encontrar o título com o id: " + id);
        }

        return mapper.map(optTitle.get(), TitleResponseDto.class);
    }

    @Override
    public List<TitleResponseDto> create(TitleRequestDto dto) {

        if (Objects.isNull(dto.getCreditCardId())) {
            return createWalletIncomeOrExpense(dto);
        } else {
            return createCreditCardExpense(dto);
        }

    }

    public List<TitleResponseDto> createWalletIncomeOrExpense(TitleRequestDto dto) {

        List<Title> titles = new ArrayList<>();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        titleValidation(dto, null, null);

        Title title = mapper.map(dto, Title.class);
        title.setUser(user);
        title.setCreatedAt(new Date());

        title = titleRepository.save(title);
        titles.add(title);

        Double newUserBalance = 0.0;

        if (dto.getType() == Type.INCOME) {
            newUserBalance = user.getUserBalance() + dto.getValue();
        } else if (dto.getType() == Type.EXPENSE) {
            newUserBalance = user.getUserBalance() - dto.getValue();
        }

        user.setUserBalance(newUserBalance);
        userRepository.save(user);

        return titles.stream()
                .map(mappedTitle -> mapper.map(mappedTitle, TitleResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<TitleResponseDto> createCreditCardExpense(TitleRequestDto dto) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CreditCard creditCard = creditCardRepository.findById(dto.getCreditCardId()).get();

        titleValidation(dto, creditCard, user);

        List<Title> titles = new ArrayList<>();
        Double remainingAmount = getRemainingAmountFromInstallments(dto);
        int installment = dto.getInstallment();
        int creditCardClosingDay = creditCard.getClosingDay();

        Instant instant = dto.getReferenceDate().toInstant();
        LocalDate titleStartDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();

        cardInvoiceService.generateInvoicesWhenCreatingTitles(titleStartDate, installment, dto.getCreditCardId());

        for (int i = 1; i <= installment; i++) {
            Title title = new Title();
            Title titleDto = mapper.map(dto, Title.class);

            Date referenceDate = titleDto.getReferenceDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(referenceDate);
            if (i != 1) {
                calendar.add(Calendar.MONTH, i - 1);
                referenceDate = calendar.getTime();
            }

            Instant instantReferenceDate = referenceDate.toInstant();
            LocalDate localReferenceDate = instantReferenceDate.atZone(ZoneId.systemDefault()).toLocalDate();

            if (localReferenceDate.getDayOfMonth() >= creditCardClosingDay) {
                localReferenceDate = localReferenceDate.plusMonths(1);
            }

            CreditCardInvoice invoice = creditCardInvoiceRepository
                    .findByCreditCardAndDueDate(creditCard, localReferenceDate.withDayOfMonth(creditCard.getDueDay()));

            Double formattedInstallmentValue = Math.round((dto.getValue() / installment) * 100.0) / 100.0;

            if (remainingAmount > 0) {
                formattedInstallmentValue += 0.01;
                remainingAmount -= 0.01;
            }

            String titleDescription = installment > 1
                    ? titleDto.getDescription() + " (" + i + ")"
                    : titleDto.getDescription();

            Date referenceDateForTitle = dto.getReferenceDate();
            Calendar calendarForTitle = Calendar.getInstance();
            calendarForTitle.setTime(referenceDateForTitle);
            calendarForTitle.add(Calendar.MONTH, i - 1); // Add 1 month to the reference date
            referenceDateForTitle = calendarForTitle.getTime();

            title.setDescription(titleDescription);
            title.setCreatedAt(new Date());
            title.setValue(formattedInstallmentValue);
            title.setUser(user);
            title.setInvoice(invoice);
            title.setCostCenter(titleDto.getCostCenter());
            title.setType(titleDto.getType());
            title.setReferenceDate(referenceDateForTitle);
            title.setCreditCard(creditCard);
            title.setNotes(titleDto.getNotes());

            title = titleRepository.save(title);
            titles.add(title);
        }

        creditCardService.updateCreditCardLimitWhenCreatingTitles(creditCard);

        return titles.stream()
                .map(mappedTitle -> mapper.map(mappedTitle, TitleResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TitleResponseDto update(Long id, TitleRequestDto dto) {

        TitleResponseDto titleDatabase = getById(id);

        titleValidation(dto, null, null);

        Title title = mapper.map(dto, Title.class);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        title.setUser(user);
        title.setInativeAt(titleDatabase.getInativeAt());
        title.setId(id);
        title = titleRepository.save(title);

        return mapper.map(title, TitleResponseDto.class);
    }

    @Override
    public void delete(Long id) {

        TitleResponseDto titleDto = getById(id);

        Title title = mapper.map(titleDto, Title.class);

        title.setInativeAt(new Date());

        titleRepository.save(title);
    }

    public List<TitleResponseDto> getCashFlowByDueDate(Date initialDate, Date finalDate) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = user.getId();

        List<Title> titles = titleRepository.findByReferenceDateBetweenAndUserId(initialDate, finalDate, userId);

        return titles.stream()
                .map(title -> mapper.map(title, TitleResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<TitleResponseDto> getTitlesByInvoiceDueDate(Date initialDate, Date finalDate) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = user.getId();

        List<Title> titles = titleRepository.findByInvoiceDueDateBetweenAndUserId(initialDate, finalDate, userId);

        return titles.stream()
                .map(title -> mapper.map(title, TitleResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<TitleResponseDto> getLastDaysTitles(Long days) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Long userId = user.getId();

        List<Title> titles = titleRepository.findByLastXDays(days, userId);

        return titles.stream()
                .map(title -> mapper.map(title, TitleResponseDto.class))
                .collect(Collectors.toList());
    }

    private void titleValidation(TitleRequestDto dto, CreditCard creditCard, User user) {

        if (creditCard != null
                && (dto.getType() == Type.EXPENSE && !creditCard.getUser().getId().equals(user.getId()))) {
            throw new ResourceBadRequestException("Você não pode alterar dados de outros usuários.");
        }

        if (dto.getInstallment() > 99) {
            throw new ResourceBadRequestException("O número máximo de prestações é 99.");
        }

        if (dto.getValue() == null || dto.getValue() == 0 ||

                dto.getDescription() == null || dto.getType() == null) {

            throw new ResourceNotFoundException(
                    "Os campos título, data de vencimento, valor e descrição são obrigatórios.");
        } else if (dto.getValue() < 0) {

            dto.setValue(-(dto.getValue()));
        }
    }

    private Double getRemainingAmountFromInstallments(TitleRequestDto dto) {
        int installment = dto.getInstallment();
        Double formattedInstallmentValue = Math.round((dto.getValue() / installment) * 100.0) / 100.0;
        Double totalInstallmentsValue = formattedInstallmentValue * installment;
        Double remainingAmount = dto.getValue() - totalInstallmentsValue;

        return remainingAmount;
    }
}