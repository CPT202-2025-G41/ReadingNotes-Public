package cn.edu.xjtlu.readingnotes;

import org.junit.jupiter.api.Test;

import cn.edu.xjtlu.readingnotes.readinglog.ReadingLog;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests
public class ReadingLogTest {
    @Test // Test class creation
    void testReadingLogCreation() {
        ReadingLog log = new ReadingLog("Test Title", "Test Author", LocalDate.now(), 30, "Test Note", 1L);

        assertNotNull(log, "ReadingLog object should not be null");
        assertEquals("Test Title", log.getTitle(), "Title should be set correctly");
        assertEquals("Test Author", log.getAuthor(), "Author should be set correctly");
        assertNotNull(log.getDate(), "Date should not be null");
        assertEquals(30, log.getSpentTime(), "Spent time should be set correctly");
        assertEquals("Test Note", log.getNote(), "Note content should be set correctly");
        assertEquals(1L, log.getUserId(), "User ID should be set correctly");
    }

    @Test // Test method usage
    void testReadingLogMethods() {

        ReadingLog log = new ReadingLog();

        log.setTitle("Test Title");
        log.setAuthor("Test Author");
        log.setDate(LocalDate.now());
        log.setSpentTime(30);
        log.setNote("Test Note");
        log.setUserId(1L);

        assertEquals("Test Title", log.getTitle(), "Title should be set correctly");
        assertEquals("Test Author", log.getAuthor(), "Author should be set correctly");
        assertNotNull(log.getDate(), "Date should not be null");
        assertEquals(30, log.getSpentTime(), "Spent time should be set correctly");
        assertEquals("Test Note", log.getNote(), "Note content should be set correctly");
        assertEquals(1L, log.getUserId(), "User ID should be set correctly");

        log.setTitle("Updated Title");
        log.setAuthor("Updated Author");
        log.setDate(LocalDate.now().plusDays(1));
        log.setSpentTime(45);
        log.setNote("Updated Note");
        log.setUserId(2L);

        assertEquals("Updated Title", log.getTitle(), "Title should be updated to 'Updated Title'");
        assertEquals("Updated Author", log.getAuthor(), "Author should be updated to 'Updated Author'");
        assertNotNull(log.getDate(), "Date should not be null");
        assertEquals(45, log.getSpentTime(), "Spent time should be updated to 45");
        assertEquals("Updated Note", log.getNote(), "Note content should be updated to 'Updated Note'");
        assertEquals(2L, log.getUserId(), "User ID should be updated to 2");
    }
}
