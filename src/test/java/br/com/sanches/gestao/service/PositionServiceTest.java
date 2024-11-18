package br.com.sanches.gestao.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.sanches.gestao.domain.position.model.dto.PositionRequestDTO;
import br.com.sanches.gestao.domain.position.model.dto.PositionResponseDTO;
import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;
import br.com.sanches.gestao.domain.position.repository.PositionRepository;
import br.com.sanches.gestao.domain.position.service.PositionService;
import br.com.sanches.gestao.shared.enums.StatusEnum;
import br.com.sanches.gestao.shared.exceptions.DataNotFoundException;

@ExtendWith(MockitoExtension.class)
class PositionServiceTest {

    @InjectMocks
    private PositionService positionService;

    @Mock
    private PositionRepository positionRepository;

    private PositionEntity positionEntity;
    private PositionRequestDTO positionRequestDTO; 

    @BeforeEach
    void setUp() {
        positionEntity = PositionEntity.builder()
                .positionCode(1)
                .positionName("Developer")
                .positionDescription("Software Developer")
                .positionStatus(StatusEnum.ATIVO.getDescription())
                .build();

        positionRequestDTO = PositionRequestDTO.builder()
                .positionCode(1)
                .positionName("Developer")
                .positionDescription("Software Developer")
                .positionStatus(StatusEnum.ATIVO.getDescription())
                .build(); 
    }

    @Test
    void findPositionById_WhenPositionExists_ReturnsPositionResponseDTO() {
        when(positionRepository.findById(1)).thenReturn(Optional.of(positionEntity));

        PositionResponseDTO result = positionService.findPositionById("1");

        assertNotNull(result);
        assertEquals("Developer", result.getPositionName());
        verify(positionRepository, times(1)).findById(1);
    }

    @Test
    void findPositionById_WhenPositionDoesNotExist_ThrowsDataNotFoundException() {
        when(positionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> positionService.findPositionById("1"));
        verify(positionRepository, times(1)).findById(1);
    }

    @Test
    void findPositionByName_WhenPositionsExist_ReturnsListOfPositionResponseDTO() {
        when(positionRepository.findPositionEntityByName("Developer"))
                .thenReturn(Collections.singletonList(positionEntity));

        List<PositionResponseDTO> result = positionService.findPositionByName("Developer");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Developer", result.get(0).getPositionName());
        verify(positionRepository, times(1)).findPositionEntityByName("Developer");
    }

    @Test
    void findPositionByName_WhenPositionsDoNotExist_ThrowsDataNotFoundException() {
        when(positionRepository.findPositionEntityByName("Unknown")).thenReturn(Collections.emptyList());

        assertThrows(DataNotFoundException.class, () -> positionService.findPositionByName("Unknown"));
        verify(positionRepository, times(1)).findPositionEntityByName("Unknown");
    }

    @Test
    void insertPosition_SavesPositionAndReturnsPositionResponseDTO() {
        when(positionRepository.saveAndFlush(any(PositionEntity.class))).thenReturn(positionEntity);

        PositionResponseDTO result = positionService.insertPosition(positionRequestDTO);

        assertNotNull(result);
        assertEquals("Developer", result.getPositionName());
        verify(positionRepository, times(1)).saveAndFlush(any(PositionEntity.class));
    }

    @Test
    void updatePosition_WhenPositionExists_UpdatesAndReturnsPositionResponseDTO() {
        when(positionRepository.findById(1)).thenReturn(Optional.of(positionEntity));
        when(positionRepository.saveAndFlush(positionEntity)).thenReturn(positionEntity);

        PositionResponseDTO result = positionService.updatePosition(positionRequestDTO, "1");

        assertNotNull(result);
        assertEquals("Developer", result.getPositionName());
        verify(positionRepository, times(1)).findById(1);
        verify(positionRepository, times(1)).saveAndFlush(positionEntity);
    }

    @Test
    void updatePosition_WhenPositionDoesNotExist_ThrowsDataNotFoundException() {
        when(positionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> positionService.updatePosition(positionRequestDTO, "1"));
        verify(positionRepository, times(1)).findById(1);
    }

    @Test
    void deletePosition_WhenPositionExists_SetsStatusToInactive() {
        when(positionRepository.findById(1)).thenReturn(Optional.of(positionEntity));

        positionService.deletePosition("1");

        assertEquals(StatusEnum.INATIVO.getDescription(), positionEntity.getPositionStatus());
        verify(positionRepository, times(1)).findById(1);
        verify(positionRepository, times(1)).saveAndFlush(positionEntity);
    }

    @Test
    void deletePosition_WhenPositionDoesNotExist_ThrowsDataNotFoundException() {
        when(positionRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> positionService.deletePosition("1"));
        verify(positionRepository, times(1)).findById(1);
    }

    @Test
    void retrieveAllPositions_ReturnsPagedPositions() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PositionEntity> page = new PageImpl<>(Collections.singletonList(positionEntity));

        when(positionRepository.findAll(pageable)).thenReturn(page);

        Page<PositionEntity> result = positionService.retrieveAllPositions(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(positionRepository, times(1)).findAll(pageable);
    }
}