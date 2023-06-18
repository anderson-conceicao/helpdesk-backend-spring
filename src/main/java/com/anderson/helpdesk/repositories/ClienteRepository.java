package com.anderson.helpdesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anderson.helpdesk.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
