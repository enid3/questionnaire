package com.github.enid3.questionnaire.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Size(min=2, max=30)
    private String firstName;
    @NotBlank
    @Size(min=2, max=30)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotBlank
    @Size(min=8, max=100)
    private String password;

    private String phoneNumber;

}
