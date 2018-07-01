package com.bxy.indexmaker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Entity
@Table(name = "ROW_CONTENT")
public class RowContent implements Identifiable<Long> {
    @Id
    @GeneratedValue(generator = "ROWCONTENT_SEQ_GEN")
    @SequenceGenerator(name = "ROWCONTENT_SEQ_GEN", sequenceName = "ROWCONTENT_SEQ", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String firstCell;

    public RowContent() {
    }

    public RowContent(String firstCell) {
        checkArgument(!StringUtils.isEmpty(firstCell), "firstCell must not be null or empty");
        this.firstCell = firstCell;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstCell() {
        return firstCell;
    }

    public void setFirstCell(String firstCell) {
        this.firstCell = firstCell;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstCell", firstCell)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowContent that = (RowContent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstCell, that.firstCell);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstCell);
    }
}
