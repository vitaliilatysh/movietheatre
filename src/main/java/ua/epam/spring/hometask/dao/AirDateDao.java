package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.AirDate;
import ua.epam.spring.hometask.domain.Event;

import java.util.Collection;

public interface AirDateDao extends BaseDao<AirDate> {

    Collection<AirDate> getAirDatesByEvent(Event event);
}
