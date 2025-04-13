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
// violation below ''package' should be separated from previous line.'
package com.google.checkstyle.test.chapter3filestructure.rule3sourcefile;
import java.util.concurrent.Callable; // violation ''import' should be separated from previous line.'
class InputSourceFileStructure { // violation ''CLASS_DEF' should be separated from previous line.'
  public static final double FOO_PI = 3.1415;
  private boolean flag = true;
  static { // violation ''STATIC_INIT' should be separated from previous line.'
    // empty static initializer
  }

  {
    // empty instance initializer
  }

  /** Some javadoc... */
  private InputSourceFileStructure() {
    // empty
  }

  public int compareTo(InputSourceFileStructure obj) {
    int number = 0;
    return 0;
  }
  /**
   * Some javadoc...
   *
   * @param task some description....
   * @param result some description....
   * @return some description....
   */
  public static <T> Callable<T> callable(Runnable task, T result) {
    // violation above ''METHOD_DEF' should be separated from previous line.'
    return null;
  }

  public int getBeastNumber() {
    return 666;
  }
  interface IntEnum {
    // violation above ''INTERFACE_DEF' should be separated from previous line.'
  }

  class InnerClass {

    public static final double FOO_PI_INNER = 3.1415;
    private boolean flagInner = true;
    { // violation ''INSTANCE_INIT' should be separated from previous line.'
      // empty instance initializer
    }

    private InnerClass() {
      // empty
    }
  }

  class InnerClass2 { // ok
    private InnerClass2() { // ok
      // empty
    }
  }

  class InnerClass3 { // ok
    public int compareTo(InputSourceFileStructure obj) { // ok
      int number = 0;
      return 0;
    }
  }
}

// violation below 'Top-level class Class1 has to reside in its own source file.'
class Class1 { // ok
  private Class1() {} // ok
}
class Class2 {
  // 2 violations above:
  //  'Top-level class Class2 has to reside in its own source file.'
  //  'CLASS_DEF' should be separated from previous line.'
  public int compareTo(InputSourceFileStructure obj) { // ok
    int number = 0;
    return 0;
  }
  Class2 anon = // violation ''VARIABLE_DEF' should be separated from previous line.'
          new Class2() {
            public int compareTo(InputSourceFileStructure obj) { // ok
              int number = 0;
              return 0;
            }
          };
}
