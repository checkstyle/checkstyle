////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Test;

/**
 * Test cases for {@link SeverityLevel} enumeration.
 * @author Mehmet Can Cömert
 */
public class SeverityLevelTest {
    @Test(expected = IllegalArgumentException.class)
    public void testMisc() {
        final SeverityLevel o = SeverityLevel.getInstance("info");
        assertNotNull(o);
        assertEquals("info", o.toString());
        assertEquals("info", o.getName());

        SeverityLevel.getInstance("unknown"); // will fail
    }

    @Test
    public void testMixedCaseSpaces() {
        SeverityLevel.getInstance("IgnoRe ");
        SeverityLevel.getInstance(" iNfo");
        SeverityLevel.getInstance(" WarniNg");
        SeverityLevel.getInstance("    ERROR ");
    }

    @Test
    public void testMixedCaseSpacesWithDifferentLocales() {
        Locale[] differentLocales = new Locale[] {new Locale("TR", "tr") };
        Locale defaultLocale = Locale.getDefault();
        try {
            for (Locale differentLocale : differentLocales) {
                Locale.setDefault(differentLocale);
                testMixedCaseSpaces();
            }
        }
        finally {
            Locale.setDefault(defaultLocale);
        }
    }
}
