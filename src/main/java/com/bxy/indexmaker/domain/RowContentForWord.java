package com.bxy.indexmaker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ROW_CONTENT_FOR_WORD")
public class RowContentForWord implements Identifiable<Long> {
    @Id
    @GeneratedValue(generator = "ROW_CONTENT_FOR_WORD_SEQ_GEN")
    @SequenceGenerator(name = "ROW_CONTENT_FOR_WORD_SEQ_GEN", sequenceName = "ROW_CONTENT_FOR_WORD_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private Long wordId;
    @Column
    private Long rowContentId;

    public RowContentForWord() {
    }

    public RowContentForWord(Long wordId, Long rowContentId) {
        this.wordId = wordId;
        this.rowContentId = rowContentId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Long getRowContentId() {
        return rowContentId;
    }

    public void setRowContentId(Long rowContentId) {
        this.rowContentId = rowContentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowContentForWord that = (RowContentForWord) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(wordId, that.wordId) &&
                Objects.equals(rowContentId, that.rowContentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wordId, rowContentId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("wordId", wordId)
                .append("rowContentId", rowContentId)
                .toString();
    }
}
