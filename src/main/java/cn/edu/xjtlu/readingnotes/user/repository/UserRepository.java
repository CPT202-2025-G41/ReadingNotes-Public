package cn.edu.xjtlu.readingnotes.user.repository;

import cn.edu.xjtlu.readingnotes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}