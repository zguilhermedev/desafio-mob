package com.desafiomobi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.desafiomobi.domain.Colaborador;
import com.desafiomobi.domain.Setor;

@Repository
public interface ColaboradorRepository extends PagingAndSortingRepository<Colaborador, Long> {

	Colaborador findByNome(String nome);
	
	Page<Colaborador> findAllBySetor(Setor setor, Pageable pageable);
}
