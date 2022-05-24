package com.nace.poc.dal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaceRepository extends JpaRepository<NaceData,String> {
}
