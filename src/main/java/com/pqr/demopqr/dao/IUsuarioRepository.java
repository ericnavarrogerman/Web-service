package com.pqr.demopqr.dao;

import com.pqr.demopqr.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("Select distinct u From Usuario u " +
            "Inner Join u.rol r " +
            "Where r.id = ?1 " +
            "order by u.id desc ")
    List<Usuario> findAllByRolIdOrderByIdAsc(Long roleId);

}
