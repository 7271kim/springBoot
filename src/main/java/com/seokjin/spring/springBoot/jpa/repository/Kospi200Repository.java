package com.seokjin.spring.springBoot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.seokjin.spring.springBoot.jpa.KospiModel;

public interface Kospi200Repository extends JpaRepository<KospiModel, Long> { }
