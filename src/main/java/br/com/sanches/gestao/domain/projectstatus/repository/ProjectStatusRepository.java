package br.com.sanches.gestao.domain.projectstatus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;

@Repository
public interface ProjectStatusRepository extends JpaRepository<ProjectStatusEntity, Integer> {
}