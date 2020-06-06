package com.github.dautofreitas.desafio.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.dautofreitas.desafio.domain.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Integer> {
	public Optional<Document> findBySideAndIdFile(String side, Integer idFile);
}
