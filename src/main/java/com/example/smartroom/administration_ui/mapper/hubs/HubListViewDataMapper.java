package com.example.smartroom.administration_ui.mapper.hubs;

import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.example.smartroom.administration_ui.dto.hubs.HubListViewDataDTO;
import com.example.smartroom.device_management.dto.hub.HubDTO;

public interface HubListViewDataMapper {
    @Mapping(target = "hubs", source = "hubs")
    HubListViewDataDTO toDTO(Page<HubDTO> hubs);
}
