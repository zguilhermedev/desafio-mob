package com.desafiomobi.util;

import java.util.ArrayList;
import java.util.List;

import com.desafiomobi.domain.dto.ColaboradorBlackListDTO;

import lombok.Data;

@Data
public class ColaboradorBlackListDTOList {

	private List<ColaboradorBlackListDTO> colabores;
	
	public ColaboradorBlackListDTOList() {
		this.colabores = new ArrayList<ColaboradorBlackListDTO>();
	}
}
