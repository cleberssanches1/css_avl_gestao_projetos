package br.com.sanches.gestao.domain.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.sanches.gestao.domain.project.model.entity.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
	
	@Query("SELECT p FROM ProjectEntity p WHERE LOWER(p.projectName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ProjectEntity> findProjectEntityByName(@Param("name") String name);
}