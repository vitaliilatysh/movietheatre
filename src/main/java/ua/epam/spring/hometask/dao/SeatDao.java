package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Seat;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface SeatDao extends BaseDao<Seat> {

    Collection<Seat> getByAuditoriumId(@Nonnull String auditoriumId);
}
