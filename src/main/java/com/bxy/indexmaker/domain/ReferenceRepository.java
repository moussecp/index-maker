package com.bxy.indexmaker.domain;

import java.util.List;


public interface ReferenceRepository extends Dao<Long, Reference> {
    void createOrUpdateReference(String word, String subChapter, RowContent rowContent);
    List<Reference> findTopReferencesMinusBlackListed();
    Reference findReference(Long id);
    Reference findReferenceWithWord(String word);
    void clearAll();
}
