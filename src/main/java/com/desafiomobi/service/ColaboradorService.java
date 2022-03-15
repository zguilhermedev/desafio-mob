package com.desafiomobi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	
	@Autowired
	BlackListService blackListService;

	public Page<Colaborador> findAll(Pageable pageable) {
		return this.repository.findAll(pageable);
	}

	public Colaborador save(Colaborador colaborador) throws RuntimeException {
		
		if(validateBlackList(colaborador.getCpf())) {
			throw new RuntimeException("Colaborador não pode ser inserido pois está na lista negra.");
		}
		
		try {
			return this.repository.save(colaborador);
		} catch (RuntimeException e) {
			throw new RuntimeException("Erro ao inserir colaborador");
		}
	}

	public void delete(Long id) throws RuntimeException {
		try {
			this.repository.deleteById(id);
		} catch (RuntimeException e) {
			throw new RuntimeException("Erro ao deletar colaborador");
		}
	}

	public Colaborador findById(Long id) throws RuntimeException {
		Colaborador colaborador = this.repository.findById(id).orElse(null);

		if (colaborador == null) {
			throw new RuntimeException("Colaborador não encontrado");
		}

		return colaborador;
	}

	public Page<Colaborador> findByNome(String nome, Pageable pageable) {
		Page<Colaborador> page = this.repository.findByNome(nome, pageable);

		if (page.getContent().isEmpty()) {
			throw new RuntimeException("Colaborador não encontrado");
		}

		return page;
	}

	public Page<Colaborador> findBySetor(String descricaoSetor, Pageable pageable) {
		Setor setor = this.setorService.findByDescricao(descricaoSetor);

		if (setor == null) {
			throw new RuntimeException("Setor não encontrado");
		}

		return this.repository.findAllBySetor(setor, pageable);
	}

	private boolean validateBlackList(String cpf) {
		return blackListService.validateIfColaboradorIsInBlackList(cpf);
	}

	private boolean validateQuantidadeMenores18AnosPorSetor(Setor setor) {

		return false;
	}

	private boolean validateQuantidadeMaiores65AnosPorSetor(Setor setor) {

		return false;
	}

}
