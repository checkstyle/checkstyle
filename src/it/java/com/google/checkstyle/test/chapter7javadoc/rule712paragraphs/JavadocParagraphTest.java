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

package com.google.checkstyle.test.chapter7javadoc.rule712paragraphs;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParagraphCheck;

public class JavadocParagraphTest extends BaseCheckTestSupport {

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Test
    public void javadocParagraphCorrectTest() throws Exception {

        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;

        final Configuration checkConfig = builder.getCheckConfig("JavadocParagraph");
        final String filePath = builder.getFilePath("InputCorrectJavadocParagraphCheck");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void javadocParagraphIncorrectTest() throws Exception {

        final String msgBefore = getCheckMessage(JavadocParagraphCheck.class,
                "javadoc.paragraph.line.before");
        final String msgRed = getCheckMessage(JavadocParagraphCheck.class,
                "javadoc.paragraph.redundant.paragraph");
        final String msgMisplaced = getCheckMessage(JavadocParagraphCheck.class,
                "javadoc.paragraph.misplaced.tag");

        final String[] expected = {
            "5: " + msgMisplaced,
            "5: " + msgBefore,
            "6: " + msgMisplaced,
            "6: " + msgBefore,
            "12: " + msgMisplaced,
            "12: " + msgBefore,
            "14: " + msgMisplaced,
            "21: " + msgBefore,
            "30: " + msgRed,
            "31: " + msgMisplaced,
            "31: " + msgBefore,
            "32: " + msgMisplaced,
            "32: " + msgBefore,
            "33: " + msgMisplaced,
            "33: " + msgBefore,
            "37: " + msgMisplaced,
            "37: " + msgBefore,
            "43: " + msgMisplaced,
            "43: " + msgRed,
            "46: " + msgMisplaced,
            "48: " + msgMisplaced,
            "48: " + msgBefore,
            "49: " + msgMisplaced,
            "49: " + msgBefore,
            "59: " + msgRed,
            "68: " + msgMisplaced,
            "68: " + msgBefore,
            "70: " + msgMisplaced,
            "73: " + msgMisplaced,
            "73: " + msgBefore,
        };

        final Configuration checkConfig = builder.getCheckConfig("JavadocParagraph");
        final String filePath = builder.getFilePath("InputIncorrectJavadocParagraphCheck");

        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
