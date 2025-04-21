package cn.edu.xjtlu.readingnotes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReadingnotesApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // Verify Spring context initialization
        assertNotNull(applicationContext, "Spring application context should be initialized");
    }

    @Test
    void mainBeanAvailabilityCheck() {
        // Verify critical beans existence
        assertTrue(applicationContext.containsBean("readingLogController"), 
            "ReadingLogController bean should be present");
        assertTrue(applicationContext.containsBean("readingLogService"), 
            "ReadingLogService bean should be present");
    }

}
