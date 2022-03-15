package com.desafiomobi.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.desafiomobi.domain.Setor;

@Repository
public interface SetorRepository extends PagingAndSortingRepository<Setor, Long> {

	List<Setor> findByDescricao(String descricao);
}
