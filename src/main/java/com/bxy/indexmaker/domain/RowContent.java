package com.bxy.indexmaker.domain;

import com.bxy.indexmaker.service.html.HtmlTagsUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Entity
@Table(name = "ROW_CONTENT")
public class RowContent implements Identifiable<Long>, Comparable<RowContent> {
    @Id
    @GeneratedValue(generator = "ROWCONTENT_SEQ_GEN")
    @SequenceGenerator(name = "ROWCONTENT_SEQ_GEN", sequenceName = "ROWCONTENT_SEQ", allocationSize = 1)
    private Long id;

    @Column
    private String content;
    @Column
    private String chapter;
    @Column
    private String subChapter;
    @Column
    private String section;
    @Column
    private String subSection;
    @Column
    private String subSubSection;
    @Column
    private String notes;
    @Column
    private ListType listType;
    @ElementCollection
    private List<String> boldText = new ArrayList<>();
    private static Long idIndex = 0L;

    public RowContent() {
    }

    private static int headerIndex = 0;

    public RowContent(
            String content,
            String chapter,
            String subChapter,
            String section,
            String subSection,
            String subSubSection,
            String notes,
            ListType listType,
            List<String> boldText) {
        this.id = ++idIndex;
        this.content = setValueOrEmpty(content);
        this.chapter = setValueOrEmpty(chapter);
        this.subChapter = setValueOrEmpty(subChapter);
        this.section = setValueOrEmpty(section);
        this.subSection = setValueOrEmpty(subSection);
        this.subSubSection = setValueOrEmpty(subSubSection);
        this.notes = setValueOrEmpty(notes);
        this.listType = listType;
        this.boldText = boldText;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }

    public List<String> getBoldText() {
        return boldText;
    }

    public void setBoldText(List<String> boldText) {
        this.boldText = boldText;
    }

    @Override
    public Long getId() {
        return id;
    }

    void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    void setContent(String firstCell) {
        this.content = firstCell;
    }

    public String getChapter() {
        return chapter;
    }

    void setChapter(String secondCell) {
        this.chapter = secondCell;
    }

    public String getSubChapter() {
        return subChapter;
    }

    void setSubChapter(String thirdCell) {
        this.subChapter = thirdCell;
    }

    public String getSection() {
        return section;
    }

    void setSection(String fourthCell) {
        this.section = fourthCell;
    }

    public String getSubSection() {
        return subSection;
    }

    public void setSubSection(String subSection) {
        this.subSection = subSection;
    }

    public String getSubSubSection() {
        return subSubSection;
    }

    public void setSubSubSection(String subSubSection) {
        this.subSubSection = subSubSection;
    }

    public String getNotes() {
        return notes;
    }

    public ListType getListType() {
        return listType;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean hasAllFieldsEmpty() {
        return content.isEmpty() && chapter.isEmpty() && subChapter.isEmpty() && section.isEmpty();
    }

    public List<String> getAllCellContents() {
        return Arrays.asList(content, chapter, subChapter, section, subSection, subSubSection, notes);
    }

    private String setValueOrEmpty(String value) {
        return value != null ? value : EMPTY;
    }

    public List<String> getFullHeadersConverted() {
        return Arrays.asList(
                getValidOrEmpty(chapter),
                getValidOrEmpty(subChapter),
                getValidOrEmpty(section),
                getValidOrEmpty(subSection),
                getValidOrEmpty(subSubSection))
                ;
    }

    private String getValidOrEmpty(String value) {
        return isValid(value) ? value : EMPTY;
    }

    private boolean isValid(String value) {
        return value != null && !value.isEmpty() && !HtmlTagsUtils.N_A.equals(value);
    }

    public String getFullHeadersId() {
        return getFullHeadersConverted()
                .stream()
                .collect(Collectors.joining("-"))
                .toLowerCase()
                .replace(" ", "-")
//                .concat(String.format("-%03d", ++headerIndex))
                ;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("content", content)
                .append("chapter", chapter)
                .append("subChapter", subChapter)
                .append("section", section)
                .append("subSection", subSection)
                .append("subSubSection", subSubSection)
                .append("notes", notes)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowContent that = (RowContent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(content, that.content) &&
                Objects.equals(chapter, that.chapter) &&
                Objects.equals(subChapter, that.subChapter) &&
                Objects.equals(section, that.section) &&
                Objects.equals(subSection, that.subSection) &&
                Objects.equals(subSubSection, that.subSubSection) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, chapter, subChapter, section, subSection, subSubSection, notes);
    }

    @Override
    public int compareTo(RowContent rowContent) {
        return this.getId().compareTo(rowContent.getId());
    }

    public String getChapterPlusSubChapter() {
        return chapter + subChapter;
    }


    public String getChapterPlusSubChapterPlusSection() {
        return chapter + subChapter + section;
    }

    public boolean hasContentDuplicatingAHeaderTitle() {
        return content != null
                && (content.equals(chapter) ||
                content.equals(subChapter) ||
                content.equals(section) ||
                content.equals(subSection) ||
                content.equals(subSubSection));
    }

    public boolean isListElement() {
        return listType.isList();
    }

    public boolean isClassicList() {
        return listType.isClassicList();
    }

    public boolean isEnumeratedList() {
        return listType.isEnumeratedList();
    }
}
