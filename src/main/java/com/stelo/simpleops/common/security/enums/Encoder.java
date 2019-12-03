package com.stelo.simpleops.common.security.enums;

import org.springframework.security.crypto.password.PasswordEncoder;

public enum Encoder {
    /**
     * @see PasswordEncoder
     */
    BCRYPT("bcrypt"),
    PBKDF2("pbkdf2"),
    SCRYPT("scrypt"),
    SHA256("sha256"),
    MD5("md5");
    private String code;

    Encoder(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
