package com.musala.restservice.model;


import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.validation.annotation.Validated;


@Entity
@Table(name = "gateway")
@Validated
public class Gateway implements Serializable{

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")

	private UUID id;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	private String name;
	
	/**
	 * Validation of address attribute using Pattern annotation
	 */
	@NotNull
	@Pattern(regexp = "^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$", message="Wrong ip address structure")
	private String address;
	
	@OneToMany(mappedBy = "gateway", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Size(max = 10)
	@Valid
	private List<Peripheral> peripheral;
	
	public List<Peripheral> getPeripheral() {
		return this.peripheral;
	}

	public void setPeripheral(List<Peripheral> peripheral) {
		this.peripheral = peripheral;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Gateway() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param id
	 * @param name
	 * @param address
	 * @param peripheral
	 */
	public Gateway(UUID id, String name, String address, List<Peripheral> peripheral) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.peripheral = peripheral;
	}	
}
