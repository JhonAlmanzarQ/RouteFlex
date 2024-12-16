package com.software.RouteFlex.repositories;

import com.software.RouteFlex.models.PaqueteEnvio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaqueteEnvioRepository extends JpaRepository<PaqueteEnvio, Long> {

}
