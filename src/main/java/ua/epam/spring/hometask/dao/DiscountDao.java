package ua.epam.spring.hometask.dao;

import ua.epam.spring.hometask.domain.Discount;

import java.util.Collection;

public interface DiscountDao extends BaseDao<Discount> {

    Collection<Discount> getByType(String type);
}
