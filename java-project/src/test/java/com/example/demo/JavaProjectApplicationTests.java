package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.boot.test.context.SpringBootTest;

@WebMvcTest(UserController.class)
class JavaProjectApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSayHello() throws Exception {
        mockMvc.perform(get("/hello").param("name", "Trae"))
               .andExpect(status().isOk())
               .andExpect(content().string("Hello, Trae!"));
    }
}

@SpringBootTest
// 为避免类名重复，将类名修改为 JavaProjectAdditionalTests
class JavaProjectAdditionalTests {

	@Test
	void contextLoads() {
	}

}
