package com.bxy.indexmaker.domain;

import org.springframework.stereotype.Service;

@Service
public class RowContentFactory {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String content;
        private String chapter;
        private String subChapter;
        private String section;
        private String subSection;
        private String subSubSection;
        private String notes;

        private Builder() {
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }
        public Builder setChapter(String chapter) {
            this.chapter = chapter;
            return this;
        }
        public Builder setSubChapter(String subChapter) {
            this.subChapter = subChapter;
            return this;
        }
        public Builder setSection(String section) {
            this.section = section;
            return this;
        }
        public Builder setSubSection(String subSection) {
            this.subSection = subSection;
            return this;
        }
        public Builder setSubSubSection(String subSubSection) {
            this.subSubSection = subSubSection;
            return this;
        }
        public Builder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public RowContent build() {
            return new RowContent(content, chapter, subChapter, section, subSection, subSubSection, notes);
        }
    }
}
