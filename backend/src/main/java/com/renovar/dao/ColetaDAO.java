package com.renovar.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.renovar.domain.Coleta;

@Repository
public interface ColetaDAO extends JpaRepository<Coleta, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Coleta obj WHERE obj.dispositivo.id = :id ORDER BY obj.data DESC")
	List<Coleta> buscarColetasDispositivo(@Param("id") Integer id);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Coleta obj WHERE obj.dispositivo.id = :id ORDER BY obj.data DESC")
	Page<Coleta> buscarColetasDispositivoPorPagina(@Param("id") Integer id, Pageable pageRequest);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Coleta obj WHERE obj.dispositivo.id = :idDispositivo AND obj.indicador.id = :idIndicador ORDER BY obj.data ASC")
	List<Coleta> buscarColetasDispositivoIndicador(@Param("idDispositivo") Integer idDispositivo,
			@Param("idIndicador") Integer idIndicador);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Coleta obj WHERE obj.dispositivo.id = :idDispositivo AND obj.indicador.id = :idIndicador ORDER BY obj.data ASC")
	Page<Coleta> buscarColetasPorPaginaDispositivoIndicador(@Param("idDispositivo") Integer idDispositivo,
			@Param("idIndicador") Integer idIndicador, Pageable pageRequest);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Coleta obj WHERE obj.dispositivo.id = :idDispositivo AND obj.indicador.id = :idIndicador AND obj.data BETWEEN :dataInicio AND :dataFim ORDER BY obj.data ASC")
	List<Coleta> buscarColetasDispositivoIndicadorData(@Param("idDispositivo") Integer idDispositivo,
			@Param("idIndicador") Integer idIndicador, @Param("dataInicio") Date dataInicio,
			@Param("dataFim") Date dataFim);

}
