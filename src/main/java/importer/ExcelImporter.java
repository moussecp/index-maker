package importer;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import persistence.RowContent;
import persistence.RowContentDao;
import persistence.RowContentDaoImpl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.HashMap;
import java.io.FileOutputStream;

public class ExcelImporter {

    private static final String XLS_FILE_ADDRESS = "/home/tms/workspace/indexmaker/src/main/resources/test.xls";

    public ExcelImporter() {
    }


    private static RowContentDao rowContentDao = new RowContentDaoImpl();

    public static void main(String[] args) throws Exception {

        Workbook workbook = WorkbookFactory.create(new FileInputStream(XLS_FILE_ADDRESS));
        workbook.getSheetAt(1);
        Sheet sheet = workbook.getSheetAt(0);
        for(int i = 0; i<= sheet.getLastRowNum(); i++) {
            rowContentDao.addRowContent(new RowContent(sheet.getRow(i).getCell(0).toString()));
        }

        System.out.println("yo");
    }


}
