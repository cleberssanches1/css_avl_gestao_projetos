package br.com.sanches.gestao.domain.position.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;

@Repository
public interface PositionRepository extends JpaRepository<PositionEntity, Integer> {
	
	@Query("SELECT p FROM PositionEntity p WHERE LOWER(p.positionName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<PositionEntity> findPositionEntityByName(@Param("name") String name);

}