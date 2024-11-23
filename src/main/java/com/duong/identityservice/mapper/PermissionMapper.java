package com.duong.identityservice.mapper;

import org.mapstruct.Mapper;

import com.duong.identityservice.dto.request.PermissionRequest;
import com.duong.identityservice.dto.response.PermissionResponse;
import com.duong.identityservice.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
