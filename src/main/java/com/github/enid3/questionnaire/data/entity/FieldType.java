package com.github.enid3.questionnaire.data.entity;

public enum FieldType {
    SINGLE_LINE_TEXT("Single line text"),
    MULTILINE_TEXT("Multiline text"),
    RADIO_BUTTON("Radio button"),
    CHECKBOX("Checkbox"),
    COMBOBOX("Combobox"),
    DATE("Date");


    private String definition;


    private FieldType(String description) {
        this.definition = description;
    }
}
