////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.powermock.reflect.Whitebox;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class XmlLoaderTest {

    @Test
    public void testParserConfiguredSuccessfully() throws Exception {
        final DummyLoader dummyLoader = new DummyLoader(new HashMap<>(1));
        final XMLReader parser = Whitebox.getInternalState(dummyLoader, "parser");
        assertEquals("Invalid entity resolver", dummyLoader, parser.getEntityResolver());
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue("Constructor is not private", isUtilsClassHasPrivateConstructor(
                XmlLoader.FeaturesForVerySecureJavaInstallations.class, true));
    }

    private static final class DummyLoader extends XmlLoader {

        DummyLoader(Map<String, String> publicIdToResourceNameMap)
                throws SAXException, ParserConfigurationException {
            super(publicIdToResourceNameMap);
        }

    }

}
