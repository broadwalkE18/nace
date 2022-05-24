package com.nace.poc.restservice;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nace.poc.dal.NaceData;
import com.nace.poc.service.NaceService;

// @ApiResponses(value = { @ApiResponse(code = 200, message = "OK"),
//		@ApiResponse(code = 201, message = "Created successfully"),
//		@ApiResponse(code = 401, message = "not authorized"), 
//		@ApiResponse(code = 403, message = "forbidden"),
//		@ApiResponse(code = 404, message = "not found") })

@RestController
@RequestMapping("/nace")
public class NaceRestController {

	private NaceService naceService;
	
	@Autowired
	public NaceRestController(NaceService naceService) {
		this.naceService = naceService;
	}
	// @ApiOperation(value = "/orders/naceID", nickname = "getNaceItem")
	@RequestMapping(value = "/orders/{naceID}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<NaceData> getNaceDetails(@PathVariable String naceID) {
		Optional<NaceData> data = naceService.getNaceData(naceID);
		if (data.isEmpty()) {
			return new ResponseEntity<NaceData>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<NaceData>(data.get(), HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<NaceData> addNaceRecord(@RequestBody NaceData naceEntry) {
		return new ResponseEntity<NaceData>(naceService.addNaceItem(naceEntry), HttpStatus.CREATED);
	}
}
