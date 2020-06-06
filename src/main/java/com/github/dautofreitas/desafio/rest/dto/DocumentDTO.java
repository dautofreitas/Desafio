package com.github.dautofreitas.desafio.rest.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
	
	Integer id;
	@Min(value = 1,message = "O id do arquivo deve ser no mínimo 1")
	@NotEmpty
	Integer idFile;
	@NotEmpty
	byte[] file;
	@NotEmpty
	String side;
}
