package com.sparta.startup_be.repository;

import com.sparta.startup_be.model.Estate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface EstateRepository extends JpaRepository<Estate, Long> {
//    List<Estate> findAllByFloor(int floor);
    List<Estate> findAllByCity(String city);
//    List<Estate> findAllByMonthly(String monthly);
    int countAllByMonthlyAndCity(String monthly, String city);
//     searchAllByCity(String city);
    @Query("select u from Estate u where u.city like %:keyword%")
    List<Estate> searchAllByCity(@Param("keyword")String city);
//    @Query(find)

}
