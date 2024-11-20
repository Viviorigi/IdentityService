package com.duong.identityservice.dto.request;

import com.duong.identityservice.validator.DobConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 3,message = "USERNAME_INVALID")
    String username;
    @Size(min = 6,message = "PASSWORD_INVALID")
    String password;
    @Size(min = 3,message = "FirstName must be at least 3 characters")
    String firstName;
    @Size(min = 3,message = "LastName must be at least 3 characters")
    @NotEmpty(message = "LastName required")
    String lastName;

    @DobConstraint(min=17,message ="INVALID_DOB")
    LocalDate dob;

}
