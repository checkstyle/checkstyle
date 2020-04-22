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
import java.io.InputStream;
import java.nio.file.Files;
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

    public static final String PACKAGE_NAME =
            "src/main/java/com/puppycrawl/tools/checkstyle/checks";

    public static final String NODE_NAME = "a";

    public static final String SUFFIX_JAVA = ".java";

    public static final String SUFFIX_CHECK_JAVA = "Check.java";

    public static final String MISC = "misc";

    public static final String ANNOTATION = "annotation";

    public static final String COMMENTS_INDENTATION = "CommentsIndentation";

    public static final String INDENTATION = "Indentation";

    public static final String SUPPRESS_WARNINGS_HOLDER = "SuppressWarningsHolder";

    private static String getRootPath() {
        return System.getProperty("user.dir");
    }

    private static String getXmlPath() {
        return "src/xdocs/checks.xml";
    }

    private static Map<String, String[]> getXdocsMap() {
        final String rootPath = getRootPath();
        final File checks = new File(rootPath + "/" + PACKAGE_NAME);
        final String[] checksSubDirNames = checks.list((rootDir, pathname) -> {
            return !pathname.endsWith(SUFFIX_JAVA);
        });
        final Map<String, String[]> checksNamesMap = new HashMap<>();
        if (checksSubDirNames != null) {
            for (String dirName : checksSubDirNames) {
                final File dirFile = new File(rootPath + "/" + PACKAGE_NAME + "/" + dirName);
                final String[] fileNames = dirFile.list((rootDir, pathname) -> {
                    return pathname.endsWith(SUFFIX_CHECK_JAVA);
                });
                checksNamesMap.put(dirName, fileNames);
            }
            final String[] miscNames = checks.list((rootDir, pathname) -> {
                return pathname.endsWith(SUFFIX_CHECK_JAVA);
            });
            checksNamesMap.put(MISC, miscNames);
        }
        return checksNamesMap;
    }

    @Test
    public void testXdocsUrl() throws Exception {
        final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        final SAXParser parser = parserFactory.newSAXParser();
        final CheckTest checkHandler = new CheckTest(NODE_NAME);
        final InputStream input = Files.newInputStream(
                Paths.get(getRootPath() + "/" + getXmlPath()));
        parser.parse(input, checkHandler);
        final ArrayList<String[]> list = (ArrayList<String[]>) checkHandler.getList();
        input.close();
        final Map<String, String[]> checksNamesMap = getXdocsMap();
        for (String[] strings : list) {
            assertEquals(strings[1], strings[2],
                    "URL in" + getXmlPath() + " is inconsistent");
            if (strings[1].equals(COMMENTS_INDENTATION) || strings[1].equals(INDENTATION)) {
                assertEquals(MISC,strings[0],
                        "URL in" + getXmlPath() + " is inconsistent");
            }
            else if (strings[1].equals(SUPPRESS_WARNINGS_HOLDER)) {
                assertEquals(ANNOTATION, strings[0],
                        "URL in" + getXmlPath() + " is inconsistent");
            }
            else {
                final String[] moduleFileNames = checksNamesMap.get(strings[0]);
                if (moduleFileNames != null) {
                    boolean match = false;
                    for (String filename : moduleFileNames) {
                        if ((strings[1] + SUFFIX_CHECK_JAVA).equals(filename)) {
                            match = true;
                            break;
                        }
                    }
                    assertTrue(match, "URL in" + getXmlPath() + " is inconsistent: "
                            + strings[0] + " " + strings[1] + " " + strings[2]);
                }
            }
        }
    }

    public static final class CheckTest extends DefaultHandler {

        public static final String SPLIT_ONE = "#";

        public static final String SPLIT_TWO = "\\.";

        public static final String PREFIX_CONFIG = "config_";

        private final String nodeName;

        private String[] strings;

        private List<String[]> list;

        private String currentTag;

        public CheckTest(String nodeName) {
            this.nodeName = nodeName;
        }

        public List<String[]> getList() {
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
                strings = new String[3];
                final String[] splitStrings = attributes.getValue(0).split(SPLIT_ONE);
                if (splitStrings[0].substring(0, 7).equals(PREFIX_CONFIG)) {
                    final String moduleName = splitStrings[0].substring(7).split(SPLIT_TWO)[0];
                    strings[0] = moduleName;
                    strings[1] = splitStrings[1];
                }
            }
            currentTag = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (currentTag != null && strings != null
                    && strings[0] != null && currentTag.equals(nodeName)) {
                final String currentValue = new String(ch, start, length);
                if (!currentValue.trim().isEmpty() && !"\n".equals(currentValue.trim())) {
                    strings[2] = currentValue.trim();
                }
                currentTag = null;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (strings != null && strings[0] != null && qName.equals(nodeName)) {
                list.add(strings);
                strings = null;
            }
        }
    }
}
