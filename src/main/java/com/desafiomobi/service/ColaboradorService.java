package com.desafiomobi.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafiomobi.domain.Colaborador;
import com.desafiomobi.domain.Setor;
import com.desafiomobi.repository.ColaboradorRepository;

@Service
public class ColaboradorService {

	@Autowired
	ColaboradorRepository repository;
	
	@Autowired
	SetorService setorService;
	
	public Page<Colaborador> findAll(Map<String, String> filters){
		
		int page = filters.get("page") != null ? Integer.valueOf(filters.get("page")) : 0;
		int size = filters.get("size") != null ? Integer.valueOf(filters.get("size")) : 10;
		
		Pageable pageable = PageRequest.of(page, size);
		
		return this.repository.findAll(pageable);
	}
	
	public Colaborador save(Colaborador colaborador) {
		return this.repository.save(colaborador);
	}
	
	public void delete(Long id) {
		this.repository.deleteById(id);
	}
	
	public Colaborador findById(Long id) {
		return this.repository.findById(id).get();
	}
	
	public Colaborador findByNome(String nome) {
		return this.repository.findByNome(nome);
	}
	
	public Page<Colaborador> findBySetor(Map<String, String> filters) {
		
		int page = filters.get("page") != null ? Integer.valueOf(filters.get("page")) : 0;
		int size = filters.get("size") != null ? Integer.valueOf(filters.get("size")) : 10;
		Long setorId = filters.get("setorId") != null ? Long.valueOf(filters.get("setorId")) : null;
		Pageable pageable = PageRequest.of(page, size);
		Setor setor = this.setorService.findById(setorId);
		return this.repository.findAllBySetor(setor, pageable);
	}
	
}
