package com.learningcode.student_management_system.mapper;

import com.learningcode.student_management_system.dto.request.StudentCreationRequest;
import com.learningcode.student_management_system.dto.request.StudentUpdateRequest;
import com.learningcode.student_management_system.dto.response.StudentResponse;
import com.learningcode.student_management_system.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "studentCode", ignore = true)
    Student toStudent(StudentCreationRequest request);

    @Mapping(target = "email",  ignore = true)
    void updateStudent(@MappingTarget Student student, StudentUpdateRequest request);


    @Mapping(
            target = "fullName",
            expression = "java(student.getFirstName() + \" \" + student.getLastName())"
    )
    StudentResponse toStudentResponse(Student student);

    List<StudentResponse> toStudentResponseList(List<Student> students);
}
