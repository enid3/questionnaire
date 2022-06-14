package com.github.enid3.questionnaire.data.entity;

import com.github.enid3.questionnaire.data.dto.user.validation.UserConstraints;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(min= UserConstraints.MIN_FIRST_NAME_LENGTH,
            max= UserConstraints.MAX_FIRST_NAME_LENGTH)
    private String firstName;

    @Size(min= UserConstraints.MIN_LAST_NAME_LENGTH,
            max= UserConstraints.MAX_LAST_NAME_LENGTH)
    private String lastName;

    @NotNull
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String password;

    @Column(nullable = true)
    private String phoneNumber;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Questionnaire> questionnaires =  new ArrayList<>();

}
