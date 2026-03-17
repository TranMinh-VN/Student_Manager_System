package com.learningcode.student_management_system.dto.response;

import com.learningcode.student_management_system.entity.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentResponse {
    String id;
    String studentCode;
    String fullName;
    String email;
    LocalDate dob;
    Gender gender;
    Double gpa;


}