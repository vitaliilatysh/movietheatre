package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;

public interface AuditoriumDao extends BaseDao<Auditorium> {

    Auditorium getByName(@Nonnull String auditoriumName);
}
