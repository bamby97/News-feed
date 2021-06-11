package com.example.newsfeed.repository;

import com.example.newsfeed.models.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostsRepository extends JpaRepository<Post,Long> {

    @Query("select p from Post as p "
            + "JOIN FETCH p.user u "
            + "JOIN FETCH u.followers f "
            + "where f.id = :uid ")
    Optional<List<Post>> getNewsFeedFromUser(Long uid, Pageable pageable);
}
