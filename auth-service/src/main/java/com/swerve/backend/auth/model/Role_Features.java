package com.swerve.backend.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swerve.backend.shared.model.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Role_Features extends BaseEntity<Long> {

    @ManyToOne(optional = false)
    @JoinColumn(name="systemFeature_ID", nullable=false)
    @JsonIgnore
    private System_Features systemFeatures;

    @ManyToOne(optional = false)
    @JoinColumn(name="role_ID", nullable=false)
    @JsonIgnore
    private Role role;
}
