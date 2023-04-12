package com.example.myexpenses.domain.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.myexpenses.domain.exception.ResourceBadRequestException;
import com.example.myexpenses.domain.exception.ResourceNotFoundException;
import com.example.myexpenses.domain.model.CreditCard;
import com.example.myexpenses.domain.model.CreditCardInvoice;
import com.example.myexpenses.domain.model.Title;
import com.example.myexpenses.domain.model.User;
import com.example.myexpenses.domain.repository.CreditCardInvoiceRepository;
import com.example.myexpenses.domain.repository.CreditCardRepository;
import com.example.myexpenses.domain.repository.TitleRepository;
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
   CreditCardRepository creditCardRepository;

   @Autowired
   CreditCardService creditCardService;

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

   @Override
   public TitleResponseDto getById(Long id) {

      Optional<Title> optTitle = titleRepository.findById(id);

      System.out.println(new Date());

      if (optTitle.isEmpty()) {
         throw new ResourceNotFoundException("Não foi possível encontrar o título com o id: " + id);
      }

      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      if (optTitle.get().getUser().getId() != user.getId()) {
         throw new ResourceNotFoundException("Não foi possível encontrar o título com o id: " + id);
      }

      return mapper.map(optTitle.get(), TitleResponseDto.class);
   }

   @Override
   public List<TitleResponseDto> create(TitleRequestDto dto) {

      if (dto.getInstallment() > 99) {
         throw new ResourceBadRequestException("O número máximo de prestações é 99.");
      }

      titleValidation(dto);

      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      CreditCard creditCard = creditCardRepository.findById(dto.getCreditCardId()).get();

      if (!creditCard.getUser().getId().equals(user.getId())) {
         throw new ResourceBadRequestException("Você não pode alterar dados de outros usuários.");
      }

      int creditCardClosingDay = creditCard.getClosingDay();
      int installment = dto.getInstallment();
      Double formattedInstallmentValue = Math.round((dto.getValue() / installment) * 100.0) / 100.0;
      Double totalInstallmentsValue = formattedInstallmentValue * installment;
      Double remainingAmount = dto.getValue() - totalInstallmentsValue;

      List<Title> titles = new ArrayList<>();

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
         }

         referenceDate = calendar.getTime();

         Instant instantReferenceDate = referenceDate.toInstant();
         LocalDate localReferenceDate = instantReferenceDate.atZone(ZoneId.systemDefault()).toLocalDate()
               .withDayOfMonth(creditCard.getDue_date());

         if (localReferenceDate.getDayOfMonth() >= creditCardClosingDay) {
            localReferenceDate = localReferenceDate.plusMonths(1);
         }
         ;

         CreditCardInvoice invoice = creditCardInvoiceRepository.findByCreditCardAndDueDate(creditCard,
               localReferenceDate);

         formattedInstallmentValue = Math.round((dto.getValue() / installment) * 100.0) / 100.0;

         if (remainingAmount > 0) {
            formattedInstallmentValue += 0.01;
            remainingAmount -= 0.01;
         }

         String titleDescription = installment > 1 ? titleDto.getDescription() + " (" + i + ")"
               : titleDto.getDescription();

         title.setDescription(titleDescription);
         title.setCreatedAt(new Date());
         title.setValue(formattedInstallmentValue);
         title.setUser(user);
         title.setInvoice(invoice);
         title.setCostCenter(titleDto.getCostCenter());
         title.setType(titleDto.getType());
         title.setReferenceDate(referenceDate);
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

      titleValidation(dto);

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

      List<Title> titles = titleRepository.findByCreatedAtBetweenAndUserId(initialDate, finalDate, userId);

      return titles.stream()
            .map(title -> mapper.map(title, TitleResponseDto.class))
            .collect(Collectors.toList());
   }

   private void titleValidation(TitleRequestDto dto) {

      if (dto.getValue() == null || dto.getValue() == 0 ||

            dto.getDescription() == null || dto.getType() == null) {

         throw new ResourceNotFoundException(
               "Os campos título, data de vencimento, valor e descrição são obrigatórios.");
      } else if (dto.getValue() < 0) {

         dto.setValue(-(dto.getValue())); // setting to a positive value
      }
   }
}