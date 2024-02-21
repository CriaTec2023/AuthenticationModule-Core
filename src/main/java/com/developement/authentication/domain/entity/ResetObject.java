package com.developement.authentication.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

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
