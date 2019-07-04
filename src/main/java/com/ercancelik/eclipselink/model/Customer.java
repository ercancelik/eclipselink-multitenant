package com.ercancelik.eclipselink.model;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantTableDiscriminator;
import org.eclipse.persistence.annotations.TenantTableDiscriminatorType;
import org.eclipse.persistence.config.PersistenceUnitProperties;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "CUSTOMER")
@Multitenant(value = MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type = TenantTableDiscriminatorType.SUFFIX, contextProperty = PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;
    private String lastName;
}
