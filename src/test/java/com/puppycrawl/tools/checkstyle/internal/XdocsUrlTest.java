////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class XdocsUrlTest {

    public static final Path PACKAGE_NAME_PATH =
            Paths.get("src/main/java/com/puppycrawl/tools/checkstyle/checks");

    public static final String NODE_NAME = "a";

    public static final String SUFFIX_JAVA = ".java";

    public static final String SUFFIX_CHECK_JAVA = "Check.java";

    public static final String MISC = "misc";

    public static final String ANNOTATION = "annotation";

    public static final String COMMENTS_INDENTATION = "CommentsIndentation";

    public static final String INDENTATION = "Indentation";

    public static final String SUPPRESS_WARNINGS_HOLDER = "SuppressWarningsHolder";

    public static final Path AVAILABLE_CHECKS_PATH = Paths.get("src/xdocs/checks.xml");

    private static Map<String, String[]> getXdocsMap() {
        final File checks = new File(PACKAGE_NAME_PATH.toString());
        final String[] checksSubDirNames = checks.list((rootDir, pathname) -> {
            return !pathname.endsWith(SUFFIX_JAVA);
        });
        final Map<String, String[]> checksNamesMap = new HashMap<>();
        if (checksSubDirNames != null) {
            final FilenameFilter filter = (rootDir, pathname) -> {
                return pathname.endsWith(SUFFIX_CHECK_JAVA);
            };
            for (String dirName : checksSubDirNames) {
                final File dirFile = new File(PACKAGE_NAME_PATH.toString() + "/" + dirName);
                final String[] fileNames = dirFile.list(filter);
                checksNamesMap.put(dirName, fileNames);
            }
            final String[] miscNames = checks.list(filter);
            checksNamesMap.put(MISC, miscNames);
        }
        return checksNamesMap;
    }

    @Test
    public void testXdocsUrl() throws Exception {
        final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        final SAXParser parser = parserFactory.newSAXParser();
        final CheckTest checkHandler = new CheckTest(NODE_NAME);
        final List<List<String>> list;
        try (InputStream input = Files.newInputStream(AVAILABLE_CHECKS_PATH)) {
            parser.parse(input, checkHandler);
            list = checkHandler.list;
        }
        final Map<String, String[]> checksNamesMap = getXdocsMap();
        for (List<String> sub : list) {
            final String moduleName = sub.get(0);
            final String checkName1 = sub.get(1);
            final String checkName2 = sub.get(2);
            final String errorMessage =
                    "URL in" + AVAILABLE_CHECKS_PATH.toString() + " is inconsistent: "
                            + moduleName + " " + checkName1 + " " + checkName2;
            assertEquals(checkName1, checkName2, errorMessage);
            if (checkName1.equals(COMMENTS_INDENTATION) || checkName1.equals(INDENTATION)) {
                assertEquals(MISC, moduleName, errorMessage);
            }
            else if (checkName1.equals(SUPPRESS_WARNINGS_HOLDER)) {
                assertEquals(ANNOTATION, moduleName, errorMessage);
            }
            else {
                final String[] moduleFileNames = checksNamesMap.get(moduleName);
                if (moduleFileNames != null) {
                    boolean match = false;
                    for (String filename : moduleFileNames) {
                        if ((checkName1 + SUFFIX_CHECK_JAVA).equals(filename)) {
                            match = true;
                            break;
                        }
                    }
                    assertTrue(match, errorMessage);
                }
            }
        }
    }

    public static final class CheckTest extends DefaultHandler {

        public static final String SPLIT_ONE = "#";

        public static final String SPLIT_TWO = "\\.";

        public static final String PREFIX_CONFIG = "config_";

        private final String nodeName;

        private List<List<String>> list;

        private List<String> stringList;

        private String currentTag;

        public CheckTest(String nodeName) {
            this.nodeName = nodeName;
        }

        public List<List<String>> getList() {
            return list;
        }

        @Override
        public void startDocument() {
            list = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) {
            if (qName.equals(nodeName)) {
                final String[] splitStrings = attributes.getValue(0).split(SPLIT_ONE);
                if (splitStrings[0].substring(0, PREFIX_CONFIG.length()).equals(PREFIX_CONFIG)) {
                    stringList = new ArrayList<>();
                    final String moduleName =
                            splitStrings[0].substring(PREFIX_CONFIG.length()).split(SPLIT_TWO)[0];
                    stringList.add(moduleName);
                    stringList.add(splitStrings[1]);
                }
            }
            currentTag = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (currentTag != null && stringList != null && currentTag.equals(nodeName)) {
                final String currentValue = new String(ch, start, length).trim();
                if (!currentValue.isEmpty() && !"\n".equals(currentValue)) {
                    stringList.add(currentValue);
                }
                currentTag = null;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (stringList != null && qName.equals(nodeName)) {
                list.add(stringList);
                stringList = null;
            }
        }
    }
}
