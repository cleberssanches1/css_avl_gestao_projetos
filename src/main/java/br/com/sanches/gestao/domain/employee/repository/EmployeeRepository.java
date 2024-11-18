package br.com.sanches.gestao.domain.employee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
	
	public List<EmployeeEntity> findEmployeeEntityByCpf(String cpf);
}