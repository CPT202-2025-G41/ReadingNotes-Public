package cn.edu.xjtlu.readingnotes;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

class UserJourneyTest extends AcceptanceTestBase {

    @Test
    void fullUserJourney() throws Exception {
       
        mockMvc.perform(post("/register")
                .param("username", "testuser")
                .param("password", "P@ssw0rd")
                .param("email", "test@example.com"))
            .andExpect(status().is3xxRedirection());

        
        mockMvc.perform(post("/login")
                .param("username", "testuser")
                .param("password", "P@ssw0rd"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/"));

        
        MvcResult result = mockMvc.perform(post("/log")
                .param("title", "Test Book")
                .param("author", "Test Author")
                .param("date", LocalDate.now().toString())
                .param("spentTime", "60")
                .param("note", "Sample content"))
            .andExpect(status().is3xxRedirection())
            .andReturn();

        
        String location = result.getResponse().getRedirectedUrl();
        mockMvc.perform(get(location))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Test Book")));

        
        mockMvc.perform(delete("/log/1"))
            .andExpect(status().is3xxRedirection());

        
        mockMvc.perform(get("/log"))
            .andExpect(status().isOk())
            .andExpect(content().string(not(containsString("Test Book"))));
    }
}
