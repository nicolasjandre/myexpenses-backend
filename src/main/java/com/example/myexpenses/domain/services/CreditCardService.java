package com.example.myexpenses.domain.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.myexpenses.domain.exception.ResourceBadRequestException;
import com.example.myexpenses.domain.model.CreditCard;
import com.example.myexpenses.domain.model.Title;
import com.example.myexpenses.domain.model.User;
import com.example.myexpenses.domain.repository.CreditCardRepository;
import com.example.myexpenses.domain.repository.TitleRepository;
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

   public List<CreditCardResponseDto> create(CreditCardRequestDto dto) {
      
      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      CreditCard creditCard = mapper.map(dto, CreditCard.class);

      creditCard.setUser(user);
      creditCard.setAvailableLimit(2600d);
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
}
