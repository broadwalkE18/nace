package com.nace.example.nacePOC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.nace.poc.dal.NaceData;
import com.nace.poc.dal.NaceRepository;
import com.nace.poc.restservice.NaceRestController;
import com.nace.poc.service.NaceService;
import com.nace.poc.service.NaceServiceImpl;

@WebMvcTest
@ContextConfiguration(classes = NaceRestController.class)
class NacePocApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NaceService service;
	@MockBean
	private NaceData naceData;

	@Mock
	private NaceRepository repository;

	@BeforeEach
	public void setupDatabase() {
		naceData = new NaceData();

		naceData.setOrderId("398481");
		naceData.setLevel(1);
		naceData.setCode("A");
		naceData.setParent("1.1");
		naceData.setDescription("AGRICULTURE, FORESTRY AND FISHING");
		naceData.setItemIncludes("This section includes the exploitation of ...");
		naceData.setAlsoIncludes("");
		naceData.setRulings(null);
		naceData.setExcludes(null);
		naceData.setRefToISICRev4("A");
		
		service = new NaceServiceImpl(repository);
	}

	// Service tests
	@Test
	public void saveNaceRecord() {
		when(service.addNaceItem(naceData)).thenReturn(naceData);
		service.addNaceItem(naceData);
		verify(repository).save(naceData);
	}
	
	@Test
	public void findNaceRecord() {
		when(repository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(naceData));
		Optional<NaceData> foundData = service.getNaceData(naceData.getOrderId());
		assertEquals(naceData, foundData.get());
		verify(repository).findById(naceData.getOrderId());
	}
	
	// REST endpoint tests
	@Test
	public void naceEntryNotPresent() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/nace/orders/{naceID}", "398482").
				contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void naceEntryPresent() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/nace/orders/{naceID}", "398481").
				contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void naceEntryCreated() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
				.post("/nace/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"orderId\":\"398483\",\"level\":1,\"code\":\"A\",\"parent\":\"1.1\",\"description\":\"Agriculture\",\"itemIncludes\":\"part2\",\"alsoIncludes\":\" \",\"rulings\":\" \",\"excludes\":\" \",\"refToISICRev4\":\"A\"}")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated());
	}
}
