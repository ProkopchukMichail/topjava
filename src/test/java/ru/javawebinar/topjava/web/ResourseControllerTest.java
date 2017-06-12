package ru.javawebinar.topjava.web;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



/**
 * Created by comp on 09.06.2017.
 */
public class ResourseControllerTest extends AbstractControllerTest {
    @Test
    public void testResources() throws Exception {
        mockMvc.perform(get("/resources/css/style.css"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("text/css")));
    }
}
