package com.darkmatter.user.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "usuario2")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(value = "iduser")
    private String iduser;

    @NotNull
    @Column(value = "firstname")
    private String firstname;

    @Column(value = "secondname")
    private String secondname;

    @Column(value = "lastname")
    private String lastname;

    @Column(value = "surname")
    private String surname;

    @Column(value = "urlfoto")
    private String urlfoto;
}
