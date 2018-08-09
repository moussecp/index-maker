package com.bxy.indexmaker.domain;

import org.springframework.stereotype.Service;

@Service
public class RowContentFactory {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        public static final String N_A = "N/A";
        private String content = N_A;
        private String chapter = N_A;
        private String subChapter = N_A;
        private String section = N_A;
        private String subSection = N_A;
        private String subSubSection = N_A;
        private String notes = N_A;

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
