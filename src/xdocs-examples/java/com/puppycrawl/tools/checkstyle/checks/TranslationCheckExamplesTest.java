///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.TranslationCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.TranslationCheck.MSG_KEY_MISSING_TRANSLATION_FILE;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;

public class TranslationCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/translation";
    }

    @Test
    public void testExample1() throws Exception {
        final File[] propertyFiles = {
            new File(getPath("messages.properties")),
            new File(getPath("messages.translations")),
        };

        final String[] expectedMessages = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages_fr.properties"),
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages_fr.translations"),
        };
        final String configFile = getPath("Example1.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFile);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();

        verify(
                createChecker(parsedConfig),
                propertyFiles,
                getPath(""),
                expectedMessages
        );
    }

    @Test
    public void testExample2() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final File[] propertyFiles = {
            new File(getPath("ButtonLabels.properties")),
            new File(getPath("ButtonLabels_fr.properties")),
        };

        expected.put(getPath("ButtonLabels_fr.properties"),
                List.of("1: " + getCheckMessage(MSG_KEY, "cancel")));
        expected.put(getPath("ButtonLabels.properties"),
                List.of("1: " + getCheckMessage(MSG_KEY, "name")));

        final String configFile = getPath("Example2.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFile);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();

        verify(
                createChecker(parsedConfig),
                propertyFiles,
                expected
        );
    }

    @Test
    public void testExample3() throws Exception {
        final Map<String, List<String>> expected = new HashMap<>();
        final File[] propertyFiles = {
            new File(getPath("Example3/messages.properties")),
            new File(getPath("Example3/messages_fr.properties")),
            new File(getPath("Example3/messages_ja.properties")),
        };

        expected.put(getPath("Example3/messages_fr.properties"),
                List.of("1: " + getCheckMessage(MSG_KEY, "age"),
                "1: " + getCheckMessage(MSG_KEY, "cancel"),
                "1: " + getCheckMessage(MSG_KEY, "hello")));
        expected.put(getPath("Example3/messages_ja.properties"),
                List.of("1: " + getCheckMessage(MSG_KEY, "cancel"),
                "1: " + getCheckMessage(MSG_KEY, "name"),
                "1: " + getCheckMessage(MSG_KEY, "hello")));
        expected.put(getPath("Example3/messages.properties"),
                List.of("1: " + getCheckMessage(MSG_KEY, "age"),
                "1: " + getCheckMessage(MSG_KEY, "name"),
                "1: " + getCheckMessage(MSG_KEY, "greeting")));

        final String configFile = getPath("Example3.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFile);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();

        verify(
                createChecker(parsedConfig),
                propertyFiles,
                expected
        );
    }
}
