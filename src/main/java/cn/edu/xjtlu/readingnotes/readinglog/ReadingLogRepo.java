package cn.edu.xjtlu.readingnotes.readinglog;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReadingLogRepo
        extends CrudRepository<ReadingLog, Long>,
                JpaSpecificationExecutor<ReadingLog> {
    public List<ReadingLog> findAll();
    public List<ReadingLog> findAll(Specification<ReadingLog> spec);
    public List<ReadingLog> findAllByUserId(Long userId);
    public Long deleteByUserId(Long userId);
}
