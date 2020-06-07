package com.github.dautofreitas.desafio.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.dautofreitas.desafio.domain.entity.Document;
import com.github.dautofreitas.desafio.exception.BisnessRuleExeption;
import com.github.dautofreitas.desafio.rest.controller.util.MessageApi;
import com.github.dautofreitas.desafio.rest.dto.DocumentDTO;
import com.github.dautofreitas.desafio.service.DocumentService;

@RestController
@RequestMapping("v1/diff")
public class DocumentController {
	private DocumentService documentService;
	private ModelMapper modelMapper; 
	
	public DocumentController(DocumentService documentService, ModelMapper modelMapper) {		
		this.documentService = documentService;
		this.modelMapper = modelMapper;
	}

	@PostMapping("{idFile}/right/")
	@ResponseStatus(code = HttpStatus.CREATED)	
	public DocumentDTO  right(@PathVariable Integer idFile, @RequestBody DocumentDTO documentDto)
	{
		String side ="right";		
		documentDto.setIdFile(idFile);
		documentDto.setSide(side);
		
		Document document  = modelMapper.map(documentDto,Document.class);	
		document = documentService.save(document);		
		return modelMapper.map(document,DocumentDTO.class);
		
	}
	
	@PostMapping("{idFile}/left/")
	@ResponseStatus(code = HttpStatus.CREATED)	
	public DocumentDTO  left(@PathVariable Integer idFile,@Valid @RequestBody DocumentDTO documentDto)
	{
		String side ="left";
		documentDto.setIdFile(idFile);
		documentDto.setSide(side);
		
		Document document  = modelMapper.map(documentDto,Document.class);	
		document = documentService.save(document);		
		return modelMapper.map(document,DocumentDTO.class);
		
	}
	
	@GetMapping("{idFile}")
	public MessageApi  result(@PathVariable Integer idFile)
	{
		
		String result =  documentService.documentDiff(idFile);
		List<String> messages = new ArrayList<String>(); 
		messages.add(result);		
		return new MessageApi("success",messages);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)	
	public MessageApi handleValidationExceptions(MethodArgumentNotValidException exception)
	{
		List<String> messages = new ArrayList<String>(); 
		exception.getBindingResult().getAllErrors().forEach( erro -> messages.add(erro.getDefaultMessage()));
		
		return new MessageApi("erro",messages);
	}
	
	@ExceptionHandler(BisnessRuleExeption.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)	
	public MessageApi handleValidationExceptions(BisnessRuleExeption exception)
	{
		List<String> messages = new ArrayList<String>(); 
		messages.add(exception.getMessage());		
		return new MessageApi("erro",messages);
	}
	
}
