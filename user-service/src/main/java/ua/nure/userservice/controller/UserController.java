package ua.nure.userservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.nure.userservice.exception.UserAlreadyExistsException;
import ua.nure.userservice.exception.UserNotFoundException;
import ua.nure.userservice.model.User;
import ua.nure.userservice.responce.ResponseBody;
import ua.nure.userservice.service.impl.UserService;

import java.util.List;

@RestController
@RequestMapping("api/user-service/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> getAllUsers(){
        ResponseBody responseBody = new ResponseBody(userService.findAllUsers());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ResponseBody> add(@RequestBody @Valid User user) {
        ResponseBody responseBody = new ResponseBody(userService.createUser(user));
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseBody> getByEmail(@PathVariable("email") String email){
        ResponseBody responseBody = new ResponseBody(userService.findUser(email));
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{email}")
    public void delete(@PathVariable("email") String email){
        userService.deleteUser(email);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBody> update(@RequestBody @Valid User user){
        ResponseBody responseBody = new ResponseBody(userService.updateUser(user));
        return ResponseEntity.ok(responseBody);
    }

}