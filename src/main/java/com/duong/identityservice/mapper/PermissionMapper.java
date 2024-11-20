package com.duong.identityservice.mapper;

import com.duong.identityservice.dto.request.PermissionRequest;
import com.duong.identityservice.dto.response.PermissionResponse;
import com.duong.identityservice.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
