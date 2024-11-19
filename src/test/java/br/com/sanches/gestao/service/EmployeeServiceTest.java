package br.com.sanches.gestao.service;
  
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.sanches.gestao.domain.employee.model.dto.EmployeeRequestDTO;
import br.com.sanches.gestao.domain.employee.model.dto.EmployeeResponseDTO;
import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;
import br.com.sanches.gestao.domain.employee.repository.EmployeeRepository;
import br.com.sanches.gestao.domain.employee.service.EmployeeService;
import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;
import br.com.sanches.gestao.domain.position.repository.PositionRepository;
import br.com.sanches.gestao.shared.enums.StatusEnum;
import br.com.sanches.gestao.shared.exceptions.DataNotFoundException;
import br.com.sanches.gestao.shared.utils.Constants;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private EmployeeEntity employeeEntity;
    private PositionEntity positionEntity;
    private EmployeeRequestDTO employeeRequestDTO;

    @BeforeEach
    void setUp() {
        positionEntity = new PositionEntity();
        positionEntity.setPositionCode(1);

        employeeEntity = EmployeeEntity.builder()
                .employeeCode(1)
                .cpf("12345678900")
                .employeeName("John Doe")
                .employeeStatus(StatusEnum.ATIVO.getDescription())
                .position(positionEntity)
                .startDate(LocalDate.now())
                .build();

        employeeRequestDTO = EmployeeRequestDTO.builder()
                .employeeCode(1)
                .cpf("12345678900")
                .employeeName("John Doe")
                .employeeStatus(StatusEnum.ATIVO.getDescription())
                .position(1)
                .startDate(LocalDate.now())
                .build();
    }

    @Test
    void testFindEmployeeById_Success() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeEntity));

        EmployeeResponseDTO response = employeeService.findEmployeeById("1");

        assertNotNull(response);
        assertEquals(employeeEntity.getEmployeeCode(), response.getEmployeeCode());
        assertEquals(employeeEntity.getCpf(), response.getCpf());
        verify(employeeRepository, times(1)).findById(1);
    }

    @Test
    void testFindEmployeeById_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> employeeService.findEmployeeById("1"));

        assertEquals(Constants.EMPLOYEE + "1" + Constants.NOT_FOUND, exception.getMessage());
    }

    @Test
    void testFindEmployeeByCpf_Success() {
        when(employeeRepository.findEmployeeEntityByCpf("12345678900")).thenReturn(List.of(employeeEntity));

        List<EmployeeResponseDTO> response = employeeService.findEmployeeByCpf("12345678900");

        assertNotNull(response);
        assertFalse(response.isEmpty());
        verify(employeeRepository, times(1)).findEmployeeEntityByCpf("12345678900");
    }

    @Test
    void testFindEmployeeByCpf_NotFound() {
        when(employeeRepository.findEmployeeEntityByCpf("12345678900")).thenReturn(List.of());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> employeeService.findEmployeeByCpf("12345678900"));

        assertEquals(Constants.COLLABORATOR_WITH_CPF + "12345678900" + Constants.NOT_FOUND, exception.getMessage());
    }

    @Test
    void testInsertEmployee() {
        when(employeeRepository.saveAndFlush(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        EmployeeResponseDTO response = employeeService.insertEmployee(employeeRequestDTO);

        assertNotNull(response);
        assertEquals(employeeEntity.getEmployeeCode(), response.getEmployeeCode());
        verify(employeeRepository, times(1)).saveAndFlush(any(EmployeeEntity.class));
    }

    @Test
    void testUpdateEmployee_Success() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeEntity));
        when(positionRepository.findById(1)).thenReturn(Optional.of(positionEntity));
        when(employeeRepository.saveAndFlush(any(EmployeeEntity.class))).thenReturn(employeeEntity);

        EmployeeResponseDTO response = employeeService.updateEmployee(employeeRequestDTO, "1");

        assertNotNull(response);
        assertEquals(employeeEntity.getEmployeeName(), response.getEmployeeName());
        verify(employeeRepository, times(1)).findById(1);
        verify(positionRepository, times(1)).findById(1);
        verify(employeeRepository, times(1)).saveAndFlush(any(EmployeeEntity.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> employeeService.updateEmployee(employeeRequestDTO, "1"));

        assertEquals(Constants.EMPLOYEE + "1" + Constants.NOT_FOUND, exception.getMessage());
    }

    @Test
    void testUpdateEmployee_PositionNotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeEntity));
        when(positionRepository.findById(1)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> employeeService.updateEmployee(employeeRequestDTO, "1"));

        assertEquals(Constants.POSITION + "1" + Constants.NOT_FOUND, exception.getMessage());
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employeeEntity));

        employeeService.deleteEmployee("1");

        assertEquals(StatusEnum.INATIVO.getDescription(), employeeEntity.getEmployeeStatus());
        verify(employeeRepository, times(1)).saveAndFlush(employeeEntity);
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        DataNotFoundException exception = assertThrows(DataNotFoundException.class, () -> employeeService.deleteEmployee("1"));

        assertEquals(Constants.EMPLOYEE + "1" + Constants.NOT_FOUND, exception.getMessage());
    }

    @Test
    void testRetrieveAllEmployees() {
        Page<EmployeeEntity> page = new PageImpl<>(List.of(employeeEntity));
        Pageable pageable = mock(Pageable.class);
        when(employeeRepository.findAll(pageable)).thenReturn(page);

        Page<EmployeeEntity> response = employeeService.retrieveAllEmployees(pageable);

        assertNotNull(response);
        assertEquals(1, response.getContent().size());
        verify(employeeRepository, times(1)).findAll(pageable);
    }
}
