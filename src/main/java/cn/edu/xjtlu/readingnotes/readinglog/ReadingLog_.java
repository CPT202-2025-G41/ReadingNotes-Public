package cn.edu.xjtlu.readingnotes.readinglog;

import java.time.LocalDate;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ReadingLog.class)
public class ReadingLog_ {
    public static volatile SingularAttribute<ReadingLog, Long> id;
    public static volatile SingularAttribute<ReadingLog, String> title;
    public static volatile SingularAttribute<ReadingLog, String> author;
    public static volatile SingularAttribute<ReadingLog, LocalDate> date;
    public static volatile SingularAttribute<ReadingLog, Integer> spentTime;
    public static volatile SingularAttribute<ReadingLog, String> note;
    public static volatile SingularAttribute<ReadingLog, Long> userId;
}
