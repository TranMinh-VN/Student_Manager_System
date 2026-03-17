package com.learningcode.student_management_system.controller;

import com.learningcode.student_management_system.dto.response.ApiResponse;
import com.learningcode.student_management_system.dto.request.StudentCreationRequest;
import com.learningcode.student_management_system.dto.request.StudentUpdateRequest;
import com.learningcode.student_management_system.dto.response.StudentResponse;
import com.learningcode.student_management_system.service.StudentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class StudentController {
    StudentService studentService;

    @PostMapping
    ApiResponse<StudentResponse> createStudent(@RequestBody @Valid StudentCreationRequest request) {

        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(studentService.createStudent(request));
        return apiResponse;
    }

    @GetMapping
    ApiResponse<List<StudentResponse>> getAllStudents() {
        ApiResponse<List<StudentResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(studentService.getAllStudents());

        return apiResponse;
    }

    @GetMapping("/{studentId}")
    ApiResponse<StudentResponse> getStudentById(@PathVariable("studentId") String studentId) {
        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(studentService.getStudentById(studentId));

        return apiResponse;
    }

    @PutMapping("/{studentId}")
    ApiResponse<StudentResponse> updateStudent(@PathVariable String studentId, @RequestBody @Valid StudentUpdateRequest request) {
        ApiResponse<StudentResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(studentService.updateStudent(studentId, request));

        return apiResponse;
    }

    @DeleteMapping("/{studentId}")
    ApiResponse<Void> deleteStudentById(@PathVariable String studentId) {
        studentService.deleteStudentById(studentId);

        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Student has been deleted");


        return apiResponse;
    }

}
