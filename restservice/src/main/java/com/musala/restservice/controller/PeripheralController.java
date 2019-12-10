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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.musala.restservice.model.Gateway;
import com.musala.restservice.model.Peripheral;
import com.musala.restservice.repository.IGatewayRepository;
import com.musala.restservice.repository.IPeripheralRepository;
import exception.CustomResponseException;

@RestController
@RequestMapping("/api")
public class PeripheralController {

	@Autowired
	IGatewayRepository gatewayrepository;
	@Autowired
	IPeripheralRepository peripheralrepository;

	/**
	 * Method to post a peripheral in specific gateway
	 * @param gateway_id
	 * @param peripheral
	 * @return
	 */
	@PostMapping("/gateway/{gateway_id}/peripheral")
	public Peripheral createPeripheral(@PathVariable UUID gateway_id, @Valid @RequestBody Peripheral peripheral) {
		Optional <Gateway> g = gatewayrepository.findById(gateway_id);
		if(g.isPresent() ) {
			if(g.get().getPeripheral().size() < 10) {
				peripheral.setGateway(g.get());
				return peripheralrepository.save(peripheral);
			}
			else {
				throw new CustomResponseException("Gateway can not have more than 10 elements " );
			}
		}
		else {
			throw new CustomResponseException("Gateway with id:"+gateway_id+"can not be found" );
		}
	}

	/**
	 * Method to get all peripherals from specific gateway
	 * @param gateway_id
	 * @return
	 */
	@GetMapping("/gateway/{gateway_id}/peripheral")
	public List<Peripheral> getPeripherals(@PathVariable UUID gateway_id) {
		Optional <Gateway> g = gatewayrepository.findById(gateway_id);
		if(g.isPresent() ) {
			return peripheralrepository.findByGateway(g.get());
		}
		else {
			throw new CustomResponseException("Gateway with id:"+gateway_id+"can not be found" );
		}
	}

	/**
	 * Method to get one specific peripheral from specific gateway
	 * @param gateway_id
	 * @param id
	 * @return
	 */
	@GetMapping("/gateway/{gateway_id}/peripheral/{id}")
	public Peripheral getPeripherals(@PathVariable UUID gateway_id,@PathVariable long id) {
		Optional <Gateway> g = gatewayrepository.findById(gateway_id);
		if(g.isPresent() ) {
			Optional<Peripheral>p = peripheralrepository.findById(id);
			if(p.isPresent())
				return p.get();
			else
				throw new CustomResponseException("Peripheral with id:"+id+"can not be found" );
		}
		else {
			throw new CustomResponseException("Gateway with id:"+gateway_id+"can not be found" );
		}
	}
	
	/**
	 * Method to delete one specific peripheral from specific gateway
	 * @param gateway_id
	 * @param peripheralId
	 * @return
	 */
	@DeleteMapping("/gateway/{gateway_id}/peripheral/{peripheralId}")
	public String deletePeripheral(@PathVariable UUID gateway_id,
			@PathVariable Long peripheralId) {

		return peripheralrepository.findById(peripheralId)
				.map(peripheral -> {
					peripheralrepository.delete(peripheral);
					return "Deleted Successfully!";
				}).orElseThrow(() -> new CustomResponseException("gateaway not found with id" + gateway_id));
	}
}


