package com.github.dautofreitas.desafio.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Document {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;
	@Column(name = "id_file")
	Integer idFile;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	byte[] file;
	String side;
	
	
}
