package com.example.smartroom.administration_ui.dto.hubs;

import org.springframework.data.domain.Page;

import com.example.smartroom.device_management.dto.hub.HubDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class HubListViewDataDTO {

    Page<HubDTO> hubs;
}
