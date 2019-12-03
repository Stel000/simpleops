package com.stelo.simpleops.common.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Table(name = "`user`")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Long id;

    @NotBlank
    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "nickname")
    private String nickname;

    @NotBlank
    @Size(min = 6, max = 20)
    @Pattern(regexp = "\\S*", message = "user.password.illegal")
    @Column(name = "`password`")
    private String password;
}