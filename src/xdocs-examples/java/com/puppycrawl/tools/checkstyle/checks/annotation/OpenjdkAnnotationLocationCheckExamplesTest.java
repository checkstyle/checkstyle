///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ALONE_OR_SAME;
import static com.puppycrawl.tools.checkstyle.checks.annotation.OpenjdkAnnotationLocationCheck.MSG_KEY_ANNOTATION_ON_TARGET_LINE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class OpenjdkAnnotationLocationCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/openjdkannotationlocation";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "33:3: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "Deprecated"),
            "33:15: " + getCheckMessage(MSG_KEY_ANNOTATION_ALONE_OR_SAME, "SafeVarargs"),
            "41:3: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Nonnull"),
            "41:12: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Deprecated"),
            "52:3: " + getCheckMessage(MSG_KEY_ANNOTATION_ON_TARGET_LINE, "Deprecated"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

}
