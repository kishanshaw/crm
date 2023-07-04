package com.onetoone.mapping.controllers;

import com.onetoone.mapping.annotations.MeasureTime;
import com.onetoone.mapping.dtos.UserRequestDTO;
import com.onetoone.mapping.entities.Address;
import com.onetoone.mapping.entities.User;
import com.onetoone.mapping.exceptions.UserNotFoundException;
import com.onetoone.mapping.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Slf4j
class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @MeasureTime
    public User createUser(@RequestHeader(value = "traceId") String traceId, HttpServletRequest request, @RequestBody UserRequestDTO userRequestDTO) {
        log.info("Request - {}", userRequestDTO);
        User user = User.builder().name(userRequestDTO.getName()).build();
        Address address = Address.builder().pinCode(userRequestDTO.getAddress().getPinCode()).street(userRequestDTO.getAddress().getStreet()).build();
        user.setAddress(address);
        address.setUser(user);
        return userRepository.save(user);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @MeasureTime
    List<User> findAllUsers(@RequestHeader(value = "traceId") String traceId, HttpServletRequest request) {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @MeasureTime
    User findUser(@RequestHeader(value = "traceId") String traceId, HttpServletRequest request, @PathVariable int id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
}
