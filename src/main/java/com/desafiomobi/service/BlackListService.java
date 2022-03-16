package com.desafiomobi.service;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.desafiomobi.domain.dto.ColaboradorBlackListDTO;

@Service
public class BlackListService {

	RestTemplate restTemplate = new RestTemplate();

	final static String BLACK_LIST_URL = "https://5e74cb4b9dff120016353b04.mockapi.io/api/v1/blacklist";

	public ColaboradorBlackListDTO findColaboradorInTheBlackListByCpf(String cpf) {

		UriComponents builder = UriComponentsBuilder.fromHttpUrl(BLACK_LIST_URL).queryParam("search", cpf).build();

		ColaboradorBlackListDTO[] response = restTemplate.getForObject(builder.toUriString(), ColaboradorBlackListDTO[].class);
		
		List<ColaboradorBlackListDTO> list = new ArrayList<>(Arrays.asList(response));
		
		if(!list.isEmpty()) {
			return list.get(0);
		}
		
		return null;
	}
	
	public boolean validateIfColaboradorIsInBlackList(String cpf) {
		ColaboradorBlackListDTO colaborador = findColaboradorInTheBlackListByCpf(cpf);
		return nonNull(colaborador);
	}

}
