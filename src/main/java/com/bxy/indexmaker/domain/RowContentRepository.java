package com.bxy.indexmaker.domain;

import java.util.Collection;
import java.util.List;


public interface RowContentRepository extends Dao<Long, RowContent> {
    void addRowContent(RowContent rowContent);
    void addAll(Collection<RowContent> rowContents);
    List<RowContent> findAllRowContents();
    RowContent findRowContent(Long id);
}
