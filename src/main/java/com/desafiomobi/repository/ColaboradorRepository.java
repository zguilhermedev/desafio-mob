package com.desafiomobi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.desafiomobi.domain.Colaborador;
import com.desafiomobi.domain.Setor;

@Repository
public interface ColaboradorRepository extends PagingAndSortingRepository<Colaborador, Long> {

	Page<Colaborador> findByNome(String nome, Pageable pageable);
	
	Page<Colaborador> findAllBySetor(Setor setor, Pageable pageable);
	
	@Query(value = ""
			+ "with dto as (select "
			+ "			sum(case when date_part('year', date(current_date)) - date_part('year', c.data_nascimento) < 18 then 1 else 0 end) as menor_dezoito, "
			+ "			count(*) as total "
			+ "			from colaborador c "
			+ "			where setor_id = :setorId) "
			+ "select (case when (dto.menor_dezoito <= (dto.total * 0.2)) then true else false end) from dto"
			+ "", nativeQuery = true)
	boolean validarColaboradoresComIdadeMenorQue18AnosPorSetor(@Param("setorId") Long setorId);
	
	@Query(value = ""
			+ "with dto as (select "
			+ "			sum(case when date_part('year', date(current_date)) - date_part('year', c.data_nascimento) > 65 then 1 else 0 end) as terceira_idade, "
			+ "			count(*) as total "
			+ "			from colaborador c) "
			+ "select (case when (dto.terceira_idade <= (dto.total * 0.2)) then true else false end) from dto"
			+ "", nativeQuery = true)
	boolean validarColaboradoresComIdadeMaiorQue65Anos();
}
