////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.Test;
public class ClassResolverTest
{
    @Test
    public void testMisc() throws ClassNotFoundException
    {
        final Set<String> imps = Sets.newHashSet();
        imps.add("java.io.File");
        imps.add("nothing.will.match.*");
        imps.add("java.applet.*");
        ClassResolver cr =
            new ClassResolver(Thread.currentThread().getContextClassLoader(),
                              null, imps);
        assertNotNull(cr);
        try {
            cr.resolve("who.will.win.the.world.cup", "");
            fail("Should not resolve class");
        }
        catch (ClassNotFoundException e) {
        }
        cr.resolve("java.lang.String", "");
        cr.resolve("StringBuffer", "");
        cr.resolve("AppletContext", "");

        try {
            cr.resolve("ChoiceFormat", "");
            fail();
        }
        catch (ClassNotFoundException e) {
        }

        imps.add("java.text.ChoiceFormat");
        cr.resolve("ChoiceFormat", "");

        cr = new ClassResolver(Thread.currentThread().getContextClassLoader(),
                               "java.util", imps);
        cr.resolve("List", "");
        try {
            cr.resolve("two.nil.england", "");
            fail();
        }
        catch (ClassNotFoundException e) {
        }
    }
}
