package se331.rest.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se331.rest.security.dao.UserDao;
import se331.rest.security.entity.User;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;
    @Override
    public User save(User user) {
        return userDao.save(user);
    }
}
