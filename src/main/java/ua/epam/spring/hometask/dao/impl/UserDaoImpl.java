package ua.epam.spring.hometask.dao.impl;

import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.ItemAlreadyExistException;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;


public class UserDaoImpl implements UserDao {


    private Store store;

    public UserDaoImpl() {
    }

    @Override
    public User getUserByEmail(@Nonnull String userEmail) {
        Optional<User> foundUser = store.getUserMap().values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(userEmail))
                .findAny();
        return foundUser.orElseThrow(() -> new ItemNotFoundException("User not found by email" + userEmail));
    }

    @Override
    public User save(@Nonnull User object) {
        Optional<User> foundUser = store.getUserMap().values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(object.getEmail()))
                .findAny();
        if (foundUser.isPresent()) {
            throw new ItemAlreadyExistException("User already registered with such email " + object.getEmail());
        }

        String uniqueID = UUID.randomUUID().toString();
        object.setId(uniqueID);

        store.getUserMap().put(uniqueID, object);
        return store.getUserMap().get(uniqueID);
    }

    @Override
    public void remove(@Nonnull User object) {
        String userId = object.getId();
        User foundUser = store.getUserMap().get(userId);
        if (foundUser == null) {
            throw new ItemNotFoundException("User not found by id: " + userId);
        }
        store.getUserMap().remove(userId);
    }

    @Override
    public User getById(@Nonnull String id) {
        User foundUser = store.getUserMap().get(id);
        if (foundUser == null) {
            throw new ItemNotFoundException("User not found by id: " + id);
        }
        return foundUser;
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return store.getUserMap().values();
    }
}
