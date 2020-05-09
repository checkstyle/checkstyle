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

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class XdocsUrlTest {

    public static final String PACKAGE_NAME =
            "src/main/java/com/puppycrawl/tools/checkstyle/checks";

    public static final String SUFFIX_CHECK = "Check";

    public static final String CHECKS = "checks";

    public static final String CHECKSTYLE = "checkstyle";

    public static final String FILTERS = "filters";

    public static final String FILE_FILTERS = "filefilters";

    public static final String MISC = "misc";

    public static final String ANNOTATION = "annotation";

    public static final String COMMENTS_INDENTATION = "CommentsIndentation";

    public static final String INDENTATION = "Indentation";

    public static final String SUPPRESS_WARNINGS_HOLDER = "SuppressWarningsHolder";

    public static final Path AVAILABLE_CHECKS_PATH = Paths.get("src/xdocs/checks.xml");

    private static Map<String, ArrayList<String>> getXdocsMap() throws IOException {
        final Map<String, ArrayList<String>> checksNamesMap = new HashMap<>();
        final Set<Class<?>> checkSet = CheckUtil.getCheckstyleModules();
        final Iterator<Class<?>> iterator = checkSet.iterator();
        while (iterator.hasNext()) {
            final String[] splitArray = iterator.next().getName().split("\\.");
            String subPackage = splitArray[splitArray.length - 2];
            if (!subPackage.equals(CHECKSTYLE)
                    && !subPackage.equals(FILTERS)
                    && !subPackage.equals(FILE_FILTERS)) {
                if (subPackage.equals(CHECKS)) {
                    subPackage = MISC;
                }
                final String checkName = splitArray[splitArray.length - 1];
                if (checksNamesMap.get(subPackage) == null) {
                    final ArrayList<String> arrayList = new ArrayList<>();
                    arrayList.add(checkName);
                    checksNamesMap.put(subPackage, arrayList);
                }
                else {
                    checksNamesMap.get(subPackage).add(checkName);
                }
            }
        }
        return checksNamesMap;
    }

    @Test
    public void testXdocsUrl() throws Exception {
        final SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        final SAXParser parser = parserFactory.newSAXParser();
        final CheckTest checkHandler = new CheckTest();
        try (InputStream input = Files.newInputStream(AVAILABLE_CHECKS_PATH)) {
            parser.parse(input, checkHandler);
        }
        final Map<String, ArrayList<String>> checksNamesMap = getXdocsMap();
        for (List<String> sub : checkHandler.checkNamesList) {
            final String moduleName = sub.get(0);
            final String checkNameInAttribute = sub.get(1);
            final String checkNameInText = sub.get(2);
            final String checkNameInconsistentErrorMsg = String.format(Locale.ROOT,
                    "Check with name '%s' in attribute "
                            + "is not consistent with check name in text in file '%s'",
                    checkNameInAttribute, AVAILABLE_CHECKS_PATH);
            assertWithMessage(checkNameInconsistentErrorMsg)
                    .that(checkNameInText).matches(checkNameInAttribute);
            final String checkNameModuleErrorMsg = String.format(Locale.ROOT,
                    "Check with name '%s' is not in '%s' module",
                    checkNameInAttribute, moduleName);
            if (checkNameInAttribute.equals(COMMENTS_INDENTATION)
                    || checkNameInAttribute.equals(INDENTATION)) {
                assertWithMessage(checkNameModuleErrorMsg).that(moduleName).matches(MISC);
            }
            else if (checkNameInAttribute.equals(SUPPRESS_WARNINGS_HOLDER)) {
                assertWithMessage(checkNameModuleErrorMsg).that(moduleName).matches(ANNOTATION);
            }
            else {
                final ArrayList<String> moduleFileNames = checksNamesMap.get(moduleName);
                final String moduleNameErrorMsg = String.format(Locale.ROOT,
                        "module name: '%s' is not exist in '%s'", moduleName, PACKAGE_NAME);
                assertWithMessage(moduleNameErrorMsg).that(moduleFileNames).isNotNull();
                boolean match = false;
                final String checkNameWithSuffix = checkNameInAttribute + SUFFIX_CHECK;
                for (String filename : moduleFileNames) {
                    if (checkNameWithSuffix.equals(filename)) {
                        match = true;
                        break;
                    }
                }
                assertWithMessage(checkNameModuleErrorMsg).that(match).isTrue();
            }
        }
    }

    public static final class CheckTest extends DefaultHandler {

        public static final String SPLIT_CHECK_NAME_IN_ATTRIBUTE = "#";

        public static final String PREFIX_CONFIG = "config_";

        private static final String NODE_NAME = "a";

        private List<List<String>> checkNamesList;

        private List<String> singleCheckNameList;

        private String currentTag;

        @Override
        public void startDocument() {
            checkNamesList = new ArrayList<>();
        }

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) {
            if (qName.equals(NODE_NAME)) {
                final String[] moduleAndCheckName =
                        attributes.getValue(0).split(SPLIT_CHECK_NAME_IN_ATTRIBUTE);
                if (moduleAndCheckName[0].startsWith(PREFIX_CONFIG)) {
                    singleCheckNameList = new ArrayList<>();
                    final String moduleName =
                            moduleAndCheckName[0].replaceAll("(.*config_)|(\\.html.*)", "");
                    singleCheckNameList.add(moduleName);
                    singleCheckNameList.add(moduleAndCheckName[1]);
                }
            }
            currentTag = qName;
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (currentTag != null && singleCheckNameList != null && currentTag.equals(NODE_NAME)) {
                final String currentValue = new String(ch, start, length).trim();
                if (!currentValue.isEmpty() && !"\n".equals(currentValue)) {
                    singleCheckNameList.add(currentValue);
                }
                currentTag = null;
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (singleCheckNameList != null && qName.equals(NODE_NAME)) {
                checkNamesList.add(singleCheckNameList);
                singleCheckNameList = null;
            }
        }
    }
}
