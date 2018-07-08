package com.bxy.indexmaker.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SUBCHAPTER")
public class SubChapter implements Identifiable<Long> {
    @Id
    @GeneratedValue(generator = "SUBCHAPTER_SEQ_GEN")
    @SequenceGenerator(name = "SUBCHAPTER_SEQ_GEN", sequenceName = "SUBCHAPTER_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String subChapter;
    @Column
    private int counter = 1;

    public SubChapter() {
    }

    public SubChapter(String subChapter) {
        this.subChapter = subChapter;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubChapter() {
        return subChapter;
    }

    public void setSubChapter(String subChapter) {
        this.subChapter = subChapter;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubChapter that = (SubChapter) o;
        return Objects.equals(subChapter, that.subChapter);
    }

    @Override
    public int hashCode() {

        return Objects.hash(subChapter);
    }

    public void incrementCounter() {
        this.counter ++;
    }
}
