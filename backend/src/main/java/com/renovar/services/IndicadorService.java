package com.renovar.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renovar.dao.IndicadorDAO;
import com.renovar.domain.Indicador;
import com.renovar.services.exceptions.ObjectNotFoundException;

@Service
public class IndicadorService {

	@Autowired
	private IndicadorDAO dao;

	public Indicador buscar(Integer id) {
		Optional<Indicador> findById = dao.findById(id);
		return findById.orElseThrow(() -> new ObjectNotFoundException(
				"Indicador não encontrado: " + id + " , Tipo: " + Indicador.class.getName()));
	}

	public List<Indicador> buscarTodos() {
		return dao.findAll();
	}

	public Indicador inserir(Indicador indicador) {
		indicador.setId(null);
		return dao.save(indicador);
	}

	public List<Indicador> toIndicadores(List<Integer> indicadoresId) {
		List<Indicador> indicadores = new ArrayList<>();
		for (Integer id : indicadoresId) {
			Indicador indicador = buscar(id);
			indicadores.add(indicador);
		}
		return indicadores;
	}

	public Indicador atualizar(Indicador indicador) {
		// TODO Auto-generated method stub
		return null;
	}

}
