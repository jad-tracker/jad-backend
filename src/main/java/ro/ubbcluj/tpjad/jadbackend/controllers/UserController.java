package ro.ubbcluj.tpjad.jadbackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.ubbcluj.tpjad.jadbackend.dtos.UserLoginGetDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.UserLoginPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.UserRegisterPostDto;
import ro.ubbcluj.tpjad.jadbackend.models.User;
import ro.ubbcluj.tpjad.jadbackend.services.JwtService;
import ro.ubbcluj.tpjad.jadbackend.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginGetDto> login(@RequestBody UserLoginPostDto userDto) {
        User authUser = userService.login(userDto);
        String jwt = jwtService.generateToken(authUser);

        UserLoginGetDto loginResponse = new UserLoginGetDto(jwt, jwtService.getExpirationTime());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserRegisterPostDto userDto) {
        userService.register(userDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
