package com.blog.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String email;
    private String username;
    private String password;


    @ManyToMany
    @JoinTable(name = "user_roles",
              joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
              inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "id")
    )
    private Set<Role> roles;


}
