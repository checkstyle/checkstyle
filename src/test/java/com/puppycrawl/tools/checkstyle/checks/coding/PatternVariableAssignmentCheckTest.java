///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.PatternVariableAssignmentCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyLineSeparatorCheck.MSG_SHOULD_BE_SEPARATED;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

import org.junit.jupiter.api.Test;

public class PatternVariableAssignmentCheckTest extends AbstractModuleTestSupport {

  @Override
  protected String getPackageLocation() {
    return "com/puppycrawl/tools/checkstyle/checks/coding/patternvariableassignment";
  }

  @Test
  public void testUnnecessaryNullCheckWithInstanceof() throws Exception {

      final String[] expected = {
          // incomplete for now
          "18: " + getCheckMessage(MSG_KEY, "s"),
          "22: " + getCheckMessage(MSG_KEY, "x"),
          "23: " + getCheckMessage(MSG_KEY, "y"),
          "26: " + getCheckMessage(MSG_KEY, "c"),
          "29: " + getCheckMessage(MSG_KEY, "c"),
          "32: " + getCheckMessage(MSG_KEY, "c"),
      };

      verifyWithInlineXmlConfig(getNonCompilablePath(
          "InputPatternVariableAssignmentCheck1.java"), expected);
  }

}
