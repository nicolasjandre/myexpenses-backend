package com.example.myexpenses.domain.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.myexpenses.domain.Enum.TitleType;
import com.example.myexpenses.domain.exception.ResourceNotFoundException;
import com.example.myexpenses.domain.model.Title;
import com.example.myexpenses.domain.model.User;
import com.example.myexpenses.domain.repository.TitleRepository;
import com.example.myexpenses.dto.title.TitleRequestDto;
import com.example.myexpenses.dto.title.TitleResponseDto;

@Service
public class TitleService implements ICRUDService<TitleRequestDto, TitleResponseDto> {

   @Autowired
   private TitleRepository titleRepository;

   @Autowired
   private ModelMapper mapper;

   @Override
   public List<TitleResponseDto> getAll() {
      List<Title> titles = titleRepository.findAll();

      return titles.stream()
            .map(title -> mapper.map(title, TitleResponseDto.class))
            .collect(Collectors.toList());
   }

   @Override
   public TitleResponseDto getById(Long id) {

      Optional<Title> optTitle = titleRepository.findById(id);

      if (optTitle.isEmpty()) {
         throw new ResourceNotFoundException("Não foi possível encontrar o título com o id: " + id);
      }

      return mapper.map(optTitle.get(), TitleResponseDto.class);
   }

   @Override
   public TitleResponseDto create(TitleRequestDto dto) {

      titleValidation(dto);

      Title title = mapper.map(dto, Title.class);

      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      title.setCreated_at(new Date());
      title.setUser(user);
      title = titleRepository.save(title);

      return mapper.map(title, TitleResponseDto.class);
   }

   @Override
   public TitleResponseDto update(Long id, TitleRequestDto dto) {

      TitleResponseDto titleDatabase = getById(id);

      titleValidation(dto);

      Title title = mapper.map(dto, Title.class);

      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      title.setUser(user);
      title.setInative_at(titleDatabase.getInative_at());
      title.setId(id);
      title = titleRepository.save(title);

      return mapper.map(title, TitleResponseDto.class);
   }

   @Override
   public void delete(Long id) {

      TitleResponseDto titleDto = getById(id);

      Title title = mapper.map(titleDto, Title.class);

      title.setInative_at(new Date());

      titleRepository.save(title);
   }

   private void titleValidation(TitleRequestDto dto) {

      if (dto.getValue() == null || dto.getValue() == 0 || dto.getDueDate() == null ||

            dto.getDescription() == null || dto.getType() == null) {

         throw new ResourceNotFoundException(
               "Os campos título, data de vencimento, valor e descrição são obrigatórios.");
      } else if (dto.getValue() < 0 && dto.getType() == TitleType.INCOME) {

         dto.setValue(-(dto.getValue())); // setting to a positive value
      } else if (dto.getValue() > 0 && dto.getType() == TitleType.EXPENSE) {

         dto.setValue(-(dto.getValue())); // setting to a negative value
      }
   }
}
