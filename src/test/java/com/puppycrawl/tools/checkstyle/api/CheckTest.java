////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class CheckTest {
    @SuppressWarnings("deprecation")
    @Test
    public void testInstanceOfCheck() {
        final Object module = new Check() {
            @Override
            public int[] getDefaultTokens() {
                return CommonUtils.EMPTY_INT_ARRAY;
            }
        };

        Assert.assertTrue("Check must be an instance of AbstractCheck - 1",
                module instanceof AbstractCheck);
        Assert.assertTrue("Check must be an instance of AbstractCheck - 2",
                AbstractCheck.class.isInstance(module));
        Assert.assertTrue("Check must be able to be assignable to AbstractCheck",
                AbstractCheck.class.isAssignableFrom(Check.class));
    }
}
