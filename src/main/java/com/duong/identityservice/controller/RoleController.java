package com.duong.identityservice.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.duong.identityservice.dto.request.ApiResponse;
import com.duong.identityservice.dto.request.RoleRequest;
import com.duong.identityservice.dto.response.RoleResponse;
import com.duong.identityservice.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .code(1000)
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(1000)
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission) {
        roleService.delete(permission);
        return ApiResponse.<Void>builder().code(1000).build();
    }
}
