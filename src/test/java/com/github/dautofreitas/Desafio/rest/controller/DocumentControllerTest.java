package com.github.dautofreitas.Desafio.rest.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dautofreitas.desafio.domain.entity.Document;
import com.github.dautofreitas.desafio.rest.dto.DocumentDTO;
import com.github.dautofreitas.desafio.service.DocumentService;
import com.jayway.jsonpath.internal.function.text.Concatenate;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class DocumentControllerTest {
	String DOCUMENT_API = "/v1/diff/";

	@Autowired
	MockMvc mvc;

	@MockBean
	DocumentService documentService;

	@Order(1)
	@Test
	@DisplayName("Deve criar documento Left")
	public void createDocumentLeft() throws Exception {
		Integer idFile = 1;
		DocumentDTO newDocumentDto = new DocumentDTO(null, null, new byte[] { 10, 32, 44 }, null);

		Document savedDocument = new Document(1, idFile, new byte[] { 10, 32, 44 }, "left");

		BDDMockito.given(documentService.save(Mockito.any(Document.class))).willReturn(savedDocument);

		String objectJason = new ObjectMapper().writeValueAsString(newDocumentDto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(DOCUMENT_API + idFile + "/left/")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(objectJason);

		mvc.perform(request).andExpect(status().isCreated()).andExpect(jsonPath("id").isNotEmpty())
				.andExpect(jsonPath("file").isNotEmpty()).andExpect(jsonPath("side").value("left"));

	}

	@Order(2)
	@Test
	@DisplayName("Não deve criar documento Left")
	public void createIvalidDocumentLeft() throws Exception {
		Integer idFile = 1;
		DocumentDTO newDocumentDto = new DocumentDTO();

		String objectJason = new ObjectMapper().writeValueAsString(newDocumentDto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(DOCUMENT_API + idFile + "/left/")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(objectJason);

		mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("type").value("erro"))
				.andExpect(jsonPath("messages", hasItem("A propriedade file não pode ser nula")));

	}

	@Order(3)
	@Test
	@DisplayName("Deve criar documento Right")
	public void createDocumentRight() throws Exception {
		Integer idFile = 1;
		DocumentDTO newDocumentDto = new DocumentDTO(null, null, new byte[] { 10, 32, 44 }, null);

		Document savedDocument = new Document(1, idFile, new byte[] { 10, 32, 44 }, "right");

		BDDMockito.given(documentService.save(Mockito.any(Document.class))).willReturn(savedDocument);

		String objectJason = new ObjectMapper().writeValueAsString(newDocumentDto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(DOCUMENT_API + idFile + "/right/")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(objectJason);

		mvc.perform(request).andExpect(status().isCreated()).andExpect(jsonPath("id").isNotEmpty())
				.andExpect(jsonPath("file").isNotEmpty()).andExpect(jsonPath("side").value("right"));

	}

	@Order(4)
	@Test
	@DisplayName("Não deve criar documento Right")
	public void createIvalidDocumentRight() throws Exception {
		Integer idFile = 1;
		DocumentDTO newDocumentDto = new DocumentDTO();
		String objectJason = new ObjectMapper().writeValueAsString(newDocumentDto);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(DOCUMENT_API + idFile + "/left/")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(objectJason);

		mvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("type").value("erro"))
				.andExpect(jsonPath("messages", hasItem("A propriedade file não pode ser nula")));
	}

	@Order(6)
	@Test
	@DisplayName("Deve informar que tamanho dos documentos são diferentes")
	public void resultDiffDocumentEquals() throws Exception {

		// Cenário
		Integer idFile = 1;
		String message = String.format("Documentos %s idênticos", idFile);
		
		BDDMockito.given(documentService.documentDiff(Mockito.any(Integer.class)))
				.willReturn(message);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(DOCUMENT_API + idFile);
		
		// Execução
		ResultActions result = mvc.perform(request);

		// Veriicação
		result.andExpect(status().isOk())
				.andExpect(jsonPath("type").value("success"))
				.andExpect(jsonPath("messages", hasItem(message)));
	}


	@Order(7)
	@Test
	@DisplayName("Deve informar que tamanho dos documentos são diferentes")
	public void resultDiffDocumentDifferentSize() throws Exception {

		// Cenário
		Integer idFile = 1;
		String message = String.format("Documentos %s com tamanhos diferentes", idFile);
		
		BDDMockito.given(documentService.documentDiff(Mockito.any(Integer.class)))
				.willReturn(message);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(DOCUMENT_API + idFile);
		
		// Execução
		ResultActions result = mvc.perform(request);

		// Veriicação
		result.andExpect(status().isOk())
				.andExpect(jsonPath("type").value("success"))
				.andExpect(jsonPath("messages", hasItem(message)));
	}
	

	@Order(8)
	@Test
	@DisplayName("Deve informar que documentos são diferentes na posição")
	public void resultDiffDocumentDifferentInIndex() throws Exception {

		// Cenário
		Integer idFile = 1;
		String message = String.format("Documentos %s diferentes na posição %s",idFile, 2);
		
		BDDMockito.given(documentService.documentDiff(Mockito.any(Integer.class)))
				.willReturn(message);

		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(DOCUMENT_API + idFile);
		
		// Execução
		ResultActions result = mvc.perform(request);

		// Veriicação
		result.andExpect(status().isOk())
				.andExpect(jsonPath("type").value("success"))
				.andExpect(jsonPath("messages", hasItem(message)));
	}
	
}
