///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
//
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
///

package com.google.checkstyle.test.chapter4formatting.rule461verticalwhitespace;

import java.util.concurrent.Callable;

class InputFormattedVerticalWhitespace {
  public static final double FOO_PI = 3.1415;
  private boolean flag = true;

  static {
    // empty static initializer
  }

  {
    // empty instance initializer
  }

  /** Some javadoc. */
  private InputFormattedVerticalWhitespace() {
    // empty
  }

  public int compareTo(InputFormattedVerticalWhitespace obj) {
    int number = 0;
    return 0;
  }

  /**
   * Some javadoc.
   *
   * @param task some description.
   * @param result some description.
   * @return some description.
   */
  public static <T> Callable<T> callable(Runnable task, T result) {

    return null;
  }

  public int getBeastNumber() {
    return 666;
  }

  interface IntEnum {}

  class InnerClass {

    public static final double FOO_PI_INNER = 3.1415;
    private boolean flagInner = true;

    {
      // empty instance initializer
    }

    private InnerClass() {
      // empty
    }
  }

  class InnerClass2 {
    private InnerClass2() {
      // empty
    }
  }

  class InnerClass3 {
    public int compareTo(InputFormattedVerticalWhitespace obj) {
      int number = 0;
      return 0;
    }
  }

  class Clazz {
    private Clazz() {}
  }

  class Class2 {
    public int compareTo(InputFormattedVerticalWhitespace obj) {
      int number = 0;
      return 0;
    }

    Class2 anon =
        new Class2() {
          public int compareTo(InputFormattedVerticalWhitespace obj) {
            int number = 0;
            return 0;
          }
        };
  }
}
