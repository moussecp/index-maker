package com.bxy.indexmaker.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlackListedWords {

    private static List<String> list = new ArrayList<>();


    public static List<String> getList() {
        generateBlackListedWords();
        return list;
    }

    public static List<String> getUpperCasedList() {
        generateBlackListedWords();
        list.replaceAll(String::toUpperCase);
        return filterSpecialCharacters(list);
    }

    private static List<String> filterSpecialCharacters(List<String> list) {
        List<String> newList = new ArrayList<>(list);
        for(String string : list) {
            newList.add(string.trim().replaceAll("[-+.^:,]",""));
        }
        return newList;
    }

    private static void generateBlackListedWords() {
        list = Arrays.asList(new String[]{
                StringUtils.EMPTY,
                " ",
                "-",
                "de",
                "et",
                "les",
                "des",
                "la",
                "à",
                "en",
                "le",
                "dans",
                "du",
                " \t○   ",
                "pour",
                "une",
                "un",
                "au",
                "par",
                ":",
                "sur",
                "ou",
                "avec",
                "plus",
                "qui",
                "leur",
                "est",
                " \t■    ",
                "ce",
                "se",
                ";",
                "d'un",
                "afin",
                "doit",
                "tout",
                "être",
                "d'une",
                "leurs",
                "sont",
                "pas",
                "comme",
                "aussi",
                "sein",
                "son",
                "tous",
                "exemple",
                "ne",
                "il",
                "elle",
                "a",
                "via",
                "vers",
                "contre",
                "mise",
                "toutes",
                "notre",
                "y",
                "ses",
                "mais",
                "lieux",
                "ainsi",
                "faire",
                "ces",
                "sa",
                "non",
                "(par",
                "même",
                "aux",
                "que",
                "entre",
                "toute",
                "d''un",
                "d''une",
                "nous",
                "vous",
                "je",
                "il",
                "elle",
                "elles",
                "ils",
                "tu",
                "on",
                "cette",
                "cet",
                "ceux",
                "celles",
                "chaque",
                "autre",
                "autres",
                "sans",
                "si",
                "quelque",
                "quelques",
                "où",
                "rendre",
                "pouvoir",
                "bien",
                "tant",
                "mieux",
                ".",
                "(cf.",
                "dont",
                "sous",
                "permettre",
                "c''est",
                "prendre",
                "propos",
                "soit",
                "d''autres",
                "nos",
                "maximum",
                "compte",
                "soient",
                "lors",
                "auprès",
                "cas",
                "deux",
                "garantir"
        });

    }
}
