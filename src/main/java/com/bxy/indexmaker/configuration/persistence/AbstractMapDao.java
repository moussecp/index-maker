package com.bxy.indexmaker.configuration.persistence;

import com.bxy.indexmaker.domain.Dao;
import com.bxy.indexmaker.domain.Identifiable;
import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AbstractMapDao<T extends Identifiable<Long>> implements Dao<Long, T> {
    private static long lastGeneratedId = 0l;
    private Map<Long, T> entities = new HashMap<Long, T>();

    private static Long generateId() {
        return Long.valueOf(++lastGeneratedId);
    }

    public Optional<T> find(Long id) {
        return Optional.of(entities.get(id));
    }

    public void persist(T entity) {
        entities.put(generateId(), entity);
    }

    public List<T> findAll() {
        return ImmutableList.copyOf(entities.values());
    }

    public boolean exists(Long id) {
        return entities.containsKey(id);
    }

    public long addEntity(T entity) {
        long generatedId = generateId();
        entities.put(generatedId, entity);
        return generatedId;
    }

    public boolean contains(T entity) {
        return entities.containsValue(entity);
    }

    protected Map<Long, T> getEntities() {
        return entities;
    }
}
