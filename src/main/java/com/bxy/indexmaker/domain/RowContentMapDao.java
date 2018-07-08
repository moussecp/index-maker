package com.bxy.indexmaker.domain;

import com.bxy.indexmaker.configuration.persistence.AbstractMapDao;

import java.util.ArrayList;
import java.util.List;

public class RowContentMapDao extends AbstractMapDao<RowContent> implements RowContentRepository {

    private static List<RowContent> rowContents = new ArrayList<>();
    private static Long index = 1L;

    @Override
    public void addRowContent(RowContent rowContent) {
        rowContent.setId(index++);
//        System.out.println("added: " + rowContent);
        rowContents.add(rowContent);
    }

    @Override
    public List<RowContent> findAllRowContents() {
//        System.out.println("results: " + rowContents);
        return rowContents;
    }

    @Override
    public RowContent findRowContent(Long id) {
        RowContent rowContent = rowContents
                .stream()
                .filter(rc -> rc.getId().equals(id))
                .findAny()
                .get();
//        System.out.println("found for id: " + id + "results: " + rowContent);
        return rowContent;
    }
}
