package com.github.dautofreitas.desafio.service.impl;

import org.springframework.stereotype.Service;

import com.github.dautofreitas.desafio.domain.entity.Document;
import com.github.dautofreitas.desafio.domain.repository.DocumentRepository;
import com.github.dautofreitas.desafio.exception.BisnessRuleExeption;
import com.github.dautofreitas.desafio.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

	private DocumentRepository documentRepository;

	public DocumentServiceImpl(DocumentRepository documentRepository) {

		this.documentRepository = documentRepository;
		
	}
	
	
	public Document save(Document document) {
		
		return documentRepository.save(document);
		
	}	
	
	public Document findDocumentByIdFileAndSite(String side, Integer idFile) {

		return documentRepository.findBySideAndIdFile(side, idFile)
				.orElseThrow(() -> new BisnessRuleExeption(String.format("Documento com Id: %s e lado: %s não encontrados",idFile,side)));
		
		
	}
	
	public String  documentDiff(Integer idFile)
	{
		Document documentRight = documentRepository.findBySideAndIdFile("right", idFile)
								.orElseThrow(() -> new BisnessRuleExeption(String.format("Documento com Id: %s e lado: right não encontrado",idFile)));
		
		Document documentLeft = documentRepository.findBySideAndIdFile("left", idFile)
				.orElseThrow(() -> new BisnessRuleExeption(String.format("Documento com Id: %s e lado:left não encontrado",idFile))); 
		
		if (documentRight.getFile().length != documentLeft.getFile().length )
		{
			return String.format("Documentos %s com tamanhos diferentes", documentRight.getIdFile());
		}
		
		int position = verifyMatchingBetweenTowByteArray(documentRight.getFile(), documentLeft.getFile());
		
		if(position != -1)
		{
			return String.format("Documentos %s diferentes na posição %s", documentRight.getIdFile(),position);
		}
		
		
		return String.format("Documentos %s idênticos", documentRight.getIdFile());
		
	}
	
	private int  verifyMatchingBetweenTowByteArray(byte[] fileRight, byte[] fileLeft)
	{
		for (int index = 0; index < fileRight.length; index++)
		{
			if(Byte.compare(fileLeft[index],fileRight[index])!=0)
			{
				return index;
			}
		}
		
		return -1;
	}
}
