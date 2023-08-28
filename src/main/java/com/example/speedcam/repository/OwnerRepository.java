package com.example.speedcam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.speedcam.entity.OwnerEntity;


public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> { 

	public List<OwnerEntity> findByRegistryNo(String registryNo);
}
