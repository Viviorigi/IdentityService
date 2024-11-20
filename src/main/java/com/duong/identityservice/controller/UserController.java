package com.duong.identityservice.controller;

import com.duong.identityservice.dto.request.ApiResponse;
import com.duong.identityservice.dto.request.UserCreationRequest;
import com.duong.identityservice.dto.request.UserUpdateRequest;
import com.duong.identityservice.dto.response.UserResponse;
import com.duong.identityservice.entity.User;
import com.duong.identityservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userReq){
        ApiResponse<UserResponse> apiResponse= new ApiResponse<>();
        apiResponse.setResult(userService.createRequest(userReq));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<UserResponse>> getUsers(){
//        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserById(@PathVariable String userId){

        return  ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.findById(userId))
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo(){
        return  ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    UserResponse updateUser(@PathVariable String userId, @RequestBody UserUpdateRequest updateRequest){
        return  userService.updateUser(userId,updateRequest);
    }
    @DeleteMapping("/{userId}")
    ApiResponse<String> deleleUser(@PathVariable String userId){
        ApiResponse<String> apiResponse= new ApiResponse<>();
        userService.deleteUser(userId);
        apiResponse.setResult("user has been deleted");
        return apiResponse;
    }
}
