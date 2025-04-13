///
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class XmlLoaderTest {

    @Test
    public void testParserConfiguredSuccessfully() throws Exception {
        final DummyLoader dummyLoader = new DummyLoader(new HashMap<>(1));
        final XMLReader parser = TestUtil.getInternalState(dummyLoader, "parser");
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
    public void testResolveEntityDefault() throws Exception {
        final Map<String, String> map = new HashMap<>();
        map.put("predefined", "/google.xml");
        final DummyLoader dummyLoader = new DummyLoader(map);
        assertWithMessage("Invalid entity")
            .that(dummyLoader.resolveEntity("notPredefined", "BAD"))
            .isNull();
    }

    @Test
    public void testResolveEntityMap() throws Exception {
        final Map<String, String> map = new HashMap<>();
        map.put("predefined", "/google.xml");
        final DummyLoader dummyLoader = new DummyLoader(map);
        assertWithMessage("Invalid entity")
            .that(dummyLoader.resolveEntity("predefined", "BAD"))
            .isNotNull();
    }

    private static final class DummyLoader extends XmlLoader {

        private DummyLoader(Map<String, String> publicIdToResourceNameMap)
                throws SAXException, ParserConfigurationException {
            super(publicIdToResourceNameMap);
        }

    }

}
