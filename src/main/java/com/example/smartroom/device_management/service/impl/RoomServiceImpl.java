package com.example.smartroom.device_management.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smartroom.common.exception.BadRequestException;
import com.example.smartroom.common.exception.NotFoundException;
import com.example.smartroom.device_management.dto.room.RoomCreateDTO;
import com.example.smartroom.device_management.dto.room.RoomDTO;
import com.example.smartroom.device_management.entity.Room;
import com.example.smartroom.device_management.mapper.RoomMapper;
import com.example.smartroom.device_management.repository.RoomRepository;
import com.example.smartroom.device_management.service.RoomService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomServiceImpl implements RoomService {
	
	private final RoomRepository roomRepository;
	private final RoomMapper roomMapper;

	@Override
	@Transactional
	public RoomDTO createRoom(@Valid RoomCreateDTO dto) {
		if (roomRepository.findById(dto.getId()).isPresent()) {
			throw new BadRequestException("Room with id " + dto.getId() + " is existed");
		}
		
		Room newRoom = roomMapper.toEntity(dto);
		Room savedRoom = roomRepository.save(newRoom);
		return roomMapper.toDTO(savedRoom);
	}

	@Override
	public RoomDTO getRoomById(String id) {
		Room room = roomRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Room with id " + id + " not found"));
		return roomMapper.toDTO(room);
	}

	@Override
	public Page<RoomDTO> getList(Pageable pageRequest) {
		return roomRepository.findAll(pageRequest)
			.map(roomMapper::toDTO);
	}

	@Override
	@Transactional
	public RoomDTO deleteRoomById(String id) {
		Room room = roomRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Room with id " + id + " not found"));

		if (!room.getHubs().isEmpty()) {
			throw new BadRequestException("Cannot delete room with id " + id + " because it has " + room.getHubs().size() + " hub(s)");
		}

		roomRepository.delete(room);
		return roomMapper.toDTO(room);
	}

	@Override
	public Long count() {
		return roomRepository.count();
	}
}
