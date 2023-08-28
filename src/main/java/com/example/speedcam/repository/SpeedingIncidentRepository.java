package com.example.speedcam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.speedcam.entity.OwnerEntity;
import com.example.speedcam.entity.SpeedingIncidentEntity;

public interface SpeedingIncidentRepository extends JpaRepository<SpeedingIncidentEntity, Long> { 

	
}

