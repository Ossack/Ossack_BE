package com.sparta.startup_be.repository;


import com.sparta.startup_be.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUserid(Long userid);
    Optional<Favorite> findByUseridAndEstateid(Long userid, Long estateid);
    void deleteByUseridAndEstateid(Long userid, Long estateid);
    List<Favorite> findAllByEstateid(Long estateid);


    boolean existsByEstateidAndUserid(Long estateid, Long userid);

    @Query(nativeQuery = true,value = "select a.estateid, count(a.userid) as cnt ,b.* from favorite a, estate b Where a.estateid = b.id group by estateid order by cnt desc limit 5")
    List<Map<String,Object>> countUseridQuery();
}
