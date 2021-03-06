package com.example.newsfeed.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "user")
    private List<Post> posts=new ArrayList<>();
    @ManyToMany
    @JoinTable(name="follow",
            joinColumns=@JoinColumn(name="userID"),
            inverseJoinColumns=@JoinColumn(name="followID")
    )
    private List<User> following=new ArrayList<>();;

    @ManyToMany
    @JoinTable(name="follow",
            joinColumns=@JoinColumn(name="followID"),
            inverseJoinColumns=@JoinColumn(name="userID")
    )
    private List<User> followers=new ArrayList<>();;

    public User(String name) {
        this.name = name;
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
