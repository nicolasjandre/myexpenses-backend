package com.example.meusgastos.domain.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.exception.ResourceNotFoundException;
import com.example.meusgastos.domain.model.CostCenter;
import com.example.meusgastos.domain.model.User;
import com.example.meusgastos.domain.repository.CostCenterRepository;
import com.example.meusgastos.dto.costcenter.CostCenterRequestDto;
import com.example.meusgastos.dto.costcenter.CostCenterResponseDto;

@Service
public class CostCenterService implements ICRUDService<CostCenterRequestDto, CostCenterResponseDto> {

   @Autowired
   private CostCenterRepository costCenterRepository;

   @Autowired
   private ModelMapper mapper;

   @Override
   public List<CostCenterResponseDto> getAll() {

      List<CostCenter> costCenters = costCenterRepository.findAll();

      return costCenters.stream()
            .map(costCenter -> mapper.map(costCenter, CostCenterResponseDto.class))
            .collect(Collectors.toList());
   }

   @Override
   public CostCenterResponseDto getById(Long id) {

      Optional<CostCenter> costCenterOpt = costCenterRepository.findById(id);

      if (costCenterOpt.isEmpty()) {
         throw new ResourceNotFoundException("Não foi possível encontrar o centro de custo com o id: " + id);
      }

      return mapper.map(costCenterOpt.get(), CostCenterResponseDto.class);
   }

   @Override
   public CostCenterResponseDto create(CostCenterRequestDto dto) {

      CostCenter costCenter = mapper.map(dto, CostCenter.class);

      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      costCenter.setUser(user);
      costCenter = costCenterRepository.save(costCenter);

      return mapper.map(costCenter, CostCenterResponseDto.class);
   }

   @Override
   public CostCenterResponseDto update(Long id, CostCenterRequestDto dto) {
     
      CostCenterResponseDto costCenterDatabase = getById(id);

      CostCenter costCenter = mapper.map(dto, CostCenter.class);

      User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      costCenter.setUser(user);
      costCenter.setInative_at(costCenterDatabase.getInative_at());
      costCenter.setId(id);
      costCenter = costCenterRepository.save(costCenter);

      return mapper.map(costCenter, CostCenterResponseDto.class);
   }

   @Override
   public void delete(Long id) {

      CostCenterResponseDto costCenterDto = getById(id);
      
      CostCenter costCenter = mapper.map(costCenterDto, CostCenter.class);
        
      costCenter.setInative_at(new Date());

      costCenterRepository.save(costCenter);
   }

}
