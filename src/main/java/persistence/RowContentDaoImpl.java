package persistence;

import java.util.ArrayList;
import java.util.List;

public class RowContentDaoImpl implements RowContentDao {

    private static List<RowContent> rowContents = new ArrayList<>();

    @Override
    public void addRowContent(RowContent rowContent) {
        rowContents.add(rowContent);
        System.out.println("rowContent added: " + rowContent.toString());
    }

    @Override
    public List<RowContent> findAllRowContents() {
        return rowContents;
    }

    @Override
    public RowContent findRowContent(Long id) {
        return rowContents
                .stream()
                .filter(rowContent -> rowContent.getId() == id)
                .findFirst()
                .get();
    }
}
