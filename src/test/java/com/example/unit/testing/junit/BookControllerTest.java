package com.example.unit.testing.junit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper=new ObjectMapper();
    ObjectWriter objectWriter= objectMapper.writer();

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book Record_1=new Book(1,"atomic habits","how to build better habits",4);
    Book Record_2=new Book(2,"think fast and slow","how to create good mental model",4);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        //this.mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
    }

    // test for all records
    @Test
    public void getAllRecords() throws Exception{
        List<Book> records =new ArrayList<>(Arrays.asList(Record_1,Record_2));
        Mockito.when(bookRepository.findAll()).thenReturn(records);
        mockMvc.perform(MockMvcRequestBuilders.get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$[1].name",is("think fast and slow")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$"),hasSize(2));
    }

    // test for get
    @Test
    public void getBookById() throws Exception{
        Mockito.when(bookRepository.findById(Long.valueOf(Record_1.getBookId())))
                .thenReturn(java.util.Optional.of(Record_1));
        mockMvc.perform(MockMvcRequestBuilders.get("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // test for create
    @Test
    public void createBookRecord() throws Exception{
        Book record= Book.builder()
                .bookId(4)
                .name("Introduction to C++")
                .summary("C++ basics")
                .rating(5)
                .build();
        Mockito.when(bookRepository.save(record)).thenReturn(record);

        String content=objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest= MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        mockMvc.perform(mockRequest)
               .andExpect(status().isOk());
    }

    @Test
    public void updateBookRecords() throws Exception{
        Book updatedRecord= Book.builder()
                .bookId(1)
                .name("updated book name")
                .summary("updated summary")
                .rating(2)
                .build();
        Mockito.when(bookRepository.findById(Long.valueOf(Record_1.getBookId())))
                .thenReturn(Optional.ofNullable(Record_1));
        Mockito.when(bookRepository.save(updatedRecord)).thenReturn(updatedRecord);

        String updatedContent=objectWriter.writeValueAsString(updatedRecord);
        MockHttpServletRequestBuilder mockRequest=MockMvcRequestBuilders.put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());

    }

    @Test // Test for delete using TDD approach
    public void deleteBookRecords() throws Exception{

        Mockito.when(bookRepository.findById(Long.valueOf(Record_2.getBookId())))
                .thenReturn(Optional.ofNullable(Record_2));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/book/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }
}
