package com.anderson.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.helpdesk.domain.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {

}
