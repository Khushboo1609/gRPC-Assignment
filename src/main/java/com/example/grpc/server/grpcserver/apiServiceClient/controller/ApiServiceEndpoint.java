package com.example.grpc.server.grpcserver.apiServiceClient.controller;

import com.example.grpc.server.grpcserver.apiServiceClient.service.GRPCClientService;
import com.example.grpc.server.grpcserver.hotelService.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
public class ApiServiceEndpoint {

    @Autowired
    GRPCClientService grpcClientService;

    @PostMapping("/save")
    public String saveUser(@RequestBody User user) {
        grpcClientService.saveUserDetails(user);
        return "Success";
    }
    @PostMapping("/saveAll")
    public String saveUser(@RequestBody List<User> user) {
        grpcClientService.saveUserDetailsInBulk(user);
        return "Success";
    }

    @GetMapping("/user/{userId}")
    public User findUser(@PathVariable("userId")int userId){
        return grpcClientService.getUserDetails((userId));
    }

    @GetMapping("/findBy/{userName}")
    public List<User>findByPattern(@PathVariable("userName") String userName){
        return grpcClientService.getUserStartingWith(userName);
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteByUserId(@PathVariable("userId")int userId){
        grpcClientService.deleteUserDetails(userId);
    }
}