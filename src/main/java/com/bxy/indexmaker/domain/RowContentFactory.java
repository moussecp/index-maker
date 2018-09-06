package com.bxy.indexmaker.domain;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.bxy.indexmaker.domain.ListType.*;

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
        private ListType listType = ListType.NONE;
        private List<String> boldText = new ArrayList<>();

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
            return new RowContent(content, chapter, subChapter, section, subSection, subSubSection, notes, listType, boldText);
        }

        public Builder setList(String list) {
            switch (list.toUpperCase().trim()) {
                case "LIST":
                    this.listType = LIST;
                    break;
                case "ENUMERATE":
                    this.listType = ENUMERATE;
                    break;
                default:
                    this.listType = NONE;
                    break;
            }
            return this;
        }

        public Builder setBold(String bold) {
            List<String> collect = Arrays.asList(bold.split(";")).stream().map(s -> s.trim()).collect(Collectors.toList());
            this.boldText = collect != null ? collect : new ArrayList<>();
            return this;
        }
    }
}
