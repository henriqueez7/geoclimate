package com.henrique.geoclimate.repository;

import com.henrique.geoclimate.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findAllByOrderByDataHoraDesc();

}