package com.learningcode.student_management_system.service;

import com.learningcode.student_management_system.dto.request.StudentCreationRequest;
import com.learningcode.student_management_system.dto.request.StudentUpdateRequest;
import com.learningcode.student_management_system.dto.response.StudentResponse;
import com.learningcode.student_management_system.entity.Student;
import com.learningcode.student_management_system.exception.AppException;
import com.learningcode.student_management_system.exception.ErrorCode;
import com.learningcode.student_management_system.mapper.StudentMapper;
import com.learningcode.student_management_system.repository.StudentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,  makeFinal = true)
public class StudentService {
    StudentRepository studentRepository;

    StudentMapper studentMapper;


    public StudentResponse createStudent(StudentCreationRequest request) {
        if (studentRepository.existsByEmail(request.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        Student student = studentMapper.toStudent(request);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        student.setPassword(passwordEncoder.encode(request.getPassword()));

        student.setStudentCode("STU-" + System.currentTimeMillis());

        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public List<StudentResponse> getAllStudents() {
        return studentMapper.toStudentResponseList(studentRepository.findAll());
    }

    private Student getStudentEntityById(String id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_FOUND));
    }

    public StudentResponse getStudentById(String id) {
        Student student =  getStudentEntityById(id);
        return studentMapper.toStudentResponse(student);
    }

    public StudentResponse updateStudent(String id, StudentUpdateRequest request) {
        Student student = getStudentEntityById(id);

        studentMapper.updateStudent(student, request);

        if (studentRepository.existsByEmailAndIdNot(request.getEmail(), id))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        student.setEmail(request.getEmail());

        return studentMapper.toStudentResponse(studentRepository.save(student));
    }

    public void deleteStudentById(String id) {
        getStudentEntityById(id);
        studentRepository.deleteById(id);
    }

    //Map tay
    /*public StudentResponse maptoStudentResponse(Student student) {
        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setStudentCode(student.getStudentCode());
        response.setFullName(student.getFirstName() + " " + student.getLastName());
        response.setEmail(student.getEmail());
        response.setDob(student.getDob());
        response.setGender(student.getGender());
        response.setGpa(student.getGpa());

        return response;
    }*/
}
