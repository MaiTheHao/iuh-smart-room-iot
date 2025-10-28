package com.example.smartroom.administration_ui.service;

import org.springframework.data.domain.Pageable;

import com.example.smartroom.administration_ui.dto.hubs.HubDetailViewDataDTO;
import com.example.smartroom.administration_ui.dto.hubs.HubListViewDataDTO;

public interface HubViewService {
    HubListViewDataDTO getHubListViewData(Pageable pageable);
    HubDetailViewDataDTO getHubDetailViewData(String hubId, Pageable pageable);
}
