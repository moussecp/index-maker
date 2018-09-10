package com.bxy.indexmaker.domain;

import com.bxy.indexmaker.configuration.persistence.AbstractJpaDao;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class RowContentRepositoryImpl extends AbstractJpaDao<Long, RowContent> implements RowContentRepository {

    public RowContentRepositoryImpl() {
        super(RowContent.class);
    }

    @Override
    public void addRowContent(RowContent rowContent) {
        persist(rowContent);
//        System.out.println("rowContent added: " + rowContent.toString());
    }

    @Override
    public void addAll(Collection<RowContent> rowContents) {
        for (RowContent rowContent : rowContents) {
            addRowContent(rowContent);
        }
    }

    @Override
    public List<RowContent> findAll() {
        return findAllRowContents();
    }

    @Override
    public List<RowContent> findAllRowContents() {
        return getEntityManager().createQuery("select r from RowContent r", RowContent.class)
                .getResultList();
    }

    @Override
    public Optional<RowContent> find(Long id) {
        throw new RuntimeException("DO NOT USE THIS METHOD: find(id)");
    }

    @Override
    public RowContent findRowContent(Long id) {
        return getEntityManager().createQuery("select r from RowContent r where r.id = :id", RowContent.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void updateRowContent(RowContent rowContent) {
        //TODO
    }

    @Override
    public RowContent findLastRowContent() {
        return null;
    }


}
