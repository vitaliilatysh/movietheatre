package ua.epam.spring.hometask.service;

import ua.epam.spring.hometask.domain.DomainObject;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * @param <T> DomainObject subclass
 * @author Yuriy_Tkach
 */
public interface AbstractDomainObjectService<T extends DomainObject> {

    /**
     * Saving new object to storage or updating existing one
     *
     * @param object Object to save
     * @return saved object with assigned id
     */
    T save(@Nonnull T object);

    /**
     * Removing object from storage
     *
     * @param object Object to remove
     */
    void remove(@Nonnull T object);

    /**
     * Getting object by id from storage
     *
     * @param id id of the object
     * @return Found object or <code>null</code>
     */
    T getById(@Nonnull String id);

    /**
     * Getting all objects from storage
     *
     * @return collection of objects
     */
    @Nonnull
    Collection<T> getAll();
}
