package com.learningcode.student_management_system.service;

import com.learningcode.student_management_system.dto.request.AuthenticationRequest;
import com.learningcode.student_management_system.exception.AppException;
import com.learningcode.student_management_system.exception.ErrorCode;
import com.learningcode.student_management_system.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    StudentRepository studentRepository;

    public boolean authenticate(AuthenticationRequest request) {
        var student = studentRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return  passwordEncoder.matches(request.getPassword(), student.getPassword());
    }
}
