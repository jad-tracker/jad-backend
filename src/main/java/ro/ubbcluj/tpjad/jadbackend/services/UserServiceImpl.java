package ro.ubbcluj.tpjad.jadbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.ubbcluj.tpjad.jadbackend.dtos.UserLoginPostDto;
import ro.ubbcluj.tpjad.jadbackend.dtos.UserRegisterPostDto;
import ro.ubbcluj.tpjad.jadbackend.exceptions.auth.ExistingUserException;
import ro.ubbcluj.tpjad.jadbackend.exceptions.auth.PasswordMismatchException;
import ro.ubbcluj.tpjad.jadbackend.exceptions.auth.UserNotFoundException;
import ro.ubbcluj.tpjad.jadbackend.models.User;
import ro.ubbcluj.tpjad.jadbackend.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void register(UserRegisterPostDto userDto) {
        Optional<User> foundUser = userRepository.findByUsername(userDto.getUsername());
        if (foundUser.isPresent()) {
            throw new ExistingUserException();
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new PasswordMismatchException();
        }

        User user = new User(
            null,
            userDto.getUsername(),
            passwordEncoder.encode(userDto.getPassword())
        );

        userRepository.save(user);
    }

    @Override
    public User login(UserLoginPostDto userDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            userDto.getUsername(),
            userDto.getPassword()
        ));

        return userRepository
                .findByUsername(userDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException(userDto.getUsername()));
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> foundUser = userRepository.findByUsername(username);

        return foundUser
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
