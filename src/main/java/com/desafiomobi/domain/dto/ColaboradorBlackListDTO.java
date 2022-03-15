package com.desafiomobi.domain.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColaboradorBlackListDTO {

	Long id;
	LocalDateTime createAt;
	String name;
	String cpf;
}