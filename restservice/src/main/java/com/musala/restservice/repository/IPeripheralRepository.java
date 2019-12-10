package com.musala.restservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.musala.restservice.model.Gateway;
import com.musala.restservice.model.Peripheral;

@Repository
public interface IPeripheralRepository extends JpaRepository<Peripheral, Long>{
  
	/**
    * 
    * @param g
    * @return
    */	
	List<Peripheral> findByGateway(Gateway g);
}
