package com.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import com.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
public class MemberRegistDTO {

    @NotNull
    @Length(max = 20)
    private String name;

    @NotNull
    private String password;

    @NotNull
    @Length(max = 100)
    private String email;

    public Member toEntity(){
        return Member.builder().email(email)
                .name(name)
                .password(password).build();
    }

    @Builder
    public MemberRegistDTO(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
