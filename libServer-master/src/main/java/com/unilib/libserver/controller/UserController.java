package com.unilib.libserver.controller;


import com.unilib.libserver.entity.*;
import com.unilib.libserver.repo.UserData;
import com.unilib.libserver.service.IssuesService;
import com.unilib.libserver.service.UserService;
import org.json.JSONStringer;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200",methods = {RequestMethod.POST,RequestMethod.GET,RequestMethod.PUT,RequestMethod.DELETE})
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping("/user/create")
    public ApiResponse<JSONObject> createUser(@RequestBody User user){
        System.out.println(user);
        return userService.addUser(user);
    }

    @PostMapping("user/login")
    public ApiResponse<JSONObject> authenticateUser(@RequestBody UserLogin userLogin){
        return userService.userLogin(userLogin.email,userLogin.pass);
    }

    @PostMapping("user/change-status")
    public ApiResponse<List<UserData>> deactivateUser(@RequestParam String id){
        return userService.changeUserStatus(id);
    }

    @GetMapping("/users")
    public ApiResponse<List<UserData>> findAllUsers(@RequestParam String id){
       return userService.getAllUsers(id);
    }

    @PostMapping("/user/change-password")
    public ApiResponse<String> changePassword(@RequestBody UserPasswordChange passwordChange){
        return userService.changePassword(passwordChange.currentPass,passwordChange.newPass,passwordChange.id);
    }




}
