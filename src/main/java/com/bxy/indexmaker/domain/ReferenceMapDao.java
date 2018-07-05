package com.bxy.indexmaker.domain;

import com.bxy.indexmaker.configuration.persistence.AbstractMapDao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReferenceMapDao extends AbstractMapDao<Reference> implements ReferenceRepository {

    public static final int REFERENCE_COUNT_THRESHOLD = 5;
    private static List<Reference> references = new ArrayList<>();
    private static Long index = 1L;

    @Override
    public void createOrUpdateReference(String word, RowContent rowContent) {
        try {
            updateReference(word, rowContent);
        } catch (NoSuchElementException e) {
            createReference(Reference.builder()
                    .setWord(word)
                    .setRowContent(rowContent)
                    .build());
        }
    }

    private void createReference(Reference reference) {
        reference.setId(index++);
        System.out.println("added: " + reference);
        references.add(reference);
    }

    private void updateReference(String word, RowContent rowContent) {
        Reference reference = findReferenceWithWord(word);
        if (reference != null) {
            reference.addRowContent(rowContent);
            System.out.println("updated: " + reference);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public List<Reference> findTopReferencesMinusBlackListed() {
        System.out.println("results: " + references);
        System.out.println(BlackListedWords.getList());
        return references.stream()
                .filter(reference -> !BlackListedWords.getList().contains(reference.getWord()))
                .filter(reference -> reference.getCount() > REFERENCE_COUNT_THRESHOLD)
                .collect(Collectors.toList());
    }

    @Override
    public Reference findReference(Long id) {
        Reference reference = references
                .stream()
                .filter(ref -> ref.getId().equals(id))
                .findAny()
                .get();
        System.out.println("found for id: " + id + "results: " + reference);
        return reference;
    }

    @Override
    public Reference findReferenceWithWord(String word) {
        Optional<Reference> optionalReference = references
                .stream()
                .filter(ref -> ref.getWord().toUpperCase().equals(word.toUpperCase()))
                .findAny();
        if (optionalReference.isPresent()) {
            Reference reference = optionalReference.get();
            System.out.println("found for word: " + word + "results: " + reference);
            return reference;
        }
        return null;
    }
}
