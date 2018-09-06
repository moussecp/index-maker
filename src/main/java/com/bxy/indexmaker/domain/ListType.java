package com.bxy.indexmaker.domain;

public enum ListType {
    NONE,
    LIST,
    ENUMERATE;

    ListType() {
    }

    public boolean isList() {
        return !this.equals(NONE);
    }

    public boolean isClassicList() {
        return this.equals(LIST);
    }

    public boolean isEnumeratedList() {
        return this.equals(ENUMERATE);
    }
}
