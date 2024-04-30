package ua.nure.userservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.userservice.exception.UserAlreadyExistsException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.User;
import ua.nure.userservice.service.impl.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.FOUND);
    }
    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user){
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{email}")
    public User getByEmail(@PathVariable("email") String email){
        try {
            return  userService.findUser(email);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable("email") String email){
        userService.deleteUser(email);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user){
        return ResponseEntity.ok(userService.updateUser(user));
    }

}