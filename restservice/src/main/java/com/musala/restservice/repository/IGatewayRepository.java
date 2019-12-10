package com.musala.restservice.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.musala.restservice.model.Gateway;

@Repository
public interface IGatewayRepository  extends JpaRepository<Gateway, UUID>{

}
