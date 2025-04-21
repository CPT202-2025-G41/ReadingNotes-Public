package cn.edu.xjtlu.readingnotes.repository;

import cn.edu.xjtlu.readingnotes.readinglog.ReadingLog; 
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingLogRepository extends JpaRepository<ReadingLog, Long> {
}