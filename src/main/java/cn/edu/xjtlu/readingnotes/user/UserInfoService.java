package cn.edu.xjtlu.readingnotes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.edu.xjtlu.readingnotes.util.UserInfo;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = repo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));
        return UserInfo.build(user);
    }
}
