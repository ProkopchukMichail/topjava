package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.web.AbstractControllerTest;

/**
 * Created by comp on 09.06.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest{

    private static final String MEAL_URL=MealRestController.MEAL_REST_URI+'/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(MEAL_URL+ MealTestData.MEAL1_ID))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER.contentMatcher(MealTestData.MEAL1));
    }
}
