package com.bxy.indexmaker.persistence;

import org.springframework.stereotype.Repository;

import java.util.List;


public interface RowContentRepository {
    void addRowContent(RowContent rowContent);
    List<RowContent> findAllRowContents();
    RowContent findRowContent(Long id);
}
