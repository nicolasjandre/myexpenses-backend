package com.example.myexpenses.domain.services;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myexpenses.domain.model.CreditCard;
import com.example.myexpenses.domain.model.CreditCardInvoice;
import com.example.myexpenses.domain.repository.CreditCardInvoiceRepository;
import com.example.myexpenses.domain.repository.CreditCardRepository;
import com.example.myexpenses.dto.creditcardinvoice.CreditCardInvoiceResponseDto;

@Service
public class CreditCardInvoiceService {

   @Autowired
   private CreditCardRepository creditCardRepository;

   @Autowired
   private CreditCardInvoiceRepository creditCardInvoiceRepository;

   @Autowired
   private ModelMapper mapper;

   public List<CreditCardInvoiceResponseDto> generateInvoicesWhenCreatingTitles(LocalDate startDate, int installment,
         Long creditCardId) {

      Optional<CreditCard> creditCardOpt = creditCardRepository.findById(creditCardId);
      CreditCard creditCard = creditCardOpt.get();
      List<CreditCardInvoice> invoices = creditCardInvoiceRepository.findByCreditCard(creditCard);
      List<CreditCardInvoice> createdInvoices = new ArrayList<>();

      int creditCardClosingDay = creditCard.getClosingDay();

      startDate = startDate.getDayOfMonth() >= creditCardClosingDay
      ? startDate.plusMonths(1)
      : startDate;

      int currentYear = startDate.getYear();
      Month currentMonth = startDate.getMonth();

      LocalDate endDate = startDate.plusMonths(installment - 1);

      LocalDate currentDate = startDate;

      while (currentDate.isBefore(endDate) || currentDate.equals(endDate)) {
         boolean alreadyExists = false;

         int closingDay = creditCard.getClosingDay();
         int dueDateDay = creditCard.getDueDay();
         LocalDate dueDate = LocalDate.of(currentYear, currentMonth, dueDateDay);

         for (CreditCardInvoice invoice : invoices) {
            if (invoice.getDueDate().equals(dueDate)) {
               currentMonth = currentMonth.plus(1);

               if (currentMonth == Month.JANUARY) {
                  currentYear++;
               }

               currentDate = LocalDate.of(currentYear, currentMonth, 1);
               alreadyExists = true;
               break;
            }
         }

         if (!alreadyExists) {
            LocalDate closingDate = LocalDate.of(currentYear, currentMonth, closingDay);

            CreditCardInvoice newInvoice = new CreditCardInvoice();
            newInvoice.setCreditCard(creditCard);
            newInvoice.setDueDate(dueDate);
            newInvoice.setClosingDate(closingDate);
            newInvoice.setPaid(false);

            creditCardInvoiceRepository.save(newInvoice);

            currentMonth = currentMonth.plus(1);

            if (currentMonth == Month.JANUARY) {
               currentYear++;
            }

            currentDate = LocalDate.of(currentYear, currentMonth, 1);
            createdInvoices.add(newInvoice);
         }
      }
      return createdInvoices.stream()
            .map(invoice -> mapper.map(invoice, CreditCardInvoiceResponseDto.class))
            .collect(Collectors.toList());
   }
}