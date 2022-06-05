package com.nace.poc.restservice;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nace.poc.csvhelper.NaceCSVHelper;
import com.nace.poc.dal.NaceData;
import com.nace.poc.service.NaceFileService;
import com.nace.poc.service.NaceService;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "201", description = "Created successfully"),
		@ApiResponse(responseCode = "401", description = "not authorized"),
		@ApiResponse(responseCode = "403", description = "forbidden"),
		@ApiResponse(responseCode = "404", description = "not found") })

@RestController
@RequestMapping("/api")
public class NaceRestController {

	private NaceFileService naceFileService;	
	private NaceService naceService;

	@Autowired
	public NaceRestController(NaceService naceService, NaceFileService naceFileService) {
		this.naceService = naceService;
		this.naceFileService = naceFileService;
	}

	@ApiOperation(value = "/orders/naceID", nickname = "getNaceItem")
	@RequestMapping(value = "/orders/{naceID}", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponseEntity<NaceData> getNaceDetails(@PathVariable(name="naceID", required=true) String naceID) {
		Optional<NaceData> data = naceService.getNaceData(naceID);
		if (data.isEmpty()) {
			return new ResponseEntity<NaceData>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<NaceData>(data.get(), HttpStatus.OK);
		}
	}

	@ApiOperation(value = "/order", nickname = "createNaceItem")
	@RequestMapping(value = "/order", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<NaceData> addNaceRecord( @Valid @RequestBody NaceData naceEntry) {
		return new ResponseEntity<NaceData>(naceService.addNaceItem(naceEntry), HttpStatus.CREATED);
	}
	
	@ApiOperation(value = "/putNaceDetails", nickname = "uploadNaceDetails")
	@RequestMapping(value = "/putNaceDetails", method = RequestMethod.POST)
	public ResponseEntity<String> uploadNaceFile(@RequestParam(name="file", required=true)  MultipartFile file) {
		String message = "";

		if (NaceCSVHelper.hasCSVFormat(file)) {
			try {
				naceFileService.save(file);

				message = "Uploaded file successfully: " + file.getOriginalFilename();
				return new ResponseEntity<String>(message, HttpStatus.CREATED);
			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + "!";
				return new ResponseEntity<String>(message, HttpStatus.EXPECTATION_FAILED);
			}
		}
		message = "Please enter a csv file!";
		return new ResponseEntity<String>(message, HttpStatus.BAD_REQUEST);
	}
}
