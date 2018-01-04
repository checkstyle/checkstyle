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

package com.google.checkstyle.test.chapter7javadoc.rule713atclauses;

import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagContinuationIndentationCheck;

public class JavadocTagContinuationIndentationTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter7javadoc/rule713atclauses";
    }

    @Test
    public void testWithDefaultConfiguration() throws Exception {
        final String msg = getCheckMessage(JavadocTagContinuationIndentationCheck.class,
                "tag.continuation.indent", 4);

        final String[] expected = {
            "47: " + msg,
            "109: " + msg,
            "112: " + msg,
            "203: " + msg,
            "206: " + msg,
            "221: " + msg,
            "223: " + msg,
            "285: " + msg,
            "288: " + msg,
            "290: " + msg,
            "310: " + msg,
            "322: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("JavadocTagContinuationIndentation");
        final String filePath = getPath("InputJavaDocTagContinuationIndentation.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
