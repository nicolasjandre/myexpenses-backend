package com.example.meusgastos.domain.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.meusgastos.domain.model.User;
import com.example.meusgastos.domain.repository.UserRepository;
import com.example.meusgastos.dto.user.UserRequestDto;
import com.example.meusgastos.dto.user.UserResponseDto;

public class UserService implements ICRUDService<UserRequestDto, UserResponseDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<UserResponseDto> getAll() {
        
        List<User> users = userRepository.findAll();

        return users.stream()
            .map(user -> mapper.map(user, UserResponseDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getById(Long id) {
        Optional<User> optUser = userRepository.findById(id);

        if (optUser.isEmpty()) {

        }

        return mapper.map(optUser.get(), UserResponseDto.class);
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}