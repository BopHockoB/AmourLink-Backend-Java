package ua.nure.securityservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ua.nure.securityservice.model.User;
import ua.nure.securityservice.model.dto.UserDTO;
import ua.nure.securityservice.responce.ResponseBody;
import ua.nure.securityservice.service.IUserService;


@RestController
@RequestMapping("api/security-service/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/get-all")
    public ResponseEntity<ResponseBody> getAllUsers(){
        ResponseBody responseBody = new ResponseBody(userService.findAllUsers());
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ResponseBody> add(@RequestBody @Valid UserDTO userDTO) {
        User user = new User(userDTO);
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
    public ResponseEntity<ResponseBody> update(@RequestBody @Valid UserDTO userDTO){
        User user = new User(userDTO);
        ResponseBody responseBody = new ResponseBody(userService.updateUser(user));
        return ResponseEntity.ok(responseBody);
    }

}