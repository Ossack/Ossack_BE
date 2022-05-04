package com.sparta.startup_be.repository;


import com.sparta.startup_be.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserid(Long userid);
    Optional<Favorite> findByUseridAndEstateid(Long userid, Long estateid);
    void deleteByUseridAndEstateid(Long userid, Long estateid);
    List<Favorite> findAllByEstateid(Long estateid);
}
