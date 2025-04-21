package cn.edu.xjtlu.readingnotes.readinglog;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ReadingLogService {

    @Autowired
    private ReadingLogRepo repo;

    public List<ReadingLog> getLogs(Long userId) throws Exception {
        if (userId == null) {
            return repo.findAll();
        }
        return repo.findAllByUserId(userId);
    }

    public List<ReadingLog> filterLogs(Map<String,String> filters) {
        Specification<ReadingLog> specs = ReadingLogSpecs.specify(filters);
        return repo.findAll(specs);
    }

    public List<ReadingLog> filterLogs(Long userId, Map<String,String> filters) {
        Specification<ReadingLog> specs = ReadingLogSpecs.specify(userId, filters);
        return repo.findAll(specs);
    }

    public Optional<ReadingLog> getLog(Long logId) {
        return repo.findById(logId);
    }
}
