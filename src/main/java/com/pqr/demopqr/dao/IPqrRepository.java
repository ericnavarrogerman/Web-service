package com.pqr.demopqr.dao;

import com.pqr.demopqr.model.Pqr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPqrRepository extends JpaRepository<Pqr, Long> {

    @Query("Select distinct p From Pqr p " +
            "Inner Join fetch p.usuario u " +
            "Inner Join Fetch p.autor a " +
            "order by p.fechaCreacion desc ")
    List<Pqr> findAllOrderByFechaCreacionDesc();
}
