package ro.ubbcluj.tpjad.jadbackend.services;

import ro.ubbcluj.tpjad.jadbackend.dtos.UserLoginPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.UserRegisterPostDto;
import ro.ubbcluj.tpjad.jadbackend.models.User;

public interface UserService {
    void register(UserRegisterPostDto userDto);
    User login(UserLoginPostDto userDto);
    User findUserByUsername(String username);
}
