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

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;

public class XdocsMobileWrapperTest {

    private static final Set<String> NODES_TO_WRAP = Set.of(
        "pre",
        "table",
        "svg",
        "img"
    );

    @Test
    public void testAllCheckSectionMobileWrapper() throws Exception {
        for (Path path : XdocUtil.getXdocsFilePaths()) {
            final File file = path.toFile();
            final String fileName = file.getName();

            final String input = Files.readString(path);
            assertWithMessage(fileName + ": input file cannot be empty")
                    .that(input)
                    .isNotEmpty();
            final Document document = XmlUtil.getRawXml(fileName, input, input);
            final NodeList sources = document.getElementsByTagName("section");

            for (int position = 0; position < sources.getLength(); position++) {
                final Node section = sources.item(position);
                final String sectionName = XmlUtil.getNameAttributeOfNode(section);

                if ("Content".equals(sectionName) || "Overview".equals(sectionName)) {
                    continue;
                }

                iterateNode(section, fileName, sectionName);
            }
        }
    }

    private static void iterateNode(Node node, String fileName, String sectionName) {
        for (Node child : XmlUtil.getChildrenElements(node)) {
            if (NODES_TO_WRAP.contains(child.getNodeName())) {
                final String wrapperMessage = fileName + "/" + sectionName + ": Tag '"
                        + child.getNodeName() + "' in '" + node.getNodeName()
                        + "' needs a wrapping <span> or <div> with the class 'wrapper'.";
                assertWithMessage(wrapperMessage)
                        .that("div".equals(node.getNodeName()) || "span".equals(node.getNodeName()))
                        .isTrue();
                assertWithMessage(wrapperMessage)
                        .that(node.hasAttributes())
                        .isTrue();
                assertWithMessage(wrapperMessage)
                        .that(node.getAttributes().getNamedItem("class"))
                        .isNotNull();
                assertWithMessage(wrapperMessage)
                        .that(node.getAttributes().getNamedItem("class")
                                .getNodeValue().contains("wrapper"))
                        .isTrue();

                if ("table".equals(child.getNodeName())) {
                    iterateNode(child, fileName, sectionName);
                }
                if ("img".equals(child.getNodeName())) {
                    final String dataImageInlineMessage = fileName + "/" + sectionName + ": img "
                            + "needs the additional class inline if it should be displayed inline "
                            + "or block if scrolling in mobile view should be enabled.";
                    assertWithMessage(dataImageInlineMessage)
                            .that(node.getAttributes().getNamedItem("class")
                                    .getNodeValue().matches(".*(block|inline).*"))
                            .isTrue();
                }
            }
            else {
                iterateNode(child, fileName, sectionName);
            }
        }
    }
}
