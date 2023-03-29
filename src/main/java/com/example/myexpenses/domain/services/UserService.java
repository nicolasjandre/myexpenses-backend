package com.example.myexpenses.domain.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.myexpenses.domain.exception.ResourceBadRequestException;
import com.example.myexpenses.domain.exception.ResourceNotFoundException;
import com.example.myexpenses.domain.model.User;
import com.example.myexpenses.domain.repository.UserRepository;
import com.example.myexpenses.dto.user.UserRequestDto;
import com.example.myexpenses.dto.user.UserResponseDto;
import com.example.myexpenses.security.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService implements ICRUDService<UserRequestDto, UserResponseDto> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;

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
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }

        return mapper.map(optUser.get(), UserResponseDto.class);
    }

    public UserResponseDto getMe(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        jwtUtil.checkIfTokenIsValid(token);
        String email = jwtUtil.getEmailFromJwt(token);
        
        return getByEmail(email);
    }

    public UserResponseDto getByEmail(String email) {

        Optional<User> optUser = userRepository.findByEmail(email);

        if (optUser.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o email: " + email);
        }

        return mapper.map(optUser.get(), UserResponseDto.class);
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {

        checkIfEmailAndPasswordAreNotNull(dto);

        Optional<User> optUser = userRepository.findByEmail(dto.getEmail());

        if (optUser.isPresent()) {
            throw new ResourceBadRequestException("Já existe um usuário cadastrado com o e-mail: " + dto.getEmail());
        }

        User user = mapper.map(dto, User.class);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());

        user = userRepository.save(user);

        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {

        User userWhoIsRequesting = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserResponseDto userDatabase = getById(id);

        checkIfEmailAndPasswordAreNotNull(dto);

        User user = mapper.map(dto, User.class);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());

        if (userDatabase.getId() != userWhoIsRequesting.getId()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }

        user.setPassword(encodedPassword);

        user.setId(id);
        user.setCreated_at(userDatabase.getCreated_at());
        user.setInative_at(userDatabase.getInative_at());
        user.setUpdated_at(new Date());
        
        user = userRepository.save(user);

        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public void delete(Long id) {

        Optional<User> optUser = userRepository.findById(id);
        
        if (optUser.isEmpty()) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }
        
        User user = optUser.get();

        user.setInative_at(new Date());

        userRepository.save(user);
    }

    private void checkIfEmailAndPasswordAreNotNull(UserRequestDto user) {
        if (user.getEmail() == null || user.getPassword() == null) {
            throw new ResourceBadRequestException(
                    "Não é possível cadastrar um usuário sem preencher os campos e-mail e senha.");
        } else if (user.getPassword().length() < 8) {
            throw new ResourceBadRequestException("A senha precisa ter pelo menos 8 caracteres.");
        }
    }
}