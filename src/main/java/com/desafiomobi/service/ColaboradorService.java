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
		
		validateColaborador(colaborador);
		validateBlackList(colaborador.getCpf());
		validateQuantidadeMaiores65Anos();
		validateQuantidadeMenores18AnosPorSetor(colaborador.getSetor());
		
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
	
	private void validateColaborador(Colaborador colaborador) throws RuntimeException {
		if(colaborador.getSetor() == null) {
			throw new RuntimeException("Setor é obrigatório");
		}
		
		if(colaborador.getCpf() == null) {
			throw new RuntimeException("Cpf é obrigatório");
		}
		
		if(colaborador.getEmail() == null) {
			throw new RuntimeException("Email é obrigatório");
		}
		
		if(colaborador.getNome() == null) {
			throw new RuntimeException("Nome é obrigatório");
		}
		
		if(colaborador.getTelefone() == null) {
			throw new RuntimeException("Telefone é obrigatório");
		}
		
		if(colaborador.getDataNascimento() == null) {
			throw new RuntimeException("Data de nascimento é obrigatório");
		}
	}

	private void validateBlackList(String cpf) throws RuntimeException {
		if(blackListService.validateIfColaboradorIsInBlackList(cpf)) {
			throw new RuntimeException("Colaborador não pode ser inserido pois está na lista negra.");
		}
	}

	private void validateQuantidadeMenores18AnosPorSetor(Setor setor) throws RuntimeException {

		if(!repository.validarColaboradoresComIdadeMenorQue18AnosPorSetor(setor.getId())) {
			throw new RuntimeException("Não é possivel cadastrar colaborador nesse setor pois o setor já tem 20% de seus colaboradores com idade menor que 18 anos");
		}
		
	}

	private void validateQuantidadeMaiores65Anos() throws RuntimeException {

		if(!repository.validarColaboradoresComIdadeMaiorQue65Anos()) {
			throw new RuntimeException("Não é possivel cadastrar colaborador pois a empresa já tem 20% de seus colaboradores com idade maior que 65 anos");
		}
	}

}
