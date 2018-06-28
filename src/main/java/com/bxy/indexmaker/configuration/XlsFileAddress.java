package com.bxy.indexmaker.configuration;

import org.springframework.beans.factory.annotation.Value;

public class XlsFileAddress {
    private String xlsFilePath;
    private String xlsFileName;

    private XlsFileAddress(String xlsFilePath, String xlsFileName) {
        this.xlsFileName = xlsFileName;
        this.xlsFilePath = xlsFilePath;
    }

    public static Builder builder() {
        return new Builder();
    };

    public String getXlsFilePath() {
        return xlsFilePath;
    }

    public String getXlsFileName() {
        return xlsFileName;
    }

    public String get() {
        return xlsFilePath.concat(xlsFileName);
    }

    static class Builder {
        private String xlsFilePath;
        private String xlsFileName;

        public Builder setXlsFilePath(String xlsFilePath) {
            this.xlsFilePath = xlsFilePath;
            return this;
        }
        public Builder setXlsFileName(String xlsFileName) {
            this.xlsFileName = xlsFileName;
            return this;
        }

        public XlsFileAddress build() {
            return new XlsFileAddress(xlsFilePath, xlsFileName);
        }
    }
}
