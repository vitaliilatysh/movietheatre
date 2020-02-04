package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.User;

import javax.annotation.Nonnull;

public interface UserDao extends BaseDao<User> {

    User getUserByEmail(@Nonnull String userEmail);
}
