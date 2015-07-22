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

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.TranslationCheck.MSG_KEY;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class TranslationCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration checkConfig) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(checkConfig);
        return dc;
    }

    @Test
    public void testTranslation() throws Exception {
        final Configuration checkConfig = createCheckConfig(TranslationCheck.class);
        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY, "only.english"),
        };
        final File[] propertyFiles = new File[] {
            new File(getPath("messages_test_de.properties")),
            new File(getPath("messages_test.properties")),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("messages_test_de.properties"),
            expected);
    }

    @Test
    public void testBaseNameSeparator() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(TranslationCheck.class);
        checkConfig.addAttribute("basenameSeparator", "-");
        final String[] expected = {
            "0: " + getCheckMessage(MSG_KEY, "only.english"),
        };
        final File[] propertyFiles = new File[] {
            new File(getPath("app-dev.properties")),
            new File(getPath("app-stage.properties")),
        };
        verify(
            createChecker(checkConfig),
            propertyFiles,
            getPath("app-dev.properties"),
            expected);
    }

}
