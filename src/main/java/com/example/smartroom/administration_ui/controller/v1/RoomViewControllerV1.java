package com.example.smartroom.administration_ui.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort; // Thêm import này
import org.springframework.data.web.PageableDefault; // Thêm import này
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.smartroom.administration_ui.service.RoomViewService;
import com.example.smartroom.administration_ui.dto.rooms.RoomDetailViewDataDTO;
import com.example.smartroom.administration_ui.dto.rooms.RoomListViewDataDTO;
import com.example.smartroom.administration_ui.constants.ViewTemplatePathConstants;

@Controller
@RequestMapping("/administration/components")
@RequiredArgsConstructor
public class RoomViewControllerV1 {

    private final RoomViewService roomViewService;

    @GetMapping("/rooms")
    public String getRooms(
        @RequestParam(name = "search", required = false) String search,
        @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
        Model model
    ) {
        // 3. (BẮT BUỘC) Cập nhật service call để truyền 'search'
        // Bạn SẼ CẦN SỬA LẠI RoomViewService.getRoomListViewData 
        // để chấp nhận tham số 'search'.
        RoomListViewDataDTO roomListViewData = roomViewService.getRoomListViewData(search, pageable);
        
        model.addAttribute("roomListViewData", roomListViewData);
        
        // Không cần add 'search' vào model, vì fragment 'roomFilters'
        // của chúng ta đã dùng 'param.search' để tự điền lại giá trị.
        
        return ViewTemplatePathConstants.ROOMS_INDEX;
    }

    @GetMapping("/rooms/{id}")
    public String getRoomDetail(
        @PathVariable String id,
        @PageableDefault(size = 10) Pageable pageable,
        Model model
    ) {
        RoomDetailViewDataDTO roomDetailViewData = roomViewService.getRoomDetailViewData(id, pageable);
        model.addAttribute("roomDetailViewData", roomDetailViewData);
        return ViewTemplatePathConstants.ROOMS_DETAIL;
    }
}