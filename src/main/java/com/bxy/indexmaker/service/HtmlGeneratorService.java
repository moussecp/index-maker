package com.bxy.indexmaker.service;

import com.bxy.indexmaker.domain.*;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class HtmlGeneratorService {

    public static final String CHAPITRE_1 = "Les bases de notre engagement";
    public static final String STYLESHEET = "stylesheet";
    public static final String BOOTSTRAP = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css";
    public static final String PATH = "D:\\\\Workspace\\\\index-maker\\\\src\\\\main\\\\resources\\\\html\\\\";
    //TODO use real hibernate Repository
    private RowContentRepository rowContentRepository = new RowContentMapDao();
    //TODO use real hibernate Repository
    private ReferenceRepository referenceRepository = new ReferenceMapDao();


    public void generateIntro() throws IOException {
        List<RowContent> rowContents = getRowContents(CHAPITRE_1);
        List<Reference> references = getReferences(CHAPITRE_1);

        File htmlTemplateFile = new File(PATH + "template.html");
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        String title = "New Page";
        String body = "This is Body";
        htmlString = htmlString.replace("$title", title);
        htmlString = htmlString.replace("$body", body);
        File newHtmlFile = new File("chapitre1.html");
        FileUtils.writeStringToFile(newHtmlFile, htmlString);

//        String output =
//                html(
//                        head(
//                                title(CHAPITRE_1),
//                                link().withRel(STYLESHEET).withHref(BOOTSTRAP)
//                        ),
//                        body(
//                        )
//                ).render();

    }

    private List<RowContent> getRowContents(String chapter) {
        return rowContentRepository.findAllRowContents()
                .stream()
                .filter(rowContent -> rowContent.getChapter().equals(chapter))
                .collect(Collectors.toList());
    }

    private List<Reference> getReferences(String chapter) {
        return referenceRepository.findAll()
                .stream()
                .filter(reference -> reference.getSubChaptersAsString().contains(chapter))
                .collect(Collectors.toList());
    }
}
