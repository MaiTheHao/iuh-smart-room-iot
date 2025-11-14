package com.example.smartroom.modules.device.service.impl;

import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.core.common.vo.PaginationInfo;
import com.example.smartroom.modules.device.dto.CreateDeviceDto;
import com.example.smartroom.modules.device.dto.DeviceDto;
import com.example.smartroom.modules.device.dto.UpdateDeviceDto;
import com.example.smartroom.modules.device.entity.Device;
import com.example.smartroom.modules.device.entity.DeviceLan;
import com.example.smartroom.modules.device.mapper.DeviceMapper;
import com.example.smartroom.modules.device.repository.DeviceRepository;
import com.example.smartroom.modules.device.service.DeviceService;
import com.example.smartroom.modules.room.entity.Room;
import com.example.smartroom.modules.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;
    private final RoomRepository roomRepository;
    private final DeviceMapper deviceMapper;

    @Override
    @Transactional
    public DeviceDto create(CreateDeviceDto dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new NotFoundException("Room not found with id: " + dto.getRoomId()));

        Device device = deviceMapper.toEntity(dto);
        device.setRoom(room);
        if (dto.getGatewayId() != null) {
            Device gateway = deviceRepository.findById(dto.getGatewayId())
                .orElseThrow(() -> new NotFoundException("Gateway not found with id: " + dto.getGatewayId()));
            if (gateway.getIsGateway() == null || !gateway.getIsGateway()) {
                throw new NotFoundException("Device with id: " + dto.getGatewayId() + " is not a gateway");
            }
            device.setGateway(gateway);
        }

        device = deviceRepository.save(device);
        DeviceDto result = deviceMapper.toDto(device);
        return result;
    }

    @Override
    @Transactional
    public DeviceDto update(Long id, UpdateDeviceDto dto, String langCode) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Device not found with id: " + id));

        deviceMapper.updateEntityFromDto(device, dto);

        if (dto.getRoomId() != null) {
            Room room = roomRepository.findById(dto.getRoomId())
                    .orElseThrow(() -> new NotFoundException("Room not found with id: " + dto.getRoomId()));
            device.setRoom(room);
        }
        if (dto.getGatewayId() != null) {
            Device gateway = deviceRepository.findById(dto.getGatewayId())
                    .orElseThrow(() -> new NotFoundException("Gateway not found with id: " + dto.getGatewayId()));
            device.setGateway(gateway);
        }

        device = deviceRepository.save(device);
        DeviceDto result = deviceMapper.toDto(device);

        if (langCode != null) {
            populateTranslationFields(result, device, langCode);
        }

        return result;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!deviceRepository.existsById(id)) {
            throw new NotFoundException("Device not found with id: " + id);
        }
        try {
            deviceRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete device", ex);
        }
    }

    @Override
    public DeviceDto getById(Long id, String langCode) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Device not found with id: " + id));
        DeviceDto result = deviceMapper.toDto(device);

        if (langCode != null) {
            populateTranslationFields(result, device, langCode);
        }

        return result;
    }

    @Override
    public PaginationInfo<DeviceDto> getList(Pageable pageable, String langCode) {
        Page<Device> devicePage = deviceRepository.findAll(pageable);

        List<DeviceDto> dtoList = devicePage.getContent().stream()
                .map(device -> {
                    DeviceDto dto = deviceMapper.toDto(device);
                    if (langCode != null) {
                        populateTranslationFields(dto, device, langCode);
                    }
                    return dto;
                })
                .toList();

        return PaginationInfo.from(devicePage, dtoList);
    }

    @Override
    public PaginationInfo<DeviceDto> getGateways(Pageable pageable, String langCode) {
        Page<Device> devicePage = deviceRepository.findAllGateways(pageable);

        List<DeviceDto> dtoList = devicePage.getContent().stream()
                .map(device -> {
                    DeviceDto dto = deviceMapper.toDto(device);
                    if (langCode != null) {
                        populateTranslationFields(dto, device, langCode);
                    }
                    return dto;
                })
                .toList();

        return PaginationInfo.from(devicePage, dtoList);
    }

    @Override
    public PaginationInfo<DeviceDto> getByGatewayId(Long gatewayId, Pageable pageable, String langCode) {
        Page<Device> devicePage = deviceRepository.findByGatewayId(gatewayId, pageable);

        List<DeviceDto> dtoList = devicePage.getContent().stream()
                .map(device -> {
                    DeviceDto dto = deviceMapper.toDto(device);
                    if (langCode != null) {
                        populateTranslationFields(dto, device, langCode);
                    }
                    return dto;
                })
                .toList();

        return PaginationInfo.from(devicePage, dtoList);
    }

    private void populateTranslationFields(DeviceDto dto, Device device, String langCode) {
        if (device.getDeviceLans() == null) return;
        DeviceLan matchedLan = device.getDeviceLans().stream()
                .filter(lan -> lan.getLanguage() != null && lan.getLanguage().getCode().equalsIgnoreCase(langCode))
                .findFirst()
                .orElse(null);

        if (matchedLan != null) {
            dto.setName(matchedLan.getName());
            dto.setDescription(matchedLan.getDescription());
            dto.setLocationDescription(matchedLan.getLocationDescription());
            dto.setLangCode(matchedLan.getLanguage().getCode());
        }
    }
}
