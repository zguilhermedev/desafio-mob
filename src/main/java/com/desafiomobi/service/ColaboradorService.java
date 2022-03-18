package com.desafiomobi.service;

import static com.desafiomobi.util.MensagemUtil.MS001_COLABORADOR_NAO_ENCONTRADO;
import static com.desafiomobi.util.MensagemUtil.MS002_SETOR_NAO_ENCONTRADO;
import static com.desafiomobi.util.MensagemUtil.MS004_CPF_OBRIGATORIO;
import static com.desafiomobi.util.MensagemUtil.MS005_EMAIL_OBRIGATORIO;
import static com.desafiomobi.util.MensagemUtil.MS006_NOME_OBRIGATORIO;
import static com.desafiomobi.util.MensagemUtil.MS007_TELEFONE_OBRIGATORIO;
import static com.desafiomobi.util.MensagemUtil.MS008_DATA_NASCIMENTO_OBRIGATORIO;
import static com.desafiomobi.util.MensagemUtil.MS009_COLABORADOR_NAO_PODE_SER_INSERIDO_BLACK_LIST;
import static com.desafiomobi.util.MensagemUtil.MS010_NAO_PERMITIDO_CADASTRO_18_ANOS;
import static com.desafiomobi.util.MensagemUtil.MS012_ERRO_INSERIR_COLABORADOR;
import static com.desafiomobi.util.MensagemUtil.MS013_ERRO_DELETAR_COLABORADOR;
import static java.util.Optional.ofNullable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.desafiomobi.domain.Colaborador;
import com.desafiomobi.domain.Setor;
import com.desafiomobi.repository.ColaboradorRepository;
import com.desafiomobi.util.MensagemUtil;

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
		validateSaveColaborador(colaborador);
		validateBlackList(colaborador.getCpf());
		validateQuantidadeMaiores65Anos();
		validateQuantidadeMenores18AnosPorSetor(colaborador.getSetor());
		try {
			return this.repository.save(colaborador);
		} catch (RuntimeException e) {
			throw new RuntimeException(MS012_ERRO_INSERIR_COLABORADOR);
		}
	}

	public void delete(Long id) throws RuntimeException {
		try {
			this.repository.deleteById(id);
		} catch (RuntimeException e) {
			throw new RuntimeException(MS013_ERRO_DELETAR_COLABORADOR);
		}
	}

	public Colaborador findById(Long id) throws RuntimeException {
		Colaborador colaborador = this.repository.findById(id).orElse(null);
		ofNullable(colaborador).orElseThrow(() -> new RuntimeException(MS001_COLABORADOR_NAO_ENCONTRADO));
		return colaborador;
	}

	public Page<Colaborador> findByNome(String nome, Pageable pageable) {
		Page<Colaborador> page = this.repository.findByNome(nome, pageable);
		if (page.getContent().isEmpty()) {
			throw new RuntimeException(MS001_COLABORADOR_NAO_ENCONTRADO);
		}
		
		return page;
	}

	public Page<Colaborador> findBySetor(String descricaoSetor, Pageable pageable) {
		Setor setor = this.setorService.findByDescricao(descricaoSetor);
		ofNullable(setor).orElseThrow(() -> new RuntimeException(MS002_SETOR_NAO_ENCONTRADO));
		return this.repository.findAllBySetor(setor, pageable);
	}
	
	private void validateSaveColaborador(Colaborador colaborador) throws RuntimeException {
//		ofNullable(colaborador.getSetor()).orElseThrow(() -> new RuntimeException(MS003_SETOR_OBRIGATORIO));
		ofNullable(colaborador.getCpf()).orElseThrow(() -> new RuntimeException(MS004_CPF_OBRIGATORIO));
		ofNullable(colaborador.getEmail()).orElseThrow(() -> new RuntimeException(MS005_EMAIL_OBRIGATORIO));		
		ofNullable(colaborador.getNome()).orElseThrow(() -> new RuntimeException(MS006_NOME_OBRIGATORIO));	
		ofNullable(colaborador.getTelefone()).orElseThrow(() -> new RuntimeException(MS007_TELEFONE_OBRIGATORIO));	
		ofNullable(colaborador.getDataNascimento()).orElseThrow(() -> new RuntimeException(MS008_DATA_NASCIMENTO_OBRIGATORIO) );
	}

	private void validateBlackList(String cpf) throws RuntimeException {
		if(blackListService.validateIfColaboradorIsInBlackList(cpf)) {
			throw new RuntimeException(MS009_COLABORADOR_NAO_PODE_SER_INSERIDO_BLACK_LIST);
		}
	}

	private void validateQuantidadeMenores18AnosPorSetor(Setor setor) throws RuntimeException {
		if(!repository.validarColaboradoresComIdadeMenorQue18AnosPorSetor(setor.getId())) {
			throw new RuntimeException(MS010_NAO_PERMITIDO_CADASTRO_18_ANOS);
		}
		
	}

	private void validateQuantidadeMaiores65Anos() throws RuntimeException {
		if(!repository.validarColaboradoresComIdadeMaiorQue65Anos()) {
			throw new RuntimeException(MensagemUtil.MS011_NAO_PERMITIDO_CADASTRO_65_ANOS);
		}
	}

}
