package com.duong.identityservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.duong.identityservice.dto.request.RoleRequest;
import com.duong.identityservice.dto.response.RoleResponse;
import com.duong.identityservice.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
