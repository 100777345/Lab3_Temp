package com.ontariotechu.sofe3980U;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.junit.runner.RunWith;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.test.context.junit4.*;

import static org.hamcrest.Matchers.containsString;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BinaryController.class)
public class BinaryControllerTest {
	
    @Autowired
    private MockMvc mvc;

   
    @Test
    public void getDefault() throws Exception {
        this.mvc.perform(get("/"))//.andDo(print())
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", ""))
			.andExpect(model().attribute("operand1Focused", false));
    }
	
	@Test
    public void getParameter() throws Exception {
        this.mvc.perform(get("/").param("operand1","111"))
            .andExpect(status().isOk())
            .andExpect(view().name("calculator"))
			.andExpect(model().attribute("operand1", "111"))
			.andExpect(model().attribute("operand1Focused", true));
    }
	
    @Test
    //test an invalid operator
    public void missingOperator() throws Exception {
        this.mvc.perform(post("/").param("operand1", "10100").param("operator", "").param("operand2", "11111"))
                .andExpect(status().isOk())
                .andExpect(view().name("Error"))
                .andExpect(model().attributeDoesNotExist("result"))
                .andExpect(model().attribute("operand1", "10100"));
    }

    @Test
    //test no parameters
    public void missingParameters() throws Exception {
        this.mvc.perform(post("/").param("operand1", "").param("operator", "").param("operand2", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("Error"))
                .andExpect(model().attributeDoesNotExist("result"))
                .andExpect(model().attribute("operand1", ""));
    }
    
    @Test
    //test only one parameter
    public void missingOneParameter() throws Exception {
        this.mvc.perform(post("/").param("operand1", "10100").param("operator", "").param("operand2", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("Error"))
                .andExpect(model().attributeDoesNotExist("result"))
                .andExpect(model().attribute("operand1", "10100"));
    }

	@Test
    public void postAddParameter() throws Exception {
    this.mvc.perform(post("/").param("operand1","111").param("operator","+").param("operand2","111"))//.andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
		.andExpect(model().attribute("result", "1110"))
		.andExpect(model().attribute("operand1", "111"));
}
    
	@ParameterizedTest
	@CsvSource({
		"111, 111, 111",
		"1001, 10, 1011",
		"10, 1001, 1011",
		"1001, 0, 1001",
		"0, 0, 0"
	})
	public void postOrParameter(String op1, String op2, String expectedResult) throws Exception {
		this.mvc.perform(post("/").param("operand1",op1).param("operator","|").param("operand2",op2))//.andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", expectedResult))
        .andExpect(model().attribute("operand1", op1))
		.andExpect(model().attribute("operand2", op2));
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"111, 111, 111",
		"1001, 10, 0",
		"10, 1001, 0",
		"1001, 0, 0",
		"0, 0, 0"
	})
	public void postAndParameter(String op1, String op2, String expectedResult) throws Exception {
		this.mvc.perform(post("/").param("operand1",op1).param("operator","&").param("operand2",op2))//.andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", expectedResult))
        .andExpect(model().attribute("operand1", op1))
		.andExpect(model().attribute("operand2", op2));
	}
	
	
	@ParameterizedTest
	@CsvSource({
		"111, 111, 110001",
		"1001, 10, 10010",
		"10, 1001, 10010",
		"1001, 0, 0",
		"0, 0, 0"
	})
	public void postMultiplyParameter(String op1, String op2, String expectedResult) throws Exception {
		this.mvc.perform(post("/").param("operand1",op1).param("operator","*").param("operand2",op2))//.andDo(print())
        .andExpect(status().isOk())
        .andExpect(view().name("result"))
        .andExpect(model().attribute("result", expectedResult))
        .andExpect(model().attribute("operand1", op1))
		.andExpect(model().attribute("operand2", op2));
	}
	
}
