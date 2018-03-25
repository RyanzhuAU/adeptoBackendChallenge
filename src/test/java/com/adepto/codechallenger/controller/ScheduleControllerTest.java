package com.adepto.codechallenger.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void loadShiftTest() throws Exception {
        //FIXME Fix this unit test for end point
//        this.mockMvc.perform(get("/loadShift"))
//                .andExpect(status().isOk());
    }

    @Test
    public void generateShiftTest() throws Exception {
        //FIXME Fix this unit test for end point
//        this.mockMvc.perform(get("/generateShift"))
//                .andExpect(status().isOk());
    }
}
