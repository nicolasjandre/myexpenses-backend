package com.example.myexpenses.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.example.myexpenses.domain.enums.Bank;
import com.example.myexpenses.domain.enums.CardFlag;
import com.example.myexpenses.domain.exception.ResourceBadRequestException;
import com.example.myexpenses.domain.model.CreditCard;
import com.example.myexpenses.domain.model.Title;
import com.example.myexpenses.domain.model.User;
import com.example.myexpenses.domain.repository.CreditCardRepository;
import com.example.myexpenses.domain.repository.TitleRepository;
import com.example.myexpenses.dto.BankDto;
import com.example.myexpenses.dto.CardFlagDto;
import com.example.myexpenses.dto.creditCard.CreditCardRequestDto;
import com.example.myexpenses.dto.creditCard.CreditCardResponseDto;

@Service
public class CreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private ModelMapper mapper;

    public List<BankDto> getAllBanks() {
        List<BankDto> banks = new ArrayList<>();

        for (Bank bank : Bank.values()) {
            BankDto bankDto = new BankDto(bank.name(), bank.getValue());
            banks.add(bankDto);
        }

        return banks;
    }

    public List<CardFlagDto> getAllFlags() {
        List<CardFlagDto> cardFlags = new ArrayList<>();

        for (CardFlag cardFlag : CardFlag.values()) {
            CardFlagDto flagDto = new CardFlagDto(cardFlag.name(), cardFlag.getValue());
            cardFlags.add(flagDto);
        }

        return cardFlags;
    }

    public List<CreditCardResponseDto> getAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<CreditCard> creditCards = creditCardRepository.findByUser(user);

        return creditCards.stream()
                .map(creditCard -> mapper.map(creditCard, CreditCardResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<CreditCardResponseDto> create(CreditCardRequestDto dto) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CreditCard creditCard = mapper.map(dto, CreditCard.class);

        creditCard.setUser(user);
        creditCard.setAvailableLimit(dto.getCreditLimit());
        creditCardRepository.save(creditCard);

        List<CreditCard> createdCard = new ArrayList<>();
        createdCard.add(creditCard);

        return createdCard.stream()
                .map(createdCreditCard -> mapper.map(createdCreditCard, CreditCardResponseDto.class))
                .collect(Collectors.toList());
    }

    public void updateCreditCardLimitWhenCreatingTitles(CreditCard creditCard) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!creditCard.getUser().getId().equals(user.getId())) {
            throw new ResourceBadRequestException("Você não pode alterar dados de outros usuários.");
        }

        Double totalLimit = creditCard.getCreditLimit();
        Double totalSpent = 0d;

        List<Title> unpaidTitlesFromCreditCard = titleRepository.getUnpaidTitlesByCreditCard(creditCard.getId());

        for (Title title : unpaidTitlesFromCreditCard) {
            totalSpent += title.getValue();
        }

        Double newRemainingLimit = totalLimit - totalSpent;

        creditCard.setAvailableLimit(newRemainingLimit);
        creditCardRepository.save(creditCard);
    }

    public void delete(Long id) {
        findByIdAndCheckIfExists(id);
        creditCardRepository.deleteById(id);
    }

    public CreditCardResponseDto update(Long id, CreditCardRequestDto dto) {
        CreditCard creditCardDb = findByIdAndCheckIfExists(id);

        Double limitDiff = dto.getCreditLimit() - creditCardDb.getCreditLimit();

        CreditCard creditCard = mapper.map(dto, CreditCard.class);
        creditCard.setId(id);
        creditCard.setAvailableLimit(creditCardDb.getAvailableLimit() + limitDiff);
        creditCard.setUser(creditCardDb.getUser());

        return mapper.map(creditCardRepository.save(creditCard), CreditCardResponseDto.class);
    }

    private CreditCard findByIdAndCheckIfExists(Long id) {
        return creditCardRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cartão de crédito de id=[" + id + "] não encontrado"));
    }
}
