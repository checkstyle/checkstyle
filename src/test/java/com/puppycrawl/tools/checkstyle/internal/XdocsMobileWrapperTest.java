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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;

public class XdocsMobileWrapperTest {

    private static final List<String> NODES_TO_WRAP = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        NODES_TO_WRAP.add("pre");
        NODES_TO_WRAP.add("table");
        NODES_TO_WRAP.add("svg");
        NODES_TO_WRAP.add("img");
    }

    @Test
    public void testAllCheckSectionMobileWrapper() throws Exception {
        for (Path path : XdocUtil.getXdocsFilePaths()) {
            final File file = path.toFile();
            final String fileName = file.getName();

            final String input = new String(Files.readAllBytes(path), UTF_8);
            assertNotEquals("", input, fileName + ": input file cannot be empty");
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
                assertTrue("div".equals(node.getNodeName())
                        || "span".equals(node.getNodeName()), wrapperMessage);
                assertTrue(node.hasAttributes(), wrapperMessage);
                assertNotNull(node.getAttributes().getNamedItem("class"), wrapperMessage);
                assertTrue(node.getAttributes().getNamedItem("class").getNodeValue()
                                .contains("wrapper"), wrapperMessage);

                if ("table".equals(child.getNodeName())) {
                    iterateNode(child, fileName, sectionName);
                }
                if ("img".equals(child.getNodeName())) {
                    final String dataImageInlineMessage = fileName + "/" + sectionName + ": img "
                            + "needs the additional class inline if it should be displayed inline "
                            + "or block if scrolling in mobile view should be enabled.";
                    assertTrue(node.getAttributes().getNamedItem("class").getNodeValue()
                                    .matches(".*(block|inline).*"), dataImageInlineMessage);
                }
            }
            else {
                iterateNode(child, fileName, sectionName);
            }
        }
    }
}
