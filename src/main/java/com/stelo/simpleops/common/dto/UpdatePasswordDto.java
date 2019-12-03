package com.stelo.simpleops.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdatePasswordDto {
    @NotBlank
    private String rawPassword;

    @NotBlank
    @Size(min = 6, max = 20)
    @Pattern(regexp = "\\S*", message = "user.password.illegal")
    private String newPassword;
}
