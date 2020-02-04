package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.AuditoriumService;
import ua.epam.spring.hometask.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    @Autowired
    private AuditoriumDao userDao;

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return null;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return null;
    }
}
