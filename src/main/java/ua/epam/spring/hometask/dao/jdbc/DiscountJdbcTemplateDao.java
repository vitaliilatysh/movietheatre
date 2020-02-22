package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.DiscountDao;
import ua.epam.spring.hometask.dao.UserDao;
import ua.epam.spring.hometask.domain.Discount;
import ua.epam.spring.hometask.domain.StrategyType;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.exceptions.ItemNotFoundException;

import javax.annotation.Nonnull;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

@Repository
public class DiscountJdbcTemplateDao implements DiscountDao {

    private static final String SELECT_FROM_DISCOUNTS = "select * from Discounts";
    private static final String DELETE_FROM_DISCOUNTS_WHERE_ID = "delete from Discounts where id = ?";
    private static final String SELECT_FROM_DISCOUNTS_WHERE_TYPE = "select * from Discounts where type = ?";
    private static final String SELECT_FROM_DISCOUNTS_WHERE_ID = "select * from Discounts where id = ?";
    private static final String INSERT_DISCOUNT_INTO_DISCOUNTS = "insert into Discounts (type, user_id) values (?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Override
    public void remove(@Nonnull Discount object) {
        Discount foundUser = getById(object.getId());
        jdbcTemplate.update(DELETE_FROM_DISCOUNTS_WHERE_ID, foundUser.getId());
    }

    @Override
    public Discount save(@Nonnull Discount object) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(INSERT_DISCOUNT_INTO_DISCOUNTS, new String[]{"ID"});
            statement.setString(1, object.getTypeDiscount().name());
            statement.setString(2, object.getUser().getId());
            return statement;
        }, keyHolder);

        return getById(String.valueOf(keyHolder.getKey()));
    }

    @Override
    public Discount getById(@Nonnull String id) {
        try {
            return jdbcTemplate.queryForObject(SELECT_FROM_DISCOUNTS_WHERE_ID, new Object[]{id}, (resultSet, rowNum) -> {
                Discount discount = new Discount();
                discount.setId(resultSet.getString("id"));
                discount.setTypeDiscount(StrategyType.valueOf(resultSet.getString("type")));

                String user_id = resultSet.getString("user_id");
                if (user_id == null) {
                    discount.setUser(null);
                    return discount;
                }
                User user = userDao.getById(user_id);
                discount.setUser(user);
                return discount;
            });
        } catch (EmptyResultDataAccessException e) {
            throw new ItemNotFoundException("Discount not found by id: " + id);
        }
    }

    @Nonnull
    @Override
    public Collection<Discount> getAll() {
        return jdbcTemplate.query(SELECT_FROM_DISCOUNTS, resultSet -> {
            Collection<Discount> result = new ArrayList<>();
            while (resultSet.next()) {
                Discount discount = new Discount();
                discount.setId(resultSet.getString("id"));
                discount.setTypeDiscount(StrategyType.valueOf(resultSet.getString("type")));

                String user_id = resultSet.getString("user_id");
                if (user_id == null) {
                    discount.setUser(null);
                    result.add(discount);
                    continue;
                }
                User user = userDao.getById(user_id);
                discount.setUser(user);

                result.add(discount);
            }
            return result;
        });
    }

    @Override
    public Collection<Discount> getByType(String type) {
        return jdbcTemplate.query(SELECT_FROM_DISCOUNTS_WHERE_TYPE, new Object[]{type}, (resultSet, rowNum) -> {
            Discount discount = new Discount();
            discount.setId(resultSet.getString("id"));
            discount.setTypeDiscount(StrategyType.valueOf(resultSet.getString("type")));

            String user_id = resultSet.getString("user_id");
            if (user_id == null) {
                discount.setUser(null);
                return discount;
            }
            User user = userDao.getById(user_id);
            discount.setUser(user);

            return discount;
        });
    }
}
