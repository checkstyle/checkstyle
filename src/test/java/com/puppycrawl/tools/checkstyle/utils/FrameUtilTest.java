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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.isUtilsClassHasPrivateConstructor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class FrameUtilTest {

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertTrue(isUtilsClassHasPrivateConstructor(FrameUtil.class, true),
                "Constructor is not private");
    }

    @Test
    public void testUnusedMethod() throws Exception {
        final DetailAstImpl ident = new DetailAstImpl();
        ident.setText("testName");

        final Class<?> cls = Class.forName(FrameUtil.class.getName() + "$CatchFrame");
        final Constructor<?> constructor = cls.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        final Object o = constructor.newInstance(null, ident);

        final Object actual = TestUtil.getClassDeclaredMethod(cls, "getFrameNameIdent").invoke(o);
        assertEquals(ident, actual, "expected ident token");
        assertEquals("CATCH_FRAME",
            TestUtil.getClassDeclaredMethod(cls, "getType").invoke(o).toString(),
                "expected catch frame type");
    }

}
