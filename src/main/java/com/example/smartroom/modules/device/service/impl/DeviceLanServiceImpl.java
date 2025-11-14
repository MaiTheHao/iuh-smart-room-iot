package com.example.smartroom.modules.device.service.impl;

import com.example.smartroom.core.common.exception.InternalServerException;
import com.example.smartroom.core.common.exception.NotFoundException;
import com.example.smartroom.modules.device.dto.CreateDeviceLanDto;
import com.example.smartroom.modules.device.dto.DeviceLanDto;
import com.example.smartroom.modules.device.dto.UpdateDeviceLanDto;
import com.example.smartroom.modules.device.entity.Device;
import com.example.smartroom.modules.device.entity.DeviceLan;
import com.example.smartroom.modules.device.mapper.DeviceLanMapper;
import com.example.smartroom.modules.device.repository.DeviceLanRepository;
import com.example.smartroom.modules.device.repository.DeviceRepository;
import com.example.smartroom.modules.device.service.DeviceLanService;
import com.example.smartroom.modules.language.entity.Language;
import com.example.smartroom.modules.language.repository.LanguageRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceLanServiceImpl implements DeviceLanService {

    private final DeviceLanRepository deviceLanRepository;
    private final DeviceRepository deviceRepository;
    private final LanguageRepository languageRepository;
    private final DeviceLanMapper deviceLanMapper;

    @Override
    @Transactional
    public DeviceLanDto create(CreateDeviceLanDto dto) {
        Device device = deviceRepository.findById(dto.getDeviceId())
            .orElseThrow(() -> new NotFoundException("Device not found with id: " + dto.getDeviceId()));

        Language language = languageRepository.findByCode(dto.getLangCode())
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + dto.getLangCode()));

        deviceLanRepository.findByDeviceIdAndLanguageCode(dto.getDeviceId(), dto.getLangCode())
            .ifPresent(existing -> {
                throw new InternalServerException(
                    "Translation already exists for device " + dto.getDeviceId() + " and language " + dto.getLangCode()
                );
            });

        DeviceLan deviceLan = DeviceLan.builder()
            .device(device)
            .language(language)
            .name(dto.getName())
            .description(dto.getDescription())
            .locationDescription(dto.getLocationDescription())
            .build();

        deviceLan = deviceLanRepository.save(deviceLan);
        return deviceLanMapper.toDto(deviceLan);
    }

    @Override
    @Transactional
    public DeviceLanDto update(Long id, UpdateDeviceLanDto dto) {
        DeviceLan deviceLan = deviceLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("DeviceLan not found with id: " + id));

        if (dto.getName() != null) {
            deviceLan.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            deviceLan.setDescription(dto.getDescription());
        }
        if (dto.getLocationDescription() != null) {
            deviceLan.setLocationDescription(dto.getLocationDescription());
        }

        deviceLan = deviceLanRepository.save(deviceLan);
        return deviceLanMapper.toDto(deviceLan);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        DeviceLan deviceLan = deviceLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("DeviceLan not found with id: " + id));

        try {
            deviceLanRepository.delete(deviceLan);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete DeviceLan", ex);
        }
    }

    @Override
    public DeviceLanDto getById(Long id) {
        DeviceLan deviceLan = deviceLanRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("DeviceLan not found with id: " + id));
        return deviceLanMapper.toDto(deviceLan);
    }

    @Override
    public List<DeviceLanDto> getByDeviceId(Long deviceId) {
        if (!deviceRepository.existsById(deviceId)) {
            throw new NotFoundException("Device not found with id: " + deviceId);
        }

        List<DeviceLan> deviceLans = deviceLanRepository.findByDeviceId(deviceId);
        return deviceLans.stream()
            .map(deviceLanMapper::toDto)
            .toList();
    }

    @Override
    public DeviceLanDto getByDeviceIdAndLangCode(Long deviceId, String langCode) {
        DeviceLan deviceLan = deviceLanRepository.findByDeviceIdAndLanguageCode(deviceId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for device " + deviceId + " and language " + langCode
            ));
        return deviceLanMapper.toDto(deviceLan);
    }

    @Override
    @Transactional
    public DeviceLanDto upsertByDeviceIdAndLangCode(Long deviceId, String langCode, UpdateDeviceLanDto dto) {
        Device device = deviceRepository.findById(deviceId)
            .orElseThrow(() -> new NotFoundException("Device not found with id: " + deviceId));

        Language language = languageRepository.findByCode(langCode)
            .orElseThrow(() -> new NotFoundException("Language not found with code: " + langCode));

        DeviceLan deviceLan = deviceLanRepository.findByDeviceIdAndLanguageCode(deviceId, langCode)
            .orElse(null);

        if (deviceLan != null) {
            if (dto.getName() != null) {
                deviceLan.setName(dto.getName());
            }
            if (dto.getDescription() != null) {
                deviceLan.setDescription(dto.getDescription());
            }
            if (dto.getLocationDescription() != null) {
                deviceLan.setLocationDescription(dto.getLocationDescription());
            }
        } else {
            deviceLan = DeviceLan.builder()
                .device(device)
                .language(language)
                .name(dto.getName())
                .description(dto.getDescription())
                .locationDescription(dto.getLocationDescription())
                .build();
        }

        deviceLan = deviceLanRepository.save(deviceLan);
        return deviceLanMapper.toDto(deviceLan);
    }

    @Override
    @Transactional
    public void deleteByDeviceIdAndLangCode(Long deviceId, String langCode) {
        DeviceLan deviceLan = deviceLanRepository.findByDeviceIdAndLanguageCode(deviceId, langCode)
            .orElseThrow(() -> new NotFoundException(
                "Translation not found for device " + deviceId + " and language " + langCode
            ));

        try {
            deviceLanRepository.delete(deviceLan);
        } catch (Exception ex) {
            throw new InternalServerException("Failed to delete DeviceLan", ex);
        }
    }
}
