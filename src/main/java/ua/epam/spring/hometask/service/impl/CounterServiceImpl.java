package ua.epam.spring.hometask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.epam.spring.hometask.dao.CounterDao;
import ua.epam.spring.hometask.domain.Counter;
import ua.epam.spring.hometask.domain.Event;
import ua.epam.spring.hometask.service.CounterService;

import javax.annotation.Nonnull;
import java.util.Collection;

@Service
public class CounterServiceImpl implements CounterService {

    @Autowired
    private CounterDao counterDao;

    @Override
    public Counter save(@Nonnull Counter object) {
        return counterDao.save(object);
    }

    @Override
    public void remove(@Nonnull Counter object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Counter getById(@Nonnull String id) {
        return counterDao.getById(id);
    }

    @Nonnull
    @Override
    public Collection<Counter> getAll() {
        return counterDao.getAll();
    }

    @Override
    public int getCountByEventName(@Nonnull Event event) {
        return counterDao.getCountByEventName(event);
    }

    @Override
    public int getCountByPrice(@Nonnull Event event) {
        return counterDao.getCountByPrice(event);
    }

    @Override
    public int getCountByTicketBooked(@Nonnull Event event) {
        return counterDao.getCountByTicketBooked(event);
    }
}
