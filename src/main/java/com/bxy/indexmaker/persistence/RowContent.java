package com.bxy.indexmaker.persistence;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ROW_CONTENT")
public class RowContent {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "FIRST_CELL")
    private String firstCell;

    public RowContent() {
    }

    public RowContent(String firstCell) {
        this.firstCell = firstCell;
    }

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
        return "RowContent{" +
                "firstCell='" + firstCell + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowContent that = (RowContent) o;
        return Objects.equals(getFirstCell(), that.getFirstCell()) &&
                Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirstCell(), getId());
    }
}
