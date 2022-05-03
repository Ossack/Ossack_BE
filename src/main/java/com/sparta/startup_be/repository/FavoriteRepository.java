package com.sparta.startup_be.repository;


import com.sparta.startup_be.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

//    @Query("SELECT COUNT(favoriteId) FROM Favorite WHERE post.postId = :postId")
//    Long findByCountByPost(@Param("postId") Long postId);
//
    Optional<Favorite> findByPost_PostIdAndUser_Nickname(Long postId, String nickname);

    void deleteAllByPost_PostId(Long postId);

    Optional<Favorite> findByUser_UserEmailAndPost_PostId(String userEmail, Long postId);
}
