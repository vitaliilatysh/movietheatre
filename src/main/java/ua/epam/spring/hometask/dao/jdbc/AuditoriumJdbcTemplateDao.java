package ua.epam.spring.hometask.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.epam.spring.hometask.dao.AuditoriumDao;
import ua.epam.spring.hometask.domain.Auditorium;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * @author Vitalii Latysh
 * Created: 18.02.2020
 */
@Repository
public class AuditoriumJdbcTemplateDao implements AuditoriumDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Auditorium getByName(@Nonnull String auditoriumName) {
        return null;
    }

    @Override
    public Auditorium save(@Nonnull Auditorium object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(@Nonnull Auditorium object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Auditorium getById(@Nonnull String id) {
        throw new UnsupportedOperationException();
    }

    @Nonnull
    @Override
    public Collection<Auditorium> getAll() {
        return null;
    }
}
