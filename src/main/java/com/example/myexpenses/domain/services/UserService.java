package com.example.myexpenses.domain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    private JwtUtil jwtUtil;

    private String userNotFound = "Não foi possível encontrar o usuário com o id: ";

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
        User userWhoIsRequesting = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (optUser.isEmpty() || !optUser.get().getId().equals(userWhoIsRequesting.getId())) {
            throw new ResourceNotFoundException("Não foi possível encontrar o usuário com o id: " + id);
        }

        return mapper.map(optUser.get(), UserResponseDto.class);
    }

    public UserResponseDto getMe(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        String token = header.substring(7);

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
    public List<UserResponseDto> create(UserRequestDto dto) {

        checkIfEmailAndPasswordAreNotNull(dto);

        Optional<User> optUser = userRepository.findByEmail(dto.getEmail().toLowerCase());

        if (optUser.isPresent()) {
            throw new ResourceBadRequestException("Já existe um usuário cadastrado com o e-mail: " + dto.getEmail());
        }

        if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
            throw new ResourceBadRequestException("As senhas não conferem.");
        }

        User user = mapper.map(dto, User.class);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEmail(user.getEmail().toLowerCase());
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());
        user.setUserBalance(0.0);

        user = userRepository.save(user);
        List<User> users = new ArrayList<>();
        users.add(user);

        return users.stream()
                .map(mappedUser -> mapper.map(mappedUser, UserResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto dto) {

        User userWhoIsRequesting = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!passwordEncoder.matches(dto.getPassword(), userWhoIsRequesting.getPassword())) {
            throw new BadCredentialsException("Não foi possível atualizar o perfil: senha atual incorreta.");
        }

        UserResponseDto userDatabase = getById(id);

        if (!userDatabase.getId().equals(userWhoIsRequesting.getId())) {
            throw new ResourceNotFoundException(userNotFound + id);
        }

        User user = mapper.map(dto, User.class);

        if (dto.getNewPassword() != null) {

            if (dto.getNewPassword().length() < 8) {
                throw new ResourceBadRequestException("A nova senha precisa ter no mínimo 8 caracteres.");
            } else if (!dto.getNewPassword().equals(dto.getPasswordConfirmation())) {
                throw new BadCredentialsException("A nova senha e a sua confirmação não conferem.");
            } else if (dto.getNewPassword().equals(dto.getPassword())) {
                throw new ResourceBadRequestException("A nova senha não pode ser igual a antiga.");
            } else {
                String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
                user.setPassword(encodedPassword);
            }
        } else {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(encodedPassword);
        }

        if (dto.getImage() == null) {

            user.setImage(userWhoIsRequesting.getImage());

        }

        user.setUserBalance(userDatabase.getUserBalance());
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
            throw new ResourceNotFoundException(userNotFound + id);
        }

        User user = optUser.get();

        user.setInative_at(new Date());

        userRepository.save(user);
    }

    public UserResponseDto updateUserBalance(UserRequestDto dto) {

        if (dto.getUserBalance() == null) {
            throw new ResourceBadRequestException("A atualização do saldo não pode ser do tipo: 'null'");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        user.setUserBalance(dto.getUserBalance());

        user = userRepository.save(user);

        return mapper.map(user, UserResponseDto.class);
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