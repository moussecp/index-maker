package com.bxy.indexmaker.domain;

import com.bxy.indexmaker.service.RowContentService;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RowContentTest {

    private RowContentRepository rowContentRepository = new RowContentMapDao();
    private ReferenceRepository referenceRepository = new ReferenceMapDao();
    private RowContentService rowContentService = new RowContentService(rowContentRepository, referenceRepository);

    @Test
    public void startsWithListElementTest() {
        RowContentTestBuilder.setRowContentRepository(rowContentRepository);
        Set<RowContent> rowContents = RowContentTestBuilder.rowContentsStartingWithListElement();
        for(RowContent rowContent : rowContents) {
            if(rowContent.getId().equals(1L)) {
                continue;
            }
            assertTrue(rowContentService.startsWithListElement(rowContent.getContent()));
        }
    }

    @Test
    public void mergeContentWithListElementTest() {
        RowContentTestBuilder.setRowContentRepository(rowContentRepository);
        RowContentTestBuilder.addListedRowContentsToRepository();
        List<RowContent> rowContents = rowContentRepository.findAllRowContents();
        RowContent firstRowContent = rowContents.iterator().next();
        for(RowContent rowContent : rowContents) {
            if(rowContent.getId().equals(1L)) {
                continue;
            }
            String mergedContent = rowContentService.mergeContentWithListElement(firstRowContent.getContent(), rowContent.getContent());
            firstRowContent.updateContent(mergedContent);
            rowContentRepository.updateRowContent(firstRowContent);
        }
        assertEquals(rowContentRepository.findRowContent(1L).getContent(), "TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT<br/> - TEXT");
    }
}