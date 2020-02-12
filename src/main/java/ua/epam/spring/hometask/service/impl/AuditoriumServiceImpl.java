package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.service.AuditoriumService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

@Service
public class AuditoriumServiceImpl implements AuditoriumService {

    @Autowired
    private AuditoriumDao auditoriumDao;

    public AuditoriumServiceImpl() {
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() {
        return auditoriumDao.getAll();
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        return auditoriumDao.getByName(name);
    }
}
