package br.com.sanches.gestao.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sanches.gestao.domain.project.model.entity.ProjectEntity;

@Repository
public interface ProjectEpository extends JpaRepository<ProjectEntity, Integer> {
}
