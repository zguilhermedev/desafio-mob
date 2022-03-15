package com.desafiomobi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.desafiomobi.domain.Colaborador;
import com.desafiomobi.domain.dto.ColaboradorBlackListDTO;

@Service
public class BlackListService {

	RestTemplate restTemplate = new RestTemplate();

	private final Logger logger = LoggerFactory.getLogger(BlackListService.class);

	final static String BLACK_LIST_URL = "https://5e74cb4b9dff120016353b04.mockapi.io/api/v1/blacklist";

	public Colaborador findColaboradorInTheBlackListByCpf(String cpf) {

		UriComponents builder = UriComponentsBuilder.fromHttpUrl(BLACK_LIST_URL).queryParam("search", cpf).build();

		ColaboradorBlackListDTO[] response = restTemplate.getForObject(builder.toUriString(), ColaboradorBlackListDTO[].class);

		logger.info(response.toString());
		
		return null;
	}
	
	public boolean validateIfColaboradorIsInBlackList(String cpf) {
		Colaborador colaborador = findColaboradorInTheBlackListByCpf(cpf);
		
		if(colaborador != null ) {
			return true;
		}
		
		return false;
	}

}
