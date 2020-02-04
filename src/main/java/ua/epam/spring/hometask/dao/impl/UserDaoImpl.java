package ua.epam.spring.hometask.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;
import ua.epam.spring.hometask.storage.Store;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private Store store;

    @Override
    public User getUserByEmail(@Nonnull String userEmail) {
        Optional<User> foundUser = store.getUserMap().values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(userEmail))
                .findAny();
        return foundUser.orElseThrow(() -> new ItemNotFoundException("User not found by email" + userEmail));
    }

    @Override
    public User save(@Nonnull User object) {
        long uniqueID = Long.valueOf(UUID.randomUUID().toString());
        store.getUserMap().put(uniqueID, object);
        return store.getUserMap().get(uniqueID);
    }

    @Override
    public void remove(@Nonnull User object) {
        Optional.ofNullable(store.getUserMap().remove(object.getId()))
                .orElseThrow(() -> new ItemNotFoundException("User not found: " + object));
    }

    @Override
    public User getById(@Nonnull Long id) {
        return Optional.of(store.getUserMap().get(id))
                .orElseThrow(() -> new ItemNotFoundException("User not found by id: " + id));
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return store.getUserMap().values();
    }
}
