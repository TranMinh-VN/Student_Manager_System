package com.learningcode.student_management_system.controller;

import com.learningcode.student_management_system.dto.request.AuthenticationRequest;
import com.learningcode.student_management_system.dto.response.ApiResponse;
import com.learningcode.student_management_system.dto.response.AuthenticationResponse;
import com.learningcode.student_management_system.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        boolean result = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(AuthenticationResponse.builder()
                        .authenticated(result)
                        .build())
                .build();
    }
}
