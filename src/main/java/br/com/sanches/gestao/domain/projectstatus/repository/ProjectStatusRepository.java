package br.com.sanches.gestao.domain.projectstatus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatusEntity, Integer> {
	
	@Query("SELECT p FROM ProjectStatusEntity p WHERE LOWER(p.statusDescription) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ProjectStatusEntity> findProjectStatusEntityByName(@Param("name") String name); 
		
}