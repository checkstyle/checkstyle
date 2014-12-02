////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.annotation;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class AnnotationLocationCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testCorrect() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation/InputCorrectAnnotationLocation.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception
    {
        DefaultConfiguration checkConfig = createCheckConfig(AnnotationLocationCheck.class);
        final String[] expected = {
            "6: Annotation 'MyAnnotation1' should be alone on line.",
            "11: Annotation 'MyAnnotation1' should be alone on line.",
            "17: Annotation 'MyAnnotation1' have incorrect indentation level 8, expected level should be 4.",
            "25: Annotation 'MyAnnotation1' have incorrect indentation level 8, expected level should be 4.",
            "29: Annotation 'MyAnnotation1' should be alone on line.",
            "29: Annotation 'MyAnnotation2' should be alone on line.",
            "32: Annotation 'MyAnnotation2' have incorrect indentation level 7, expected level should be 4.",
            "36: Annotation 'MyAnnotation2' have incorrect indentation level 8, expected level should be 4.",
            "37: Annotation 'MyAnnotation3' have incorrect indentation level 6, expected level should be 4.",
            "38: Annotation 'MyAnnotation4' have incorrect indentation level 10, expected level should be 4.",
            "41: Annotation 'MyAnnotation1' should be alone on line.",
            "48: Annotation 'MyAnnotation1' have incorrect indentation level 12, expected level should be 8.",
            "61: Annotation 'MyAnnotation2' have incorrect indentation level 12, expected level should be 8.",
            "65: Annotation 'MyAnnotation2' have incorrect indentation level 12, expected level should be 8.",
            "70: Annotation 'MyAnnotation2' have incorrect indentation level 7, expected level should be 4.",
            "73: Annotation 'MyAnnotation1' should be alone on line.",
            "85: Annotation 'MyAnnotation2' have incorrect indentation level 11, expected level should be 8.",
            "88: Annotation 'MyAnnotation2' have incorrect indentation level 10, expected level should be 8.",
            "98: Annotation 'MyAnnotation2' have incorrect indentation level 0, expected level should be 3.",
        };
        verify(checkConfig, getPath("annotation/InputIncorrectAnnotationLocation.java"), expected);
    }
}
