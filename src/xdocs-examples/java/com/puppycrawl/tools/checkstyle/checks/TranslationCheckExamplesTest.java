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
        final String messages = getPath("Example1/messages.properties");
        final String messagesFr = getPath("Example1/messages_fr.properties");
        final String messagesEs = getPath("Example1/messages_es.properties");
        final File[] propertyFiles = {
            new File(messages),
            new File(messagesFr),
            new File(messagesEs),
        };
        final Map<String, List<String>> expected = new HashMap<>();
        expected.put(messagesFr,
                List.of("1: " + getCheckMessage(MSG_KEY, "age"),
                "1: " + getCheckMessage(MSG_KEY, "cancel"),
                "1: " + getCheckMessage(MSG_KEY, "hello")));
        expected.put(messagesEs,
                List.of("1: " + getCheckMessage(MSG_KEY, "cancel"),
                "1: " + getCheckMessage(MSG_KEY, "name"),
                "1: " + getCheckMessage(MSG_KEY, "hello")));
        expected.put(messages,
                List.of("1: " + getCheckMessage(MSG_KEY, "age"),
                "1: " + getCheckMessage(MSG_KEY, "name"),
                "1: " + getCheckMessage(MSG_KEY, "greeting")));
        final String configFile = getPath("Example1.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFile);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();
        verify(createChecker(parsedConfig), propertyFiles, expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String buttonLabels = getPath("ButtonLabels.properties");
        final String buttonLabelsFr = getPath("ButtonLabels_fr.properties");
        final File[] propertyFiles = {
            new File(buttonLabels),
            new File(buttonLabelsFr),
        };
        final Map<String, List<String>> expected = new HashMap<>();
        expected.put(buttonLabelsFr,
                List.of("1: " + getCheckMessage(MSG_KEY, "cancel")));
        expected.put(buttonLabels,
                List.of("1: " + getCheckMessage(MSG_KEY, "name")));
        final String configFile = getPath("Example2.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFile);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();
        verify(createChecker(parsedConfig), propertyFiles, expected);
    }

    @Test
    public void testExample3() throws Exception {
        final File[] propertyFiles = {
            new File(getPath("messages_home.properties")),
            new File(getPath("messages_home.translations")),
        };
        final String[] expectedMessages = {
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages_home_fr.properties"),
            "1: " + getCheckMessage(MSG_KEY_MISSING_TRANSLATION_FILE,
                    "messages_home_fr.translations"),
        };
        final String configFile = getPath("Example3.java");
        final TestInputConfiguration testInputConfiguration =
                InlineConfigParser.parse(configFile);
        final DefaultConfiguration parsedConfig =
                testInputConfiguration.createConfiguration();
        verify(createChecker(parsedConfig), propertyFiles, getPath(""), expectedMessages);
    }
}
