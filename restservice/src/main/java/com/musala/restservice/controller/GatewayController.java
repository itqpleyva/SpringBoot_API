package com.musala.restservice.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.musala.restservice.model.Gateway;
import com.musala.restservice.repository.IGatewayRepository;
import exception.CustomResponseException;

@RestController
@RequestMapping(path = "/api")
public class GatewayController {

	@Autowired
	IGatewayRepository gatewayrepository;

	/**
	 * Method to post a gateway in DB
	 * @param gateway
	 * @return
	 */
	
	@PostMapping("/gateway")
	public Gateway createGateway(@Valid @RequestBody Gateway gateway) {
		return gatewayrepository.save(gateway);
	}
	
	/**
	 * Method to get a list of gateways
	 * @return
	 */
	@GetMapping("/gateway")
	public List<Gateway> getAllGateway() {
		return gatewayrepository.findAll();
	}
	
	/**
	 * Method to get gateway by id
	 * @param id
	 * @return
	 */
	@GetMapping("/gateway/{id}")
	public Gateway getById(@PathVariable UUID id) {
		Optional<Gateway>  g=gatewayrepository.findById(id);
		if(g.isPresent())
			return g.get();
		else
			throw new CustomResponseException("Gateway not found with id " + id);
	}
	/**
	 * Method to update gateway by id
	 * @param id
	 * @param gatewayUpdated
	 * @return
	 */
	@PutMapping("/gateway/{id}")
	public Gateway updateGateway(@PathVariable UUID id, @Valid @RequestBody Gateway gatewayUpdated) {
		return gatewayrepository.findById(id)
				.map(gateway -> {
					gateway.setName(gatewayUpdated.getName());
					gateway.setAddress(gatewayUpdated.getAddress());
					return gatewayrepository.save(gateway);
				}).orElseThrow(() -> new CustomResponseException("Gateway not found with id " + id));
	}
	/**
	 * Method to delete gateway by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/gateway/{id}")
	public String deleteGateway(@PathVariable UUID id) {
		return  gatewayrepository.findById(id)
				.map(gateway -> {
					gatewayrepository.delete(gateway);
					return "Delete Successfully!";
				}).orElseThrow(() -> new CustomResponseException("Gateway not found with id " + id));
	}
}	

