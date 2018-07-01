package com.bxy.indexmaker.domain;

import java.util.List;
import java.util.Optional;

public interface Dao<I, T extends Identifiable<I>> {
    Optional<T> find(I id);

    void persist(T entity);

    List<T> findAll();

    boolean exists(I id);
}
