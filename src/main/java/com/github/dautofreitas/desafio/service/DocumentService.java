package com.github.dautofreitas.desafio.service;

import com.github.dautofreitas.desafio.domain.entity.Document;

public interface DocumentService {
	
	 Document save(Document document);	 
	 Document findDocumentByIdFileAndSite(String side, Integer idFile);
	 String  documentDiff(Integer idFile);
}
