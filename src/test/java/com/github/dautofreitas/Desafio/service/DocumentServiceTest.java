package com.github.dautofreitas.Desafio.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;

import java.lang.reflect.Method;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.github.dautofreitas.desafio.domain.entity.Document;
import com.github.dautofreitas.desafio.domain.repository.DocumentRepository;
import com.github.dautofreitas.desafio.service.DocumentService;
import com.github.dautofreitas.desafio.service.impl.DocumentServiceImpl;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DocumentServiceTest {

	DocumentServiceImpl documentService;

	@MockBean
	DocumentRepository documentRepository;

	@BeforeEach
	public void setUp() {
		documentService = new DocumentServiceImpl(documentRepository);
	}

	@Test
	@DisplayName("Deve salvar Documento")
	public void saveDocument() {
		Document newDocument = new Document(null, 1, new byte[] { 23, 23, 44 }, "left");

		Mockito.when(documentRepository.save(newDocument))
				.thenReturn(new Document(1, 1, new byte[] { 23, 23, 44 }, "left"));

		Document savedDocumentDto = documentService.save(newDocument);

		assertThat(savedDocumentDto.getId()).isNotNull();
		assertThat(savedDocumentDto.getFile()).isNotNull();
		assertThat(savedDocumentDto.getSide()).isEqualTo("left");
		assertThat(savedDocumentDto.getIdFile()).isEqualTo(1);

	}

	@Test
	@DisplayName("Deve informar que documentos são iguais")
	public void DiffDocumentEquals() {
		Document documentRight = new Document(1, 1, new byte[] { 23, 23, 44 }, "right");
		Document documentLeft = new Document(1, 1, new byte[] { 23, 23, 44 }, "left");

		Mockito.when(documentRepository.findBySideAndIdFile("right", documentRight.getIdFile()))
				.thenReturn(Optional.ofNullable(documentRight));

		Mockito.when(documentRepository.findBySideAndIdFile("left", documentLeft.getIdFile()))
				.thenReturn(Optional.ofNullable(documentRight));

		String result = documentService.documentDiff(1);

		assertThat(result).isEqualTo(String.format("Documentos %s idênticos", documentRight.getIdFile()));

	}

	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Deve informar que tamanho dos documentos são diferentes")
	public void DiffDocumentDifferentSize() {
		Document documentRight = new Document(1, 1, new byte[] { 23, 23, 44 }, "right");
		Document documentLeft = new Document(1, 1, new byte[] { 23, 23, 44, 45 }, "left");

		Mockito.when(documentRepository.findBySideAndIdFile(Mockito.any(String.class), Mockito.any(Integer.class)))
				.thenReturn(Optional.ofNullable(documentRight), Optional.ofNullable(documentLeft));
		String result = documentService.documentDiff(1);

		assertThat(result).isEqualTo(String.format("Documentos %s com tamanhos diferentes", documentRight.getIdFile()));

	}

	
	@SuppressWarnings("unchecked")
	@Test
	@DisplayName("Deve informar que documentos são diferentes na posição")
	public void DiffDocumentDifferentInIndex() {
		Document documentRight = new Document(1, 1, new byte[] { 23, 23, 44 }, "right");
		Document documentLeft = new Document(1, 1, new byte[] { 23, 23, 46 }, "left");
		int diferrentIdex = 2;
		
	
		Mockito.when(documentRepository.findBySideAndIdFile(Mockito.any(String.class), Mockito.any(Integer.class)))
				.thenReturn(Optional.ofNullable(documentRight), Optional.ofNullable(documentLeft));


		String result = documentService.documentDiff(1);

		assertThat(result).isEqualTo(
				String.format("Documentos %s diferentes na posição %s", documentRight.getIdFile(), diferrentIdex));

	}

	@Test
	@DisplayName("Deve verificar se dois Arrays de Bytes são iguais")
	public void verifyMatchingBetweenTowByteArray() throws Exception, SecurityException {
		// cenário

		byte[] byteArrayFist = new byte[] { 23, 23, 44 };
		byte[] byteArraySecund = new byte[] { 23, 23, 44 };

		Method method = DocumentServiceImpl.class.getDeclaredMethod("verifyMatchingBetweenTowByteArray", byte[].class,
				byte[].class);
		method.setAccessible(true);

		// execução

		int result = (int) method.invoke(documentService, byteArrayFist, byteArraySecund);
		// verificação

		assertThat(result).isEqualTo(-1);

	}

}