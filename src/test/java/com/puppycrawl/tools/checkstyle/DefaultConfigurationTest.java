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

package com.puppycrawl.tools.checkstyle;

import org.junit.Assert;
import org.junit.Test;

public class DefaultConfigurationTest {

    @Test
    public void testRemoveChild() {
        DefaultConfiguration config = new DefaultConfiguration("Myconfig");
        DefaultConfiguration configChild = new DefaultConfiguration("childConfig");
        Assert.assertTrue(config.getChildren().length == 0);
        config.addChild(configChild);
        Assert.assertTrue(config.getChildren().length == 1);
        config.removeChild(configChild);
        Assert.assertTrue(config.getChildren().length == 0);
    }
}
