package com.bxy.indexmaker.domain;

import com.bxy.indexmaker.configuration.persistence.AbstractJpaDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RowContentRepositoryImpl extends AbstractJpaDao<Long, RowContent> implements RowContentRepository {

    protected RowContentRepositoryImpl() {
        super(RowContent.class);
    }

    @Override
    public void addRowContent(RowContent rowContent) {
        persist(rowContent);
        System.out.println("rowContent added: " + rowContent.toString());
    }

    @Override
    public List<RowContent> findAllRowContents() {
        return getEntityManager().createQuery("select r from RowContent r" , RowContent.class)
                .getResultList();
    }



    @Override
    public RowContent findRowContent(Long id) {
        return getEntityManager().createQuery("select r from RowContent r where r.id = :id" , RowContent.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
