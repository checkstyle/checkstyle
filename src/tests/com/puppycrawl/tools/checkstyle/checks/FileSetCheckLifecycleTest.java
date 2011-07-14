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

import static org.junit.Assert.assertTrue;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import java.io.File;
import java.util.List;
import org.junit.Test;

public class FileSetCheckLifecycleTest
    extends BaseCheckTestSupport
{
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration aCheckConfig)
    {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aCheckConfig);
        return dc;
    }

    public static class TestFileSetCheck extends AbstractFileSetCheck
    {
        private static boolean destroyed;

        @Override
        public void destroy()
        {
            destroyed = true;
        }

        public static boolean isDestroyed()
        {
            return destroyed;
        }

        @Override
        protected void processFiltered(File aFile, List<String> aLines)
        {
        }
    }

    @Test
    public void testTranslation() throws Exception
    {
        final Configuration checkConfig =
            createCheckConfig(TestFileSetCheck.class);
        final String[] expected = {
        };
        verify(checkConfig, getPath("InputScopeAnonInner.java"), expected);

        assertTrue("destroy() not called by Checker", TestFileSetCheck.isDestroyed());
    }

}
