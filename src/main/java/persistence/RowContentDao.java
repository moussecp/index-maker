package persistence;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RowContentDao {
    void addRowContent(RowContent rowContent);
    List<RowContent> findAllRowContents();
    RowContent findRowContent(Long id);
}
