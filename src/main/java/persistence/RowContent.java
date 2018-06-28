package persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class RowContent {
    private String firstCell;
    @Id
    @GeneratedValue
    private Long id;
//    private static Long idCounter = 0l;

    public RowContent(String firstCell) {
//        this.id = ++idCounter;
        this.firstCell = firstCell;
    }

    public Long getId() {
        return id;
    }

    public String getFirstCell() {
        return firstCell;
    }

    public void setFirstCell(String firstCell) {
        this.firstCell = firstCell;
    }

    @Override
    public String toString() {
        return "RowContent{" +
                "firstCell='" + firstCell + '\'' +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowContent that = (RowContent) o;
        return Objects.equals(getFirstCell(), that.getFirstCell()) &&
                Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getFirstCell(), getId());
    }
}
