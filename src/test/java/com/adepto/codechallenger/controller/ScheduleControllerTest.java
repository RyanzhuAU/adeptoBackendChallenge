package com.adepto.codechallenger.controller;

import com.adepto.codechallenger.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by ryan.zhu on 24/03/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {

    }

    @Test
    public void loadShiftTest() throws Exception {
        File shiftFile = new File(Constants.SHIFT_FILE_NAME);
        File staffFile = new File(Constants.STAFF_FILE_NAME);

        if (shiftFile.exists() && staffFile.exists()) {
            this.mockMvc.perform(get("/loadShift"))
                    .andExpect(status().isOk());
        }
        else {
            this.mockMvc.perform(get("/loadShift"))
                    .andExpect(status().isBadRequest());
        }
    }

    @Test
    public void generateShiftTest() throws Exception {
        this.mockMvc.perform(get("/generateShift"))
                .andExpect(status().isOk());
    }
}
