package com.developement.authentication.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ResetObject {

    private String resetToken;
    private boolean resetTokenValid;
    private LocalDateTime resetTokenExpireDate;

}
