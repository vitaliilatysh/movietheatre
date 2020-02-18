package ua.epam.spring.hometask.mappers;

import org.springframework.jdbc.core.RowMapper;
import ua.epam.spring.hometask.domain.Discount;
import ua.epam.spring.hometask.domain.StrategyType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Vitalii Latysh
 * Created: 17.02.2020
 */

public class DiscountMapper implements RowMapper<Discount> {

    @Override
    public Discount mapRow(ResultSet resultSet, int i) throws SQLException {
        Discount discount = new Discount();
        discount.setId(resultSet.getString("id"));
        discount.setUserId(resultSet.getString("user_id"));
        discount.setTypeDiscount(StrategyType.valueOf(resultSet.getString("type")));
        return discount;
    }
}
