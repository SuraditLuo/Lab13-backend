package se331.rest.security.dao;

import se331.rest.entity.Event;
import se331.rest.security.entity.User;

public interface UserDao {
    User save(User user);
}