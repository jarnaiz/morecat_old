package org.emamotor.morecat.model;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author tanabe
 */
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Basic(optional = false)
    @NotEmpty(message = "Password not be empty")
    private String password;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "Email must not be empty")
    @Email
    private String email;

    @Basic(optional = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

}
