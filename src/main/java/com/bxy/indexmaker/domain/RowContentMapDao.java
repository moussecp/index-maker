package com.bxy.indexmaker.domain;

import com.bxy.indexmaker.configuration.persistence.AbstractMapDao;
import org.hibernate.exception.ConstraintViolationException;

import java.sql.SQLException;
import java.util.*;

public class RowContentMapDao extends AbstractMapDao<RowContent> implements RowContentRepository {

    private static Map<Long, RowContent> rowContents = new TreeMap<>();

    @Override
    public void addRowContent(RowContent rowContent) {
        if(rowContents.containsKey(rowContent.getId())) {
            throw new ConstraintViolationException("A rowContent already exists with this  id", new SQLException(), "ID");
        }
        rowContents.put(rowContent.getId(), rowContent);
    }

    @Override
    public void addAll(Collection<RowContent> rowContents) {
        for(RowContent rowContent : rowContents) {
            addRowContent(rowContent);
        }
    }

    @Override
    public List<RowContent> findAllRowContents() {
//        System.out.println("results: " + rowContents);
        return new ArrayList<>(rowContents.values());
    }

    @Override
    public RowContent findRowContent(Long id) {
        return rowContents.get(id);
    }

    @Override
    public void updateRowContent(RowContent rowContent) {
        rowContents.put(rowContent.getId(), rowContent);
    }

    @Override
    public RowContent findLastRowContent() {
        Optional<Long> lastKey = rowContents.keySet().stream().max(Long::compareTo);
        return rowContents.get(lastKey.get());
    }
}
