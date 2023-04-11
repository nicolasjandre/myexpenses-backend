package com.example.myexpenses.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myexpenses.domain.services.UserService;
import com.example.myexpenses.dto.user.UserRequestDto;
import com.example.myexpenses.dto.user.UserResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/users")
public class UserController implements ICRUDController<UserRequestDto, UserResponseDto> {

    @Autowired
    private UserService userService;

    @Override
    @Operation(summary = "Replies with a list of all users and their information", description = "DON'T NEED A REQUEST BODY -- Just an user with admin permissions can use this")
    public ResponseEntity<List<UserResponseDto>> getAll() {

        return ResponseEntity.ok(userService.getAll());
    }

    @Override
    @Operation(summary = "Replies with the information about a specific user, searching by id")
    public ResponseEntity<UserResponseDto> getById(Long id) {

        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Replies with the information of the user who's making the request")
    public ResponseEntity<UserResponseDto> getMe(HttpServletRequest request) {

        return ResponseEntity.ok(userService.getMe(request));
    }

    @Override
    @Operation(summary = "Creates a new user", description = """
            {
            "name": "User",
            "email": "user@live.com",
            "password": "12345678",
            "passwordConfirmation": "12345678"
            }
    """)
    public ResponseEntity<UserResponseDto> create(UserRequestDto dto) {

        UserResponseDto userDto = userService.create(dto);

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @Override
    @Operation(summary = "Updates the user who match's the ID with the new submitted information", description = """

            <h2>Request body exemple to just update name or email:</h2>

            <code>
            {<br>
                "name": "Nicolas Jandre",<br>
                "email": "nicolasjandre@live.com",<br>
                "image": "base64 code here",<br>
                "password": "12345678"<br>
            }</code>

            <br>
                <h2>Request body example if the user needs to change his password:</h2>
                <br><br><code>{<br>
                    "name": "Nicolas Jandre",<br>
                    "email": "nicolasjandre@live.com",<br>
                    "image": "base64 code here",<br>
                    "password": "12345678", << old password<br>
                    "newPassword": "123456789", << new password<br>
                    "passwordConfirmation": "123456789" << new password confirmation<br>
                }</code>
                    """)
    public ResponseEntity<UserResponseDto> update(Long id, @RequestBody UserRequestDto dto) {

        UserResponseDto user = userService.update(id, dto);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    @Operation(summary = "Delete an user (does'nt remove from database, just puts an innative date)")
    public ResponseEntity<?> delete(Long id) {

        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Operation(summary = "Updates only the user BALANCE", description = """
            The other endpoint requires the user's password to function properly.
            In order to provide the user with greater convenience, I have
            created this specific endpoint for updating their balance with no needs.

            <h2>Request body example:</h2>

            <code>{
                "userBalance": 100
            }</code>""")
    public ResponseEntity<UserResponseDto> updateUserBalance(@RequestBody UserRequestDto dto) {

        UserResponseDto userDto = userService.updateUserBalance(dto);

        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
