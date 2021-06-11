package com.example.newsfeed.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String desc;
    @Column(name = "publishDate")
    private Date date;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Post(String title, String desc, Date date, User user) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.user = user;
    }
}
