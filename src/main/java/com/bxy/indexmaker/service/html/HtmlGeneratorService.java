package com.bxy.indexmaker.service.html;

import com.bxy.indexmaker.domain.*;
import com.bxy.indexmaker.service.RowContentService;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import static com.bxy.indexmaker.service.FilePathService.getExportedHtmlFilePath;
import static com.bxy.indexmaker.service.html.HtmlContentFormatService.getFormattedContent;
import static com.bxy.indexmaker.service.html.HtmlHeaderMenuService.getHeaderMenusAsHtml;
import static com.bxy.indexmaker.service.html.HtmlHeaderStructureService.buildHeadersStructure;
import static com.bxy.indexmaker.service.html.HtmlTagsUtils.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;

//TODO COPY from https://v4-alpha.getbootstrap.com/examples/blog/

@Service
public class HtmlGeneratorService {
    private File htmlTemplateFile = new File(TEMPLATE_PATH);

    //TODO use real hibernate Repository
    private RowContentRepository rowContentRepository = new RowContentMapDao();
    //TODO use real hibernate Repository
    private ReferenceRepository referenceRepository = new ReferenceMapDao();
    private RowContentService rowContentService = new RowContentService();

    protected void generateHtmlFile(String body, String title, String style, String bodyIndex, String outputFilePath, String bodyHeaderLinks) throws IOException {
        String htmlString = generateHtmlString(body, title, style, bodyIndex, bodyHeaderLinks);
        File newHtmlFile = new File(outputFilePath);
        Files.deleteIfExists(newHtmlFile.toPath());
        FileUtils.write(newHtmlFile, htmlString, StandardCharsets.UTF_8);
    }

    protected String generateHtmlString(String body, String title, String style, String bodyIndex, String bodyHeaderLinks) throws IOException {
        String htmlString = FileUtils.readFileToString(htmlTemplateFile, StandardCharsets.UTF_8);
        htmlString = htmlString.replace(HEADER_TITLE, title);
        htmlString = htmlString.replace(HEADER_STYLE, style);
        htmlString = htmlString.replace(BODY_HEADER_LINKS, bodyHeaderLinks);
        htmlString = htmlString.replace(BODY_INDEX, bodyIndex);
        htmlString = htmlString.replace(BODY_CONTENT, body);
        return htmlString;
    }

    protected String buildBodyContentWihStructure(List<RowContent> rowContents, Map<Long, String> headersStructure) {
        Long index = 0L;
        StringBuilder sb = new StringBuilder();
        int headersStructureMaxIndex = headersStructure.keySet().stream().max(Long::compareTo).get().intValue();
        for (RowContent rowContent : rowContents) {
            String headers = headersStructure.get(index);
            sb.append(headers != null ? headers : EMPTY)
                    .append(newLine())
                    .append(getFormattedContent(rowContent))
                    .append(newLine());
            index++;
        }
        while (index <= headersStructureMaxIndex) {
            String str = headersStructure.get(index);
            sb.append(str != null ? str : EMPTY);
            index++;
        }

        return sb.toString();
    }

    public void generateAllChapters() throws IOException {
        List<RowContent> rowContents = rowContentRepository.findAllRowContents();
        rowContents.remove(0);
        List<Reference> references = referenceRepository.findAll();
        String style = HtmlTagsUtils.getChapterStyle();
        String bodyIndex = "";// getFirstTwentyReferencesFormatted(references);
        String body = buildBodyContentWihStructure(rowContents, buildHeadersStructure(rowContents));
        String title = "Tout le programme";
        String outputFilePath = getExportedHtmlFilePath() + "TOUT.html";
        generateHtmlFile(body, title, "", bodyIndex, outputFilePath, getHeaderMenusAsHtml(rowContents));
        System.out.println("html generated: " + "TOUT.html");
    }

    public void generateChapterNo(String chapterName, String fileName) throws IOException {
        List<RowContent> rowContents = getRowContentsFromChapter(chapterName);
        if (rowContents.size() == 0) {
            rowContents = getRowContentsFromSubChapter(chapterName);
        }
        List<Reference> references = getReferencesFromChapterOrSubChapter(chapterName);
        String bodyIndex = getFirstTwentyReferencesFormatted(references);
        String body = buildBodyContentWihStructure(rowContents, buildHeadersStructure(rowContents));
        String title = chapterName;
        String outputFilePath = getExportedHtmlFilePath() + fileName;
        generateHtmlFile(body, title, "", bodyIndex, outputFilePath, getHeaderMenusAsHtml(rowContents));
        System.out.println("html generated: " + fileName);
    }

