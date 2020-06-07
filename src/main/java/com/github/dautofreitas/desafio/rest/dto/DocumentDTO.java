package com.github.dautofreitas.desafio.rest.dto;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
	
	private Integer id;	
	private Integer idFile;
	@NotEmpty(message = "A propriedade file não pode ser nula")	
	byte[] file;	
	private String side;
}
