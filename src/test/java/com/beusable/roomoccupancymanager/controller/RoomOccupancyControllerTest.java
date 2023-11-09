package com.beusable.roomoccupancymanager.controller;

import com.beusable.roomoccupancymanager.exception.RoomOccupancyException;
import com.beusable.roomoccupancymanager.service.RoomBookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith({MockitoExtension.class})
public class RoomOccupancyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RoomBookingService roomBookingService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RoomOccupancyController(roomBookingService)).build();
    }

    @Test
    public void testGetRevenueSuccessful() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/revenue/available-rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rooms\": {\"premium\": 5, \"economy\": 10}}")
        ).andReturn();

        // Assert
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    public void testGetRevenueBadRequest() throws Exception {
        // Act
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/revenue/available-rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("")
        ).andReturn();

        // Assert
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(HttpStatus.BAD_REQUEST.value(), status);
    }


    @Test
    public void testGetRevenueInternalServerError() throws Exception {
        // Arrange
        doThrow(new RoomOccupancyException("No customers or availableRoomMap", HttpStatus.INTERNAL_SERVER_ERROR)).when(roomBookingService).calculateRevenue(Mockito.anyList(), Mockito.any());

        // Act
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/revenue/available-rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"rooms\": {\"premium\": 5, \"economy\": 10}}")
        ).andReturn();

        // Assert
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
        assertEquals("{\"message\":\"No customers or availableRoomMap\"}", response.getContentAsString());
    }
}