package cn.edu.xjtlu.readingnotes.readinglog;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
public class ReadingLogService {

    @Autowired
    private ReadingLogRepo repo;

    @PersistenceContext
    private EntityManager manager;

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

    public List<Object[]> getTitleProgressList(Long userId) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
        Root<ReadingLog> root = query.from(ReadingLog.class);

        query.multiselect(root.get("title"), builder.sum(root.get("spentTime")))
            .where(builder.equal(root.get("userId"), userId))
            .groupBy(root.get("title"));

        return manager.createQuery(query).getResultList();
    }
}
