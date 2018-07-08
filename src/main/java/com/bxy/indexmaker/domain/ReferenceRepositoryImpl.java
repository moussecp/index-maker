package com.bxy.indexmaker.domain;

import com.bxy.indexmaker.configuration.persistence.AbstractJpaDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReferenceRepositoryImpl extends AbstractJpaDao<Long, Reference> implements ReferenceRepository {

    protected ReferenceRepositoryImpl() {
        super(Reference.class);
    }


    @Override
    public void createOrUpdateReference(String word, String subChapter, RowContent rowContent) {
        //TODO
    }

    @Override
    public List<Reference> findTopReferencesMinusBlackListed() {
        //TODO
        return null;
    }

    @Override
    public Reference findReference(Long id) {
        //TODO
        return null;
    }

    @Override
    public Reference findReferenceWithWord(String word) {
        //TODO
        return null;
    }
}
