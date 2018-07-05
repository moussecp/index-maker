package com.bxy.indexmaker.domain;

import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlackListedWords {

    private static List<String> list = new ArrayList<>();


    public static List<String> getList() {
        generateBlackListedWords();
        return list;
    }

    private static void generateBlackListedWords() {
        list = Arrays.asList(new String[]{
                Strings.EMPTY,
                " ",
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
                "toute"
        });

    }
}
