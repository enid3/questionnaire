package com.github.enid3.questionnaire.data.dto.user.validation;

public class UserValidationConstraints {
    public static final int MIN_PASS_LENGTH = 6;
    public static final int MAX_PASS_LENGTH = 60;

    public static final int MIN_FIRST_NAME_LENGTH = 2;
    public static final int MAX_FIRST_NAME_LENGTH = 30;

    public static final int MIN_SECOND_NAME_LENGTH = 2;
    public static final int MAX_SECOND_NAME_LENGTH = 30;

    private UserValidationConstraints(){}
}
