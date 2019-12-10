package com.musala.restservice;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musala.restservice.model.Gateway;
import com.musala.restservice.model.Peripheral;
import com.musala.restservice.model.StatusEnum;
import com.musala.restservice.repository.IGatewayRepository;
import com.musala.restservice.repository.IPeripheralRepository;
import exception.CustomResponseException;

@RunWith(SpringRunner.class)
@SpringBootTest()
class RestserviceApplicationTests {

	String host="http://localhost:8087/api/gateway/";
	@Autowired
	IGatewayRepository gatewayrepository;

	@Autowired
	IPeripheralRepository peripheralrepository;
	
	/**
	 * test to validate the gatewayRepository method save gateway
	 */
	@Test
	public void createGateway() {
		
		Gateway g = new Gateway();
		g.setName("gateway1");
		g.setAddress("10.0.0.21");
		Gateway newgateway = gatewayrepository.save(g);
		assertTrue(newgateway.getName().equals("gateway1"));
		assertTrue(newgateway.getAddress().equals("10.0.0.21"));
	}
	
	/**
	 * test to validate the peripheralRepository method save peripheral
	 */
	@Test
	public void createPeripheral() {
		
		Gateway g = new Gateway();
		g.setName("gateway1");
		g.setAddress("10.0.0.21");
		Gateway newgateway = gatewayrepository.save(g);
		Peripheral p = new Peripheral();
		p.setVendor("vendor1");
		p.setStatus(StatusEnum.online);
		p.setGateway(newgateway);
		Peripheral newperipheral = peripheralrepository.save(p);
		assertTrue(newperipheral.getVendor().equals("vendor1"));
		assertTrue(newperipheral.getStatus().name().equals("online"));
	}
	
	/**
	 * test to validate the method GET(rest) of gateway
	 * @throws URISyntaxException
	 */
	@Test
	public void testGetGateway() throws URISyntaxException 
	{
	    RestTemplate restTemplate = new RestTemplate();
	    final String baseUrl = host;
	    URI uri = new URI(baseUrl);
	    ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);   
	    assertEquals(200, result.getStatusCodeValue());	   
	}
	//test to validate the Gateway method POST(rest) running one gateway insertion
	@Test
	public void testPostGateway() throws URISyntaxException 
	{
	    RestTemplate restTemplate = new RestTemplate();
	    final String baseUrl = host;
	    URI uri = new URI(baseUrl);
	    Gateway gateway = new Gateway(null, "g1", "10.0.0.1", null);	     	     
	    HttpHeaders headers = new HttpHeaders();	 
	    HttpEntity<Gateway> request = new HttpEntity<>(gateway, headers);	     
	    try
	    {	       
	        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);	        
	        assertEquals(200, result.getStatusCodeValue());
	    }
	    catch(HttpClientErrorException ex) 
	    {
	        //Verify bad request and missing header
	       assertEquals(400, ex.getRawStatusCode());
	       assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
	    }

	}
	
	/**
	 * Test to evaluate if the peripheral method POST validate no more than 10 peripheral insertions in the same gateway
	 * @throws URISyntaxException
	 */
	@Test
	public void testPostPeripheral() throws URISyntaxException 
	{		
		int cont = 0;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		final String baseUrl1 =host;
		Gateway g1 = new Gateway(null, "gatewayTest", "10.0.0.1", null);
		HttpEntity<Gateway> request = new HttpEntity<>(g1, headers);
		URI uri = new URI(baseUrl1); 	
		ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);	
		try {
			System.out.println(result);
			Gateway gatewayResult= new ObjectMapper().readValue(result.getBody(), Gateway.class);	     
			
			for (int i = 0; i < 11; i++) {
				final String baseUrl = host+gatewayResult.getId().toString()+"/peripheral";
				URI uri1 = new URI(baseUrl);
				Peripheral p = new Peripheral();
				p.setVendor("vG");
				p.setStatus(StatusEnum.online);

				HttpEntity<Peripheral> request1 = new HttpEntity<>(p, headers);	  

				System.out.println(uri1.toURL());
				restTemplate.postForEntity(uri1, request1, String.class);		
				cont++;	
			}
		} catch (JsonProcessingException  e) {				
		} catch ( CustomResponseException e1) {
		} catch ( Exception e1) {	
		}
		finally {
			assertEquals(10, cont);
		}
	}			

}
