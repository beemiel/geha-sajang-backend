package com.incense.gehasajang.controller;

import com.incense.gehasajang.model.dto.RoomDto;
import com.incense.gehasajang.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    @Test
    @WithMockUser(username = "lynn", roles = {"SUB", "MAIN"})
    public void list() throws Exception {

        mockMvc.perform(get("/api/v1/houses/{houseId}/rooms", 1))
                .andExpect(status().isOk())
                .andExpect(content().encoding("UTF-8"));
    }
}
