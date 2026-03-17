package com.learningcode.student_management_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(unique = true)
    String studentCode;

    String firstName;
    String lastName;

    @Column(unique = true)
    String email;

    LocalDate dob;

    @Enumerated(EnumType.STRING)
    Gender gender;

    double gpa;

    String password;

}
