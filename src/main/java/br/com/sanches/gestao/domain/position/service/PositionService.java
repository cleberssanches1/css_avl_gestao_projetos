package br.com.sanches.gestao.domain.position.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sanches.gestao.domain.position.model.adapter.PositionAdapter;
import br.com.sanches.gestao.domain.position.model.dto.PositionRequestDTO;
import br.com.sanches.gestao.domain.position.model.dto.PositionResponseDTO;
import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;
import br.com.sanches.gestao.domain.position.repository.PositionRepository;
import br.com.sanches.gestao.shared.enums.StatusEnum;
import br.com.sanches.gestao.shared.exceptions.DataNotFoundException;
import br.com.sanches.gestao.shared.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PositionService {
	  
	private final PositionRepository positionRepository;
	
	@Autowired
	public PositionService(PositionRepository positionRepository) {
		this.positionRepository = positionRepository;
	}

	public PositionResponseDTO findPositionById(String id) {
		log.info(Constants.BUSCANDO_CARGO_COM_O_ID + id);
		Optional<PositionEntity> positionOptional = findPosition(id);

		return positionOptional.map(PositionAdapter::fromEntityToDTO)
				.orElseThrow(() -> new DataNotFoundException(Constants.POSITION + id + Constants.NOT_FOUND));
	}
 
	public List<PositionResponseDTO> findPositionByName(String name) {
		log.info(Constants.BUSCANDO_CARGO_COM_O_NOME + name);
		List<PositionEntity> positionList = positionRepository.findPositionEntityByName(name);

		if (positionList.isEmpty()) {
			throw new DataNotFoundException(Constants.POSITION_WITH_NAME + name + Constants.NOT_FOUND);
		}

		return positionList.stream().map(PositionAdapter::fromEntityToDTO).toList();
	}
	
	public PositionResponseDTO insertPosition(PositionRequestDTO request) {	
		log.info("inserindo cargo");
		return PositionAdapter.fromEntityToDTO(this.positionRepository.saveAndFlush(PositionAdapter.fromDTOToEntity(request)));	 
	}
		
	public PositionResponseDTO updatePosition(PositionRequestDTO request, String id) {	
		log.info(Constants.ATUALIZANDO_O_CARGO);
		Optional<PositionEntity> positionOptional = findPosition(id);
		
		if(positionOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.POSITION + id + Constants.NOT_FOUND);
		}
		 
		positionOptional.get().setPositionDescription(request.getPositionDescription());
		positionOptional.get().setPositionName(request.getPositionName()); 
		positionOptional.get().setPositionStatus(request.getPositionStatus());
		 
		return PositionAdapter.fromEntityToDTO(this.positionRepository.saveAndFlush(positionOptional.get()));	 
	}
	
	public void deletePosition(String id) {		
		log.info(Constants.EXCLUINDO_O_CARGO);
        Optional<PositionEntity> positionOptional = findPosition(id);
		
		if(positionOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.POSITION + id + Constants.NOT_FOUND);
		}
		
		positionOptional.get().setPositionStatus(StatusEnum.INATIVO.getDescription());
		
		this.positionRepository.saveAndFlush(positionOptional.get());			 
	}
	
	public Page<PositionEntity> retrieveAllPositions(Pageable page){	
		log.info(Constants.BUSCANDO_O_CARGOS);
		return this.positionRepository.findAll(page); 
	}
	
	private Optional<PositionEntity> findPosition(String id) {
		return positionRepository.findById(Integer.valueOf(id));
	}
 
}	 