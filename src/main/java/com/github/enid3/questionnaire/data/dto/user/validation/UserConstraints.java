package com.github.enid3.questionnaire.data.dto.user.validation;

public class UserConstraints {
    public static final int MIN_PASS_LENGTH = 6;
    public static final int MAX_PASS_LENGTH = 60;

    public static final int MIN_FIRST_NAME_LENGTH = 2;
    public static final int MAX_FIRST_NAME_LENGTH = 30;

    public static final int MIN_LAST_NAME_LENGTH = 2;
    public static final int MAX_LAST_NAME_LENGTH = 30;

    private UserConstraints(){}
}
