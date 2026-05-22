package com.learningcode.student_management_system.exception;

public enum ErrorCode {
    EMAIL_REQUIRED(1006, "Email is required"),
    EMAIL_EXISTED(1001, "Email existed"),
    EMAIL_INVALID(1002, "The email address must be a valid address."),
    EMAIL_NOT_EXISTED(1016, "Email not existed"),
    STUDENT_NOT_FOUND(1003, "Student not found"),
    FIRST_NAME_REQUIRED(1004, "First name is required"),
    FIRST_NAME_INVALID(1011, "First name must be from 1 to 50 characters"),
    LAST_NAME_REQUIRED(1005, "Last name is required"),
    LAST_NAME_INVALID(1012, "Last name must be from 1 to 50 characters"),
    DOB_REQUIRED(1007, "DOB is required"),
    DOB_INVALID(1009, "Birthday must be a day in the past"),
    GENDER_REQUIRED(1013, "Gender is required"),
    GPA_REQUIRED(1008, "GPA must be not null"),
    GPA_INVALID(1010, "GPA must be between 0.0 and 4.0"),
    PASSWORD_REQUIRED(1014, "Password is required"),
    PASSWORD_INVALID(1015, "Password must be at least 8 characters"),

    UNAUTHENTICATED(2001, "Unauthenticated"),
    INVALID_KEY(9998, "Invalid message key"),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception error"),
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
