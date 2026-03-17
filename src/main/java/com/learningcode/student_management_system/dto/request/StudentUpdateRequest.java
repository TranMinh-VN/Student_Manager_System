package com.learningcode.student_management_system.dto.request;

import com.learningcode.student_management_system.entity.Gender;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentUpdateRequest {
    @NotBlank(message = "FIRST_NAME_REQUIRED")
    @Size(min = 1, max = 50, message = "FIRST_NAME_INVALID")
    String firstName;

    @NotBlank(message = "LAST_NAME_REQUIRED")
    @Size(min = 1, max = 50, message = "LAST_NAME_INVALID")
    String lastName;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Email(message = "EMAIL_INVALID")
    String email;

    @NotNull(message = "DOB_REQUIRED")
    @Past(message = "DOB_INVALID")
    LocalDate dob;

    @NotNull(message = "GENDER_REQUIRED")
    Gender gender;

    @NotNull(message = "GPA_REQUIRED")
    @DecimalMin(value = "0.0", message = "GPA_INVALID")
    @DecimalMax(value = "4.0", message = "GPA_INVALID")
    Double gpa;

}
