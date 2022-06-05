package com.nace.poc.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nace.poc.csvhelper.NaceCSVHelper;
import com.nace.poc.dal.NaceData;
import com.nace.poc.dal.NaceRepository;

@Component
@Service
public class NaceFileServiceImpl implements NaceFileService {
	
	@Autowired
	private NaceCSVHelper naceCSVHelper;
	
	private NaceRepository repository;
	
	@Autowired
	public NaceFileServiceImpl(NaceRepository naceRepository) {
		this.repository = naceRepository;
	}

	@Override
	public void save(MultipartFile file) {
		try {
			List<NaceData> naceRecs = naceCSVHelper.csvToNaceData(file.getInputStream());
			repository.saveAll(naceRecs);
		} catch (IOException e) {
			throw new RuntimeException("Failed to store csv data to db: " + e.getMessage());
		}
	}

}
