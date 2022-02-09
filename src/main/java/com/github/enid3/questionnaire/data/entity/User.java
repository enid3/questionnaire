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

    @Size(min=2, max=30)
    private String firstName;

    @Size(min=2, max=30)
    private String lastName;

    @JoinColumn(nullable = false)
    private String email;

    @JoinColumn(nullable = false)
    private String password;

    private String phoneNumber;

}
