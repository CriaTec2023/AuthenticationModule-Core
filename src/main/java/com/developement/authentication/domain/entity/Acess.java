package com.developement.authentication.domain.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Acess {
    @ElementCollection
    private List<String> permission;
    @ElementCollection
    private List<String> AvailiableCompanies;
}
