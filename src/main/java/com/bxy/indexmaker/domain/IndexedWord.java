package com.bxy.indexmaker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "INDEXED_WORD")
public class IndexedWord implements Identifiable<Long> {
    @Id
    @GeneratedValue(generator = "INDEXEDWORD_SEQ_GEN")
    @SequenceGenerator(name = "INDEXEDWORD_SEQ_GEN", sequenceName = "INDEXEDWORD_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String word;
    @Column
    private int count = 0;

    public IndexedWord() {
    }

    public IndexedWord(String word) {
        this.word = word;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("word", word)
                .append("count", count)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexedWord that = (IndexedWord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(word, that.word) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, count);
    }
}
