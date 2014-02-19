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
package com.puppycrawl.tools.checkstyle.checks.coding;

/**
 * This Class contains no logic, but serves as test-input for the unit tests for the 
 * <code>OneStatementPerLineCheck</code>-checkstyle enhancement.
 * @author Alexander Jesse
 * @see com.puppycrawl.tools.checkstyle.checks.metrics.ExcessiveTypePerFileCheck
 */
public class OneStatementPerLineCheckInput {
  /**
   * Dummy innerclass to test the behaviour in the case of a smalltalk-style
   * statements (<code>myObject.firstMethod().secondMethod().thirdMethod()</code>).
   * For this programming style each method must return the obejct itself <code>this</code>.
   */
  class SmallTalkStyle {
    SmallTalkStyle doSomething1() {
      return this;
    }

    SmallTalkStyle doSomething2() {
      return this;
    }

    SmallTalkStyle doSomething3() {
      return this;
    }
  }

  /**
   * Dummy variable to work on.
   */
  private int one = 0;

  /**
   * Dummy variable to work on.
   */
  private int two = 0;

  /**
   * Simple legal method
   */
  public void doLegal() {
    one = 1;
    two = 2;
  }

  /**
   * The illegal format is used in a coment. Therefor the whole method is legal.
   */
  public void doLegalComment() {
    one = 1;
    //one = 1; two = 2;
    two = 2;
    /*
     * one = 1; two = 2;
     */
  }

  /**
   * The illegal format is used within a String. Therefor the whole method is legal.
   */
  public void doLegalString() {
    one = 1;
    two = 2;
    System.out.println("one = 1; two = 2");
  }

  /**
   * Within the for-header there are 3 Statements, but this is legal.
   */
  public void doLegalForLoop() {
    for (int i = 0; i < 20; i++) {
      one = i;
    }
  }

  /**
   * Simplest form a an illegal layout.
   */
  public void doIllegal() {
    one = 1; two = 2;
  }

  
  /**
   * Smalltalk-style is considered as one statement.
   */
  public void doIllegalSmallTalk() {
    SmallTalkStyle smalltalker = new SmallTalkStyle();
    smalltalker.doSomething1().doSomething2().doSomething3();
  }

  /**
   * Smalltalk-style is considered as one statement.
   */
  public void doIllegalSmallTalk2() {
    SmallTalkStyle smalltalker = new SmallTalkStyle();
    smalltalker.doSomething1()
               .doSomething2()
               .doSomething3();
  }
  
  /**
   * While theoretically being distributed over two lines, this is a sample
   * of 2 statements on one line.
   */
  public void doIllegal2() {
    one = 1
    ; two = 2;
  }
  
  /**
   * The StringBuffer is a Java-API-class that permits smalltalk-style concatenation
   * on the <code>append</code>-method.
   */
  public void doStringBuffer() {
    StringBuffer sb = new StringBuffer();
    sb.append("test ");
    sb.append("test2 ").append("test3 ");
    appendToSpringBuffer(sb, "test4");
  }
  
  /**
   * indirect stringbuffer-method. Used only internally.
   * @param sb The stringbuffer we want to append something
   * @param text The text to append
   */
  private void appendToSpringBuffer(StringBuffer sb, String text) {
    sb.append(text);
  }
}