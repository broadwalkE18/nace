package com.nace.example.nacePOC;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nace.poc.csvhelper.NaceCSVHelper;
import com.nace.poc.dal.NaceData;
import com.nace.poc.dal.NaceRepository;
import com.nace.poc.restservice.NaceRestController;
import com.nace.poc.service.NaceFileService;
import com.nace.poc.service.NaceService;

@WebMvcTest
@ContextConfiguration(classes = NaceRestController.class)
class NacePocApplicationTests {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NaceService naceService;
	
	@MockBean
	private NaceFileService naceFileSevice;
		
	@MockBean
	private NaceRepository repository;
	
	@MockBean
	private NaceCSVHelper naceCSVHelper;
	
	private NaceData naceData;
	private List<NaceData> naceRecords;
	
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
	
		naceRecords = new ArrayList<>();
		naceRecords.add(naceData);
	}

	// Service tests
	@Test
	public void saveNaceRecord() {
		
		when(naceService.addNaceItem(naceData)).thenReturn(naceData);
		// when(repository.save(naceData)).thenReturn(naceData);
		NaceData foundData = naceService.addNaceItem(naceData);
		assertEquals(naceData, foundData);
	}
	
	@Test
	public void findNaceRecord() {
		
		when(repository.findById(naceData.getOrderId())).thenReturn(Optional.of(naceData));
		when(naceService.getNaceData(naceData.getOrderId())).thenReturn(Optional.of(naceData));
		Optional<NaceData> foundData = naceService.getNaceData(naceData.getOrderId());
		assertEquals(naceData, foundData.get());
	}
	
	// REST endpoint tests
	@Test
	public void naceEntryPresent() throws Exception {
		
		when(repository.findById(naceData.getOrderId())).thenReturn(Optional.of(naceData));
		when(naceService.getNaceData(naceData.getOrderId())).thenReturn(Optional.of(naceData));
		
		mockMvc.perform(MockMvcRequestBuilders.get("/api/orders/{naceID}", "398481")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void naceEntryNotPresent() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/api//orders/{naceID}", "398482")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
	}

	@Test
	public void naceEntryCreated() throws Exception {

		mockMvc.perform( MockMvcRequestBuilders
				.post("/api/order")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"orderId\":\"398483\",\"level\":1,\"code\":\"A\",\"parent\":\"1.1\",\"description\":\"Agriculture\",\"itemIncludes\":\"part2\",\"alsoIncludes\":\" \",\"rulings\":\" \",\"excludes\":\" \",\"refToISICRev4\":\"A\"}")
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(status().isCreated());
	}
	
	// File upload 
	@Test
	public void csvToNaceRecords() throws IOException {
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SampleNaceData.csv");
		when(naceCSVHelper.csvToNaceData(is)).thenReturn(naceRecords);
		List<NaceData> records = naceCSVHelper.csvToNaceData(is);
		assertEquals(1,records.size());
	}
	
	@Test 
	public void uploadNaceFile() throws Exception {
	
		MockMultipartFile firstFile = new MockMultipartFile("file", "NaceTestData.csv", "text/csv", "nace data".getBytes());
		mockMvc.perform( MockMvcRequestBuilders.multipart("/api/putNaceDetails").file(firstFile).contentType(MediaType.MULTIPART_FORM_DATA))
	                .andExpect(MockMvcResultMatchers.status().is(201)).andReturn();		
	}
}
