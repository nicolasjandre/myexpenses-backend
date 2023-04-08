package com.example.myexpenses.security;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.myexpenses.common.FormatDate;
import com.example.myexpenses.domain.model.RefreshJwt;
import com.example.myexpenses.domain.model.User;
import com.example.myexpenses.domain.repository.RefreshJwtRepository;
import com.example.myexpenses.domain.services.RefreshJwtService;
import com.example.myexpenses.dto.user.LoginRequestDto;
import com.example.myexpenses.dto.user.LoginResponseDto;
import com.example.myexpenses.dto.user.UserResponseDto;
import com.example.myexpenses.handler.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private RefreshJwtService refreshJwtService;
    private RefreshJwtRepository refreshJwtRepository;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
            RefreshJwtService refreshJwtService, RefreshJwtRepository refreshJwtRepository) {
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.refreshJwtService = refreshJwtService;
        this.refreshJwtRepository = refreshJwtRepository;

        setFilterProcessesUrl("/api/auth");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            LoginRequestDto login = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getEmail(),
                    login.getPassword());
            Authentication auth = authenticationManager.authenticate(authToken);

            return auth;
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Usuário ou senha inválidos");

        } catch (IOException e) {
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String notExpiredRefreshToken = "";

        try {
            notExpiredRefreshToken = refreshJwtRepository.findByUsers(user.getId()).get().getToken();
        } catch (Exception e) {
            System.out.println("Nenhum refresh token na base de dados. Será gerado um novo refresh token.");
        }

        String token = jwtUtil.generateToken(authResult);

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setCreated_at(user.getCreated_at());
        userResponseDto.setName(user.getName());
        userResponseDto.setInative_at(user.getInative_at());
        userResponseDto.setUpdated_at(user.getUpdated_at());

        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken("Bearer " + token);

        if (notExpiredRefreshToken == null || notExpiredRefreshToken.isEmpty()) {

            RefreshJwt newRefreshToken = refreshJwtService.createRefreshToken(user.getId());
            loginResponseDto.setRefreshToken(newRefreshToken.getToken());

        } else {
            loginResponseDto.setRefreshToken(notExpiredRefreshToken);
        }

        loginResponseDto.setUser(userResponseDto);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(loginResponseDto));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {

        String dateAndHour = FormatDate.formatDate(new Date());

        ErrorResponse error = new ErrorResponse(
                dateAndHour,
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                failed.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(error.getMessage()));
    }
}
