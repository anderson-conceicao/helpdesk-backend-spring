package com.anderson.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.helpdesk.domain.Tecnico;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