    public void generateChapter1() throws IOException {
        generateChapterNo(CHAPITRE_1, CHAPITRE1_HTML);
    }

    public void generateChapter2() throws IOException {
        generateChapterNo(CHAPITRE_2, CHAPITRE2_HTML);
    }

    public void generateChapter3() throws IOException {
        generateChapterNo(CHAPITRE_3, CHAPITRE3_HTML);
    }

    public void generateChapter4() throws IOException {
        generateChapterNo(CHAPITRE_4, CHAPITRE4_HTML);
    }

    private String getIndex(List<Reference> references) {
        return new StringBuilder()
                .append(openingParagraphClassLeadBlogDescription())
                .append(getFirstTwentyReferencesFormatted(references))
                .append(closingParagraph())
                .toString();
    }

    protected String getFirstTwentyReferencesFormatted(List<Reference> references) {
        int numberOfReferences = 20;
        double[] fontSizes = {3, 4, 5, 6, 7};
        double steps = fontSizes.length;
        double max = getBiggestNumberOfCounts(references);
        double min = getSmallestNumberOfCountsOutOfTop(references, numberOfReferences);
        List<Long> topReferencesIds = getTopReferences(references, numberOfReferences);
        Collections.shuffle(topReferencesIds);
        double step = (max - min) / steps;
        StringBuilder sb = new StringBuilder();
        sb.append(openingDivClassContainer())
                .append(openingDivClassRowAlignCenter());
        for (Long id : topReferencesIds) {
            Reference reference = referenceRepository.findReference(id);
            String url = "something/stupid/" + reference.getWord();
            double fontSize = fontSizes[0];
            for (int i = 0; i < steps; i++) {
                if ((i * step + min <= reference.getCount()) && (reference.getCount() <= (i + 1) * step + min)) {
                    fontSize = fontSizes[i];
                }
            }
            sb.append("<a href=\"" + url + " \">")
                    .append("<font size=\"" + fontSize + "\">")
                    .append(reference.getWord().substring(0, 1).toUpperCase() + reference.getWord().substring(1).toLowerCase())
                    .append("</font> ")
                    .append("</a>");
        }
        return sb.append(closingDiv())
                .append(closingDiv())
                .toString();
    }

    private int getBiggestNumberOfCounts(List<Reference> references) {
        Optional<Reference> first = references
                .stream()
                .sorted((current, other) -> other.getCount().compareTo(current.getCount()))
                .findFirst();
        return first.isPresent() ? first.get().getCount() : 0;
    }

    private int getSmallestNumberOfCountsOutOfTop(List<Reference> references, int top) {
        Optional<Reference> first = references
                .stream()
                .sorted((current, other) -> other.getCount().compareTo(current.getCount()))
                .limit(top)
                .sorted(Comparator.comparing(Reference::getCount))
                .findFirst();
        return first.isPresent() ? first.get().getCount() : 0;
    }

    protected List<Long> getTopReferences(List<Reference> references, long limitNumber) {
        return references
                .stream()
                .sorted((current, other) -> other.getCount().compareTo(current.getCount()))
                .limit(limitNumber)
                .map(Reference::getId)
                .collect(Collectors.toList());
    }

    protected List<RowContent> getRowContentsFromChapter(String chapter) {
        return rowContentRepository.findAllRowContents()
                .stream()
                .filter(rowContent -> rowContent.getChapter().contains(chapter))
                .sorted(Comparator.comparing(RowContent::getId))
                .collect(Collectors.toList());
    }

    protected List<Reference> getReferencesFromChapterOrSubChapter(String chapter) {
        return referenceRepository.findAll()
                .stream()
                .filter(reference -> reference.getSubChaptersAsString().contains(chapter))
                .collect(Collectors.toList());
    }

    protected List<RowContent> getRowContentsFromSubChapter(String subChapter) {
        return rowContentRepository.findAllRowContents()
                .stream()
                .filter(rowContent -> rowContent.getSubChapter().contains(subChapter))
                .sorted(Comparator.comparing(RowContent::getId))
                .collect(Collectors.toList());
    }

    protected void setReferenceRepository(ReferenceRepository referenceRepository) {
        this.referenceRepository = referenceRepository;
    }
}
