package com.nace.poc.service;

import java.util.Optional;

import com.nace.poc.dal.NaceData;

public interface NaceService {
	NaceData addNaceItem(NaceData naceData);

	Optional<NaceData> getNaceData(String orderId);
}
