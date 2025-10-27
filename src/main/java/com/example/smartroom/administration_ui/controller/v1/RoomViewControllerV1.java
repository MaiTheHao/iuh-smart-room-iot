package com.example.smartroom.administration_ui.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.administration_ui.service.RoomViewService;
import com.example.smartroom.administration_ui.dto.RoomListViewDataDTO;
import com.example.smartroom.administration_ui.dto.RoomDetailViewDataDTO;

@Controller
@RequestMapping("/administration/components")
@RequiredArgsConstructor
public class RoomViewControllerV1 {

    private final RoomViewService roomViewService;

    @GetMapping("/rooms")
    public String getRooms(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        RoomListViewDataDTO roomListViewData = roomViewService.getRoomListViewData(pageable);
        model.addAttribute("roomListViewData", roomListViewData);
        return "pages/components/rooms";
    }

    @GetMapping("/rooms/{id}")
    public String getRoomDetail(
        @PathVariable String id,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        RoomDetailViewDataDTO roomDetailViewData = roomViewService.getRoomDetailViewData(id, pageable);
        model.addAttribute("roomDetailViewData", roomDetailViewData);
        return "pages/components/room-detail";
    }
}