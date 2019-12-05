package com.marin.phonebook.ui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marin.phonebook.data.RecordEntity;
import com.marin.phonebook.service.RecordsServiceImpl;
import com.marin.phonebook.shared.RecordDto;
import com.marin.phonebook.ui.model.request.CreateUpdateRecordRequestModel;
import com.marin.phonebook.ui.model.request.DeleteRecordRequestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RecordsControllerTest {

    @MockBean
    RecordsServiceImpl recordsService;

    @Autowired
    private MockMvc mockMvc;

    RecordDto mockDto;
    RecordEntity mockEntity;

    @BeforeEach
    void setUp() {

        initMocks(this);

        mockDto = new RecordDto();
        mockDto.setPersonName("Marin Puizina");
        mockDto.setPhoneNumber("2343939123291");
        mockDto.setRecordType("mobilni");

        mockEntity = new RecordEntity();
        mockEntity.setPersonName("Marin Puizina");
        mockEntity.setPhoneNumber("2343939123291");
        mockEntity.setRecordType("mobilni");

    }

    @Test
    void createUpdateRecordShouldReturnStatusCreated() throws Exception{

        CreateUpdateRecordRequestModel model = new CreateUpdateRecordRequestModel();
        model.setPersonName("Marin Puizina");
        model.setPhoneNumber("2343939123291");
        model.setRecordType("mobilni");

        String mockValue = "Created";

        when(recordsService.createUpdateRecord(mockDto)).thenReturn(mockValue);

        mockMvc.perform(post("/records")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(model)))
                .andExpect(status().isCreated());

    }

    @Test
    void createUpdateRecordShouldReturnStatusNoContent() throws Exception{

        CreateUpdateRecordRequestModel model = new CreateUpdateRecordRequestModel();
        model.setPersonName("Marin Puizina");
        model.setPhoneNumber("2343939123291");
        model.setRecordType("mobilni");

        String mockValue = "Updated";
        String urlTemplate = "/records";

        when(recordsService.createUpdateRecord(mockDto)).thenReturn(mockValue);

        mockMvc.perform(post(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(model)))
                .andExpect(status().isNoContent());

    }

    @Test
    void getRecordByNameShouldReturnStatusOk() throws Exception {

        List<RecordDto> testValue = new ArrayList<>();
        testValue.add(mockDto);

        String personName = "Marin Puizina";
        String urlTemplate = "/records/names/{name}";

        when(recordsService.findRecordsUsingPersonName(personName)).thenReturn(testValue);

        mockMvc.perform(get(urlTemplate, personName))
                .andExpect(status().isOk());

    }

    @Test
    void getRecordByPhoneNumberShouldReturnStatusOk() throws Exception {

        List<RecordDto> testValue = new ArrayList<>();
        testValue.add(mockDto);

        String phoneNumber = "2343939123291";
        String urlTemplate = "/records/phone_numbers/{phoneNumber}";

        when(recordsService.findRecordUsingPhoneNumber(phoneNumber)).thenReturn(testValue);

        mockMvc.perform(get(urlTemplate, phoneNumber))
                .andExpect(status().isOk());

    }

    @Test
    void deleteRecordShouldReturnStatusNoContent() throws Exception {

        DeleteRecordRequestModel model = new DeleteRecordRequestModel();
        model.setPhoneNumber("2343939123291");

        String urlTemplate = "/records";

        mockMvc.perform(delete(urlTemplate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(model)))
                .andExpect(status().isNoContent());

    }


    @Test
    void deleteRecordUriShouldReturnStatusNoContent() throws Exception {

        String phoneNumber = "2343939123291";
        String urlTemplate = "/records/phone_numbers/{phoneNumber}";

        mockMvc.perform(delete(urlTemplate, phoneNumber))
                .andExpect(status().isNoContent());

    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}