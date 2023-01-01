///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class XdocsUrlTest {

    private static final String PACKAGE_NAME =
            "src/main/java/com/puppycrawl/tools/checkstyle/checks";

    private static final String TREE_WORKER = "TreeWalker";

    private static final String SUFFIX_CHECK = "Check";

    private static final String CHECKS = "checks";

    private static final String MISC = "misc";

    private static final String ANNOTATION = "annotation";

    private static final String COMMENTS_INDENTATION = "CommentsIndentation";

    private static final String INDENTATION = "Indentation";

    private static final String SUPPRESS_WARNINGS_HOLDER = "SuppressWarningsHolder";

    private static final Path AVAILABLE_CHECKS_PATH = Paths.get("src/xdocs/checks.xml");

    private static Map<String, List<String>> getXdocsMap() throws IOException {
        final Map<String, List<String>> checksNamesMap = new HashMap<>();
        final Set<Class<?>> checkSet = CheckUtil.getCheckstyleModules();
        final Set<Class<?>> treeWalkerOrFileSetCheckSet = checkSet.stream()
                .filter(clazz -> {
                    return AbstractCheck.class.isAssignableFrom(clazz)
                            || AbstractFileSetCheck.class.isAssignableFrom(clazz);
                })
                .collect(Collectors.toSet());
        for (Class<?> check : treeWalkerOrFileSetCheckSet) {
            final String checkName = check.getSimpleName();
            if (!TREE_WORKER.equals(checkName)) {
                String packageName = check.getPackage().getName();
                packageName = packageName.substring(packageName.lastIndexOf('.') + 1);
                if (CHECKS.equals(packageName)) {
                    packageName = MISC;
                }
                if (checksNamesMap.get(packageName) == null) {
                    final List<String> arrayList = new ArrayList<>();
                    arrayList.add(checkName);
                    checksNamesMap.put(packageName, arrayList);
                }
                else {
                    checksNamesMap.get(packageName).add(checkName);
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
        final Map<String, List<String>> checksNamesMap = getXdocsMap();
        for (List<String> sub : checkHandler.checkNamesList) {
            final String moduleName = sub.get(0);
            final String checkNameInAttribute = sub.get(1);
            final String checkNameInText = sub.get(2);
            final String checkNameInconsistentErrorMsg = String.format(Locale.ROOT,
                    "Check with name '%s' in attribute "
                            + "is not consistent with check name in text in file '%s'",
                    checkNameInAttribute, AVAILABLE_CHECKS_PATH);
            assertWithMessage(checkNameInconsistentErrorMsg)
                    .that(checkNameInText)
                    .matches(checkNameInAttribute);
            final String checkNameModuleErrorMsg = String.format(Locale.ROOT,
                    "Check with name '%s' is not in '%s' module",
                    checkNameInAttribute, moduleName);
            if (COMMENTS_INDENTATION.equals(checkNameInAttribute)
                    || INDENTATION.equals(checkNameInAttribute)) {
                assertWithMessage(checkNameModuleErrorMsg)
                        .that(moduleName)
                        .matches(MISC);
            }
            else if (SUPPRESS_WARNINGS_HOLDER.equals(checkNameInAttribute)) {
                assertWithMessage(checkNameModuleErrorMsg)
                        .that(moduleName)
                        .matches(ANNOTATION);
            }
            else {
                final List<String> moduleFileNames = checksNamesMap.get(moduleName);
                final String moduleNameErrorMsg = String.format(Locale.ROOT,
                        "module name: '%s' does not exist in '%s'", moduleName, PACKAGE_NAME);
                assertWithMessage(moduleNameErrorMsg)
                        .that(moduleFileNames)
                        .isNotNull();
                boolean match = false;
                final String checkNameWithSuffix = checkNameInAttribute + SUFFIX_CHECK;
                if (moduleFileNames.contains(checkNameWithSuffix)) {
                    match = true;
                }
                assertWithMessage(checkNameModuleErrorMsg)
                        .that(match)
                        .isTrue();
            }
        }
    }

    public static final class CheckTest extends DefaultHandler {

        private static final String SPLIT_CHECK_NAME_IN_ATTRIBUTE = "#";

        private static final String PREFIX_CONFIG = "config_";

        private static final String NODE_NAME = "a";

        private final List<List<String>> checkNamesList = new ArrayList<>();

        private List<String> singleCheckNameList;

        private String currentTag;

        @Override
        public void startElement(String uri, String localName, String qName,
                                 Attributes attributes) {
            if (NODE_NAME.equals(qName)) {
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
