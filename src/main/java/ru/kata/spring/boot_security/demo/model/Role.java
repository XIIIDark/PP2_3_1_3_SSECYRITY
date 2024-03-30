package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity

@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", unique = true)
    private String nameRole;

    public Role(long id, String nameRole) {
        this.id = id;
        this.nameRole = nameRole;
    }

    public Role() {

    }

    @Override
    public String toString() {
        return nameRole.replace("ROLE_", "");
    }

    @Override
    public String getAuthority() {
        return nameRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }
}
