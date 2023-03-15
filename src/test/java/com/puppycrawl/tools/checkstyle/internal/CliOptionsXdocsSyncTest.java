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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;
import picocli.CommandLine;
import picocli.CommandLine.Model.OptionSpec;

public class CliOptionsXdocsSyncTest {

    @Test
    public void validateCliDocSections() throws Exception {
        final Map<String, String> cmdDesc = new HashMap<>();

        final NodeList sections = getSectionsFromXdoc("src/xdocs/cmdline.xml.vm");
        final Set<String> cmdOptions = getListById(sections.item(2), "CLI_Options");
        for (String option : cmdOptions) {
            final String text = option.trim().replaceAll("\\s+", " ");
            cmdDesc.put(text.substring(0, 2), text.substring(text.indexOf(" - ") + 3));
        }

        final Class<?> cliOptions = Class.forName("com.puppycrawl.tools.checkstyle"
                + ".Main$CliOptions");
        final CommandLine commandLine = new CommandLine(cliOptions);
        final List<OptionSpec> optionSpecList = commandLine.getCommandSpec().options();

        for (OptionSpec opt : optionSpecList) {
            final String option = opt.names()[0];
            if ("-h".equals(option) || "-V".equals(option)) {
                cmdDesc.remove(option);
                continue;
            }
            final String descXdoc = cmdDesc.get(option);
            final String descMain = opt.description()[0];
            assertWithMessage("CLI Option: " + option + " present in "
                    + "Main.java but not documented in cmdline.xml.vm")
                    .that(descXdoc)
                    .isNotNull();
            assertWithMessage("CLI options descriptions in xdoc: "
                    + " should match that of in Main.java")
                .that(descMain)
                .isEqualTo(descXdoc);
            cmdDesc.remove(option);
        }
        assertWithMessage("CLI Options: %s present in cmdline.xml.vm, not documented in Main.java",
                    cmdDesc)
                .that(cmdDesc)
                .isEmpty();
    }

    @Test
    public void validateCliUsageSection() throws Exception {
        final NodeList sections = getSectionsFromXdoc("src/xdocs/cmdline.xml.vm");
        final Node usageSource = XmlUtil.getFirstChildElement(sections.item(2));
        final String usageText = XmlUtil.getFirstChildElement(usageSource).getTextContent();

        final Set<String> shortParamsXdoc = getParameters(usageText, "-[a-zA-CE-X]\\b");
        final Set<String> longParamsXdoc = getParameters(usageText, "-(-\\w+)+");

        final Class<?> cliOptions = Class.forName("com.puppycrawl.tools.checkstyle"
                + ".Main$CliOptions");
        final CommandLine commandLine = new CommandLine(cliOptions);
        final Set<String> shortParamsMain = commandLine.getCommandSpec().options()
                        .stream()
                        .map(OptionSpec::shortestName)
                        .collect(Collectors.toSet());
        final Set<String> longParamsMain = commandLine.getCommandSpec().options()
                        .stream()
                        .map(OptionSpec::longestName)
                        .filter(names -> names.length() != 2)
                        .collect(Collectors.toSet());

        assertWithMessage("Short parameters in Main.java and cmdline"
                + ".xml.vm should match")
            .that(shortParamsMain)
            .isEqualTo(shortParamsXdoc);
        assertWithMessage("Long parameters in Main.java and cmdline"
                + ".xml.vm should match")
            .that(longParamsMain)
            .isEqualTo(longParamsXdoc);
    }

    private static Set<String> getParameters(String text, String regex) {
        final Set<String> result = new HashSet<>();
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    private static NodeList getSectionsFromXdoc(String xdocPath) throws Exception {
        final Path path = Paths.get(xdocPath);
        final String input = Files.readString(path);
        final Document document = XmlUtil.getRawXml(path.getFileName().toString(), input, input);
        return document.getElementsByTagName("section");
    }

    private static Set<String> getListById(Node subSection, String id) {
        Set<String> result = null;
        final Node node = XmlUtil.findChildElementById(subSection, id);
        if (node != null) {
            result = XmlUtil.getChildrenElements(node)
                    .stream()
                    .map(Node::getTextContent)
                    .collect(Collectors.toSet());
        }
        return result;
    }
}
