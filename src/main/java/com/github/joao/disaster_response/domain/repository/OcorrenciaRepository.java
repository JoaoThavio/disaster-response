package com.github.joao.disaster_response.domain.repository;

import com.github.joao.disaster_response.domain.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long>{

}
