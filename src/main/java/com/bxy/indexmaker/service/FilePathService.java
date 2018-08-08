package com.bxy.indexmaker.service;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.stereotype.Service;

@Service
public final class FilePathService {
    public static final boolean IS_OS_WINDOWS = SystemUtils.IS_OS_WINDOWS;
    public static final boolean IS_OS_LINUX = SystemUtils.IS_OS_LINUX;
    public static final String HOME_DIR_WINDOWS = "D:\\\\Workspace\\\\index-maker";
    public static final String HOME_DIR_LINUX = "/home/tms/workspace/indexmaker/";
    public static final String MAIN_RESOURCES_WINDOWS = "\\\\src\\\\main\\\\resources\\\\";
    public static final String MAIN_RESOURCES_LINUX = "src/main/resources/";
    public static final String TEST_RESOURCES_WINDOWS = "\\\\src\\\\test\\\\resources\\\\";
    public static final String TEST_RESOURCES_LINUX = "src/test/resources/";
    public static final String IMPORTED_XLS_FILE_PATH_WINDOWS = HOME_DIR_WINDOWS + MAIN_RESOURCES_WINDOWS;
    public static final String IMPORTED_XLS_FILE_PATH_LINUX = HOME_DIR_LINUX + MAIN_RESOURCES_LINUX;
    public static final String IMPORTED_XLS_TEST_FILE_PATH_WINDOWS = HOME_DIR_WINDOWS + TEST_RESOURCES_WINDOWS;
    public static final String IMPORTED_XLS_TEST_FILE_PATH_LINUX = HOME_DIR_LINUX + TEST_RESOURCES_LINUX;
    public static final String HTML_HOME_PATH_WINDOWS = HOME_DIR_WINDOWS + MAIN_RESOURCES_WINDOWS + "html\\\\";
    public static final String HTML_HOME_PATH_LINUX = HOME_DIR_LINUX + MAIN_RESOURCES_LINUX + "html/";
    public static final String EXPORTED_HTML_FILE_PATH_WINDOWS = HTML_HOME_PATH_WINDOWS + "generated\\\\";
    public static final String EXPORTED_HTML_FILE_PATH_LINUX = HTML_HOME_PATH_LINUX + "generated/";
    public static final String IMPORTED_IMAGES_FOR_HTML_WINDOWS = HTML_HOME_PATH_WINDOWS + "images\\\\";
    public static final String IMPORTED_IMAGES_FOR_HTML_LINUX = HTML_HOME_PATH_LINUX + "images/";
    public static final String EXPORTED_HTML_TEST_FILE_PATH_WINDOWS = HOME_DIR_WINDOWS + TEST_RESOURCES_WINDOWS + "html\\\\";
    public static final String EXPORTED_HTML_TEST_FILE_PATH_LINUX = HOME_DIR_LINUX + TEST_RESOURCES_LINUX + "html/";

    public static String getImportedXlsFilePath() {
        if (IS_OS_LINUX) {
            return IMPORTED_XLS_FILE_PATH_LINUX;
        } else if (IS_OS_WINDOWS) {
            return IMPORTED_XLS_FILE_PATH_WINDOWS;
        } else {
            throw new OSNotRecognisedException();
        }
    }

    public static String getImportedXlsTestFilePath() {
        if (IS_OS_LINUX) {
            return IMPORTED_XLS_TEST_FILE_PATH_LINUX;
        } else if (IS_OS_WINDOWS) {
            return IMPORTED_XLS_TEST_FILE_PATH_WINDOWS;
        } else {
            throw new OSNotRecognisedException();
        }
    }

    public static String getHtmlHomePath() {
        if (IS_OS_LINUX) {
            return HTML_HOME_PATH_LINUX;
        } else if (IS_OS_WINDOWS) {
            return HTML_HOME_PATH_WINDOWS;
        } else {
            throw new OSNotRecognisedException();
        }
    }

    public static String getExportedHtmlFilePath() {
        if (IS_OS_LINUX) {
            return EXPORTED_HTML_FILE_PATH_LINUX;
        } else if (IS_OS_WINDOWS) {
            return EXPORTED_HTML_FILE_PATH_WINDOWS;
        } else {
            throw new OSNotRecognisedException();
        }
    }

    public static String getExportedHtmlTestFilePath() {
        if (IS_OS_LINUX) {
            return EXPORTED_HTML_TEST_FILE_PATH_LINUX;
        } else if (IS_OS_WINDOWS) {
            return EXPORTED_HTML_TEST_FILE_PATH_WINDOWS;
        } else {
            throw new OSNotRecognisedException();
        }
    }

    public static String getImagesPathForHtml() {
        if (IS_OS_LINUX) {
            return IMPORTED_IMAGES_FOR_HTML_LINUX;
        } else if (IS_OS_WINDOWS) {
            return IMPORTED_IMAGES_FOR_HTML_WINDOWS;
        } else {
            throw new OSNotRecognisedException();
        }
    }
}
