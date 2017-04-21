////////////////////////////////////////////////////////////////////////////////
//checkstyle: Checks Java source code for adherence to a set of rules.
//Copyright (C) 2001-2004  Oliver Burn
//
//This library is free software; you can redistribute it and/or
//modify it under the terms of the GNU Lesser General Public
//License as published by the Free Software Foundation; either
//version 2.1 of the License, or (at your option) any later version.
//
//This library is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//Lesser General Public License for more details.
//
//You should have received a copy of the GNU Lesser General Public
//License along with this library; if not, write to the Free Software
//Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.coding.nestedfordepth;

/**
 * This Class contains no logic, but serves as test-input for the unit tests for the
 * <code>NestedForDepthCheck</code>-checkstyle enhancement.
 * @author Alexander Jesse
 * @see com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck
 */
public class InputNestedForDepth {

  /**
   * Dummy method containing 5 layers of for-statements.
   */
  public void nestedForFiveLevel() {
    int i = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;

    for (i1 = 0; i1 < 10; i1++) {
      for (i2 = 0; i2 < 10; i2++) {
        for (i3 = 0; i3 < 10; i3++) {
          for (i4 = 0; i4 < 10; i4++) {
            for (i5 = 0; i5 < 10; i5++) {
              i += 1;
            }
          }
        }
      }
    }
  }
}
