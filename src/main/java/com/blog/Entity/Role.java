package com.blog.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> user;
}
