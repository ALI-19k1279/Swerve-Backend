package com.swerve.backend.auth.model;

import com.swerve.backend.shared.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class System_Features extends BaseEntity<Long> {

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "systemFeatures")
    private Set<Role_Features> roleFeatures=new HashSet<>();

}
