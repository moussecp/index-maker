package com.bxy.indexmaker.domain;

import org.springframework.stereotype.Service;

@Service
public class RowContentFactory {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String firstCell;
        private String secondCell;
        private String thirdCell;
        private String fourthCell;

        private Builder() {
        }

        public Builder setFirstCell(String firstCell) {
            this.firstCell = firstCell;
            return this;
        }
        public Builder setSecondCell(String secondCell) {
            this.secondCell = secondCell;
            return this;
        }
        public Builder setThirdCell(String thirdCell) {
            this.thirdCell = thirdCell;
            return this;
        }
        public Builder setFourthCell(String fourthCell) {
            this.fourthCell = fourthCell;
            return this;
        }

        public RowContent build() {
            return new RowContent(firstCell, secondCell, thirdCell, fourthCell);
        }
    }
}
