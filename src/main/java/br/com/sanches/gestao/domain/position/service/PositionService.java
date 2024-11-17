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
import br.com.sanches.gestao.shared.exceptions.PositionNotFoundException;
import br.com.sanches.gestao.shared.utils.Constants;

@Service
public class PositionService {
	 
	private final PositionRepository positionRepository;
	
	@Autowired
	public PositionService(PositionRepository positionRepository) {
		this.positionRepository = positionRepository;
	}

	public PositionResponseDTO findPositionById(String id) {

		Optional<PositionEntity> positionOptional = findPosition(id);

		return positionOptional.map(PositionAdapter::fromEntityToDTO)
				.orElseThrow(() -> new PositionNotFoundException(Constants.POSITION + id + Constants.NOT_FOUND));
	}
 
	public List<PositionResponseDTO> findPositionByName(String name) {

		List<PositionEntity> PositionList = positionRepository.findPositionEntityByName(name);

		if (PositionList.isEmpty()) {
			throw new PositionNotFoundException(Constants.POSITION_WITH_NAME + name + Constants.NOT_FOUND);
		}

		return PositionList.stream().map(PositionAdapter::fromEntityToDTO).toList();
	}
	
	public PositionResponseDTO insertPosition(PositionRequestDTO request) {		
		return PositionAdapter.fromEntityToDTO(this.positionRepository.saveAndFlush(PositionAdapter.fromDTOToEntity(request)));	 
	}
		
	public PositionResponseDTO updatePosition(PositionRequestDTO request, String id) {	
		Optional<PositionEntity> positionOptional = findPosition(id);
		
		if(positionOptional.isEmpty()) {
			throw new PositionNotFoundException(Constants.POSITION + id + Constants.NOT_FOUND);
		}
		 
		positionOptional.get().setPositionDescription(request.getPositionDescription());;
		positionOptional.get().setPositionName(request.getPositionName()); 
		positionOptional.get().setPositionStatus(request.getPositionStatus());
		 
		return PositionAdapter.fromEntityToDTO(this.positionRepository.saveAndFlush(positionOptional.get()));	 
	}
	
	public void deletePosition(String id) {		
        Optional<PositionEntity> positionOptional = findPosition(id);
		
		if(positionOptional.isEmpty()) {
			throw new PositionNotFoundException(Constants.POSITION + id + Constants.NOT_FOUND);
		}
		
		positionOptional.get().setPositionStatus("INATIVO");
		
		this.positionRepository.saveAndFlush(positionOptional.get());			 
	}
	
	public Page<PositionEntity> retrieveAllPositions(Pageable page){		
		return this.positionRepository.findAll(page); 
	}
	
	private Optional<PositionEntity> findPosition(String id) {
		return positionRepository.findById(Integer.valueOf(id));
	}
 
}	 