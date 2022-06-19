package com.example.doannmcnpm.controller;


import com.example.doannmcnpm.common.ApiResponse;
import com.example.doannmcnpm.dto.PasswordDto;
import com.example.doannmcnpm.dto.ResponseDto;
import com.example.doannmcnpm.dto.user.SignInDto;
import com.example.doannmcnpm.dto.user.SignInReponseDto;
import com.example.doannmcnpm.dto.user.SignupDto;
import com.example.doannmcnpm.model.User;
import com.example.doannmcnpm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserService userService;

    // two apis

    // signup

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto) {
        return userService.signUp(signupDto);
    }


    // signin

    @PostMapping("/signin")
    public SignInReponseDto signIn(@RequestBody SignInDto signInDto) {
        return userService.signIn(signInDto);
    }

    //listUsser

    @GetMapping("/list")
    public List<User> listAll() {
        return userService.listUser();
    }

    @GetMapping("/{token}")
    public User getUser(@PathVariable("token") String token)    {
        return userService.getUser(token);
    }

    @PutMapping("/update/{userId}/{roleId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") int userId, @PathVariable("roleId") int roleId)  {
        User user= userService.getUserById(userId);
        if(user==null)   {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "User does not exists"), HttpStatus.NOT_FOUND);
        }
        userService.updateUser(user, roleId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User has been updated"), HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping("/changePass/{token}")
    public ResponseEntity<ApiResponse> changePassword(@PathVariable("token") String token,@RequestBody PasswordDto passwordDto)  {


        User user= userService.getUser(token);
        if(user==null)   {
            return new ResponseEntity<>(new ApiResponse(false, "User does not exists"), HttpStatus.NOT_FOUND);
        }
        userService.changePassword(user, passwordDto);
        return new ResponseEntity<>(new ApiResponse(true, "Password has been updated"), HttpStatus.OK);
    }



    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") int userId)  {

        User user= userService.getUserById(userId);
        if(user==null)   {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "User does not exists"), HttpStatus.NOT_FOUND);
        }
//        userService.deleteUser(user);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User has been deleted "), HttpStatus.OK);
    }




}