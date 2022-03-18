package com.desafiomobi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Date;

import org.junit.jupiter.api.Test;

import com.desafiomobi.domain.Colaborador;
import com.desafiomobi.util.MensagemUtil;

public class ColaboradorServiceTest {
	
	@Test
	public void shouldNotSaveWithoutCPF() {
		
		ColaboradorService service = new ColaboradorService();
		Colaborador colaborador = Colaborador
									.builder()
									.nome("Jules Homenick")
									.cpf(null)
									.dataNascimento(new Date())
									.email("test@bol.com")
									.telefone("85999598232")
									.build();
		try {
			service.save(colaborador);
			fail("Deveria ter lançado exceção.");
		} catch (Exception e) {
			assertEquals(MensagemUtil.MS004_CPF_OBRIGATORIO, e.getMessage());
		}
		
	}
	
}
