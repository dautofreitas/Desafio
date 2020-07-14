package com.github.dautofreitas.Desafio.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.dautofreitas.desafio.domain.entity.Document;
import com.github.dautofreitas.desafio.domain.repository.DocumentRepository;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class DocumentRepositoryTest {
	
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	DocumentRepository docRepository;
	
	@Test
	@DisplayName("Deve  documento com Side e IdFile buscado")
	public void resturnDoceumentWithSideAndIdFileExists()
	{
		//Cenário
		Document savedDocument = new Document(null, 1, new byte[] { 10, 32, 44 }, "left");
	
		entityManager.persist(savedDocument);
		
		//Execução
		
		Optional<Document> documentFind = docRepository.findBySideAndIdFile(savedDocument.getSide(),savedDocument.getIdFile());
		
		//Verifcação
		
		assertThat(documentFind.isPresent()).isTrue();
		
	}
	

	@Test
	@DisplayName("Não deve documento com Side e IdFile buscado")
	public void notReturnDoceumentWithSideAndIdFileExists()
	{
		//Cenário
		String side = "left";
		Integer idFile = 1;
		//Execução
		
		Optional<Document> documentFind = docRepository.findBySideAndIdFile(side, idFile);
		
		//Verifcação
		
		assertThat(documentFind.isPresent()).isFalse();
		
	}
	
}
