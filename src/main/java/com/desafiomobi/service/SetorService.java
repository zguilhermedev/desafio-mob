package com.desafiomobi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafiomobi.domain.Setor;
import com.desafiomobi.repository.SetorRepository;

@Service
public class SetorService {
	
	@Autowired
	SetorRepository setorRepository;

	public Setor findById(Long id) {
		return this.setorRepository.findById(id).get();
	}
	
	public Setor findByDescricao(String descricao) {
		return this.setorRepository.findByDescricao(descricao).get(0);
	}
}
