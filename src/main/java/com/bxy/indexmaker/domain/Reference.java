package com.bxy.indexmaker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "REFERENCE")
public class Reference implements Identifiable<Long> {
    @Id
    @GeneratedValue(generator = "REFERENCE_SEQ_GEN")
    @SequenceGenerator(name = "REFERENCE_SEQ_GEN", sequenceName = "REFERENCE_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String word;
    @Column
    private int count = 0;
    @OneToMany
    private List<RowContent> rowContents = new ArrayList<>();

    public List<RowContent> getRowContents() {
        return rowContents;
    }

    public void setRowContents(List<RowContent> rowContents) {
        this.rowContents = rowContents;
    }

    public Reference() {
    }

    private Reference(String word, RowContent rowContent) {
        this.word = word;
        rowContents.add(rowContent);
        ++count;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference reference = (Reference) o;
        return getCount() == reference.getCount() &&
                Objects.equals(getId(), reference.getId()) &&
                Objects.equals(getWord(), reference.getWord()) &&
                Objects.equals(getRowContents(), reference.getRowContents());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getId(), getWord(), getCount(), getRowContents());
    }

    public void addRowContent(RowContent rowContent) {
        rowContents.add(rowContent);
        count++;
    }

    public List<Long> getRowContentIds() {
        return rowContents.stream().map(rowContent -> rowContent.getId()).collect(Collectors.toList());
    }

    public static Builder builder() {
        return new Builder();
    }

    static class Builder{
        private String word;
        private RowContent rowContent;

        public Builder setWord(String word) {
            this.word = word;
            return this;
        }

        public Builder setRowContent(RowContent rowContent) {
            this.rowContent = rowContent;
            return this;
        }

        public Reference build() {
            return new Reference(this.word, this.rowContent);
        }
    }
}
