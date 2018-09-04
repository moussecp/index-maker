package com.bxy.indexmaker.domain;

import java.util.List;


public interface RowContentRepository extends Dao<Long, RowContent> {
    void addRowContent(RowContent rowContent);
    void addAll(List<RowContent> rowContents);
    List<RowContent> findAllRowContents();
    RowContent findRowContent(Long id);
}
