package com.nace.poc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nace.poc.dal.NaceData;
import com.nace.poc.dal.NaceRepository;

@Service
public class NaceServiceImpl implements NaceService {
	
	@Autowired
	private NaceRepository repository;
	
	public NaceServiceImpl( NaceRepository repository) {
		this.repository = repository;
	}
	
	@Override
	public NaceData addNaceItem(NaceData naceItem) {
		return repository.save(naceItem);
	}

	@Override
	public Optional<NaceData> getNaceData(String orderId) {
		return repository.findById(orderId);
	}
}
