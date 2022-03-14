package com.desafiomobi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.desafiomobi.domain.Colaborador;
import com.desafiomobi.service.ColaboradorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Colaborador Controller")
@RestController
@RequestMapping("colaborador")
public class ColaboradorController {

	@Autowired
	ColaboradorService service;
	
	@Operation(summary = "recupera todos os colaboradores")
	@GetMapping
	private Page<Colaborador> findAll(@RequestParam(required = false) Map<String, String> filters) {
		return this.service.findAll(filters);
	}
	
	@Operation(summary = "Cria um novo colaborador")
	@PostMapping
	private ResponseEntity<Colaborador> create(@RequestBody Colaborador colaborador) {
		
		return new ResponseEntity<Colaborador>(this.service.save(colaborador), HttpStatus.CREATED);
	}
	
	@Operation(summary = "Remove um colaborador")
	@DeleteMapping("/{id}")
	private ResponseEntity<?> remove(@PathVariable("id") Long id) {
		this.service.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Operation(summary = "Remove um colaborador")
	@GetMapping("/{id}")
	private ResponseEntity<Colaborador> findById(@PathVariable("id") Long id) {
		Colaborador colaborador = this.service.findById(id);
		return new ResponseEntity<Colaborador>(colaborador, HttpStatus.OK);
	}
}
