///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class XmlLoaderTest {

    private static final String XML_WITH_MAPPED_PUBLIC_ID =
            """
            <!DOCTYPE module PUBLIC "predefined" "urn:checkstyle:unreachable">
            <module name="Checker"/>
            """;

    @Test
    public void testParserConfiguredSuccessfully() throws Exception {
        final DummyLoader dummyLoader = new DummyLoader(new HashMap<>(1));
        final XMLReader parser = TestUtil.getInternalState(dummyLoader, "parser", XMLReader.class);
        assertWithMessage("Invalid entity resolver")
            .that(parser.getEntityResolver())
            .isEqualTo(dummyLoader);
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertWithMessage("Constructor is not private")
                .that(isUtilsClassHasPrivateConstructor(
                        XmlLoader.LoadExternalDtdFeatureProvider.class))
                .isTrue();
    }

    @Test
    public void testResolveEntityUnknownPublicId() throws Exception {
        final Map<String, String> map = new HashMap<>();
        map.put("predefined", "/google.xml");
        final DummyLoader dummyLoader = new DummyLoader(map);
        assertWithMessage("Unknown public IDs should use the existing parser policy")
            .that(dummyLoader.resolveEntity("notPredefined", "BAD"))
            .isNull();
    }

    @Test
    public void testResolveEntityMap() throws Exception {
        final Map<String, String> map = new HashMap<>();
        map.put("predefined", "com/puppycrawl/tools/checkstyle/configuration_1_3.dtd");
        final DummyLoader dummyLoader = new DummyLoader(map);
        try (InputStream stream = dummyLoader.resolveEntity("predefined", "BAD").getByteStream()) {
            assertWithMessage("DTD resource must have readable content")
                .that(stream.read())
                .isNotEqualTo(-1);
        }
    }

    @Test
    public void testResolveEntityWithoutPublicId() throws Exception {
        final DummyLoader dummyLoader = new DummyLoader(Map.of());
        assertWithMessage("System-only entities should use the existing parser policy")
            .that(dummyLoader.resolveEntity(null, "BAD"))
            .isNull();
    }

    @Test
    public void testParseMappedResource() throws Exception {
        final DummyLoader dummyLoader = new DummyLoader(Map.of("predefined",
                "com/puppycrawl/tools/checkstyle/configuration_1_3.dtd"));
        final InputSource inputSource =
                new InputSource(new StringReader(XML_WITH_MAPPED_PUBLIC_ID));
        assertDoesNotThrow(() -> dummyLoader.parseInputSource(inputSource),
                "Bundled DTD should parse without system identifier fallback");
    }

    @Test
    public void testResolveEntityMissingResource() throws Exception {
        final DummyLoader dummyLoader = new DummyLoader(Map.of("predefined", "missing.dtd"));
        final SAXException exception = getExpectedThrowable(SAXException.class,
                () -> dummyLoader.resolveEntity("predefined", "BAD"),
                "Missing catalogued DTD resource should fail immediately");
        assertWithMessage("Invalid exception message")
            .that(exception)
            .hasMessageThat()
            .isEqualTo("Unable to load DTD resource missing.dtd for public ID predefined");
    }

    @Test
    public void testParseMissingResource() throws Exception {
        final DummyLoader dummyLoader = new DummyLoader(Map.of("predefined", "missing.dtd"));
        final InputSource inputSource =
                new InputSource(new StringReader(XML_WITH_MAPPED_PUBLIC_ID));
        final SAXException exception = getExpectedThrowable(SAXException.class,
                () -> dummyLoader.parseInputSource(inputSource),
                "Missing catalogued DTD resource should fail before system identifier fallback");
        assertWithMessage("Invalid exception message")
            .that(exception)
            .hasMessageThat()
            .isEqualTo("Unable to load DTD resource missing.dtd for public ID predefined");
    }

    private static final class DummyLoader extends XmlLoader {

        private DummyLoader(Map<String, String> publicIdToResourceNameMap)
                throws SAXException, ParserConfigurationException {
            super(publicIdToResourceNameMap);
        }

    }

}
