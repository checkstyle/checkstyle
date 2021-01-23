////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XmlLoaderTest {

    @Test
    public void testParserConfiguredSuccessfully() throws Exception {
        final DummyLoader dummyLoader = new DummyLoader(new HashMap<>(1));
        final XMLReader parser = Whitebox.getInternalState(dummyLoader, "parser");
        assertEquals(dummyLoader, parser.getEntityResolver(), "Invalid entity resolver");
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(
                XmlLoader.LoadExternalDtdFeatureProvider.class, true),
                "Constructor is not private");
    }

    @Test
    public void testResolveEntityDefault() throws Exception {
        final Map<String, String> map = new HashMap<>();
        map.put("predefined", "/google.xml");
        final DummyLoader dummyLoader = new DummyLoader(map);
        assertNull(dummyLoader.resolveEntity("notPredefined", "BAD"), "Invalid entity");
    }

    @Test
    public void testResolveEntityMap() throws Exception {
        final Map<String, String> map = new HashMap<>();
        map.put("predefined", "/google.xml");
        final DummyLoader dummyLoader = new DummyLoader(map);
        assertNotNull(dummyLoader.resolveEntity("predefined", "BAD"), "Invalid entity");
    }

    private static final class DummyLoader extends XmlLoader {

        /* package */ DummyLoader(Map<String, String> publicIdToResourceNameMap)
                throws SAXException, ParserConfigurationException {
            super(publicIdToResourceNameMap);
        }

    }

}
