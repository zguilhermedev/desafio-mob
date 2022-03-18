package com.desafiomobi.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.desafiomobi.domain.Colaborador;
import com.desafiomobi.service.ColaboradorService;
import com.desafiomobi.util.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Colaborador Controller")
@RestController
@RequestMapping("colaborador")
@CrossOrigin("*")
public class ColaboradorController {

	@Autowired
	ColaboradorService service;
	
	@Operation(summary = "recupera todos os colaboradores")
	@GetMapping
	private ResponseEntity<?> findAll(
			@RequestParam(required = false) String nome,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		if(nome == null) {			
			return new ResponseEntity<Page<Colaborador>>(this.service.findAll(pageable), HttpStatus.OK);
		}
		
		try {			
			return new ResponseEntity<Page<Colaborador>>(this.service.findByNome(nome, pageable), HttpStatus.OK);
		} catch( Exception e ) {
			return new ResponseEntity<>(new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, e.getMessage() ), HttpStatus.NOT_FOUND);
		}
		 
	}
	
	@Operation(summary = "Cria um novo colaborador")
	@PostMapping
	private ResponseEntity<?> create(@RequestBody Colaborador colaborador) {
		try {			
			return new ResponseEntity<Colaborador>(this.service.save(colaborador), HttpStatus.CREATED);
		} catch( Exception e ) {
			return new ResponseEntity<>(new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage() ), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Operation(summary = "Remove um colaborador")
	@DeleteMapping("/{id}")
	private ResponseEntity<?> remove(@PathVariable("id") Long id) {
		try {			
			this.service.delete(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch( Exception e ) {
			return new ResponseEntity<>(new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.getMessage() ), HttpStatus.BAD_REQUEST);
		} 
	}
	
	@Operation(summary = "recupera todos os colaboradores por setor")
	@GetMapping("/findAllBySetor")
	private ResponseEntity<?> findAllBySetor(
			@RequestParam(required = false) String setor,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		if(setor == null) {			
			return new ResponseEntity<>(new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, "Descrição do setor é obrigatória" ), HttpStatus.BAD_REQUEST);
		}
		
		try {			
			return new ResponseEntity<Page<Colaborador>>(this.service.findBySetor(setor, pageable), HttpStatus.OK);
		} catch( Exception e ) {
			return new ResponseEntity<>(new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, e.getMessage() ), HttpStatus.NOT_FOUND);
		}
		 
	}
	
}
