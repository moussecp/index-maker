package com.bxy.indexmaker.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;

@Entity
@Table(name = "ROW_CONTENT")
public class RowContent implements Identifiable<Long> {
    @Id
    @GeneratedValue(generator = "ROWCONTENT_SEQ_GEN")
    @SequenceGenerator(name = "ROWCONTENT_SEQ_GEN", sequenceName = "ROWCONTENT_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String firstCell;
    @Column
    private String secondCell;
    @Column
    private String thirdCell;
    @Column
    private String fourthCell;

    public RowContent() {
    }

    protected RowContent(String firstCell, String secondCell, String thirdCell, String fourthCell) {
        this.firstCell = setValueOrEmpty(firstCell);
        this.secondCell = setValueOrEmpty(secondCell);
        this.thirdCell = setValueOrEmpty(thirdCell);
        this.fourthCell = setValueOrEmpty(fourthCell);
    }

    private String setValueOrEmpty(String value) {
        return value != null ? value : Strings.EMPTY;
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getFirstCell() {
        return firstCell;
    }

    void setFirstCell(String firstCell) {
        this.firstCell = firstCell;
    }

    public String getSecondCell() {
        return secondCell;
    }

    void setSecondCell(String secondCell) {
        this.secondCell = secondCell;
    }

    public String getThirdCell() {
        return thirdCell;
    }

    void setThirdCell(String thirdCell) {
        this.thirdCell = thirdCell;
    }

    public String getFourthCell() {
        return fourthCell;
    }

    void setFourthCell(String fourthCell) {
        this.fourthCell = fourthCell;
    }

    public boolean hasAllFieldsEmpty() {
        return firstCell.isEmpty() && secondCell.isEmpty() && thirdCell.isEmpty() && fourthCell.isEmpty();
    }
}
