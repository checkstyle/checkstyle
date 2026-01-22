package com.google.checkstyle.test.chapter4formatting.rule488numericliterals;

class InputNumericLiterals {
  /** Test. * */
  private final long ignore = 666l + 666L; // violation 'Should use uppercase 'L'.'

  private String notWarn = "666l"; // ok

  private long foo() {
    processUpperEll(66l); // violation 'Should use uppercase 'L'.'
    processUpperEll(66L); // ok
    processUpperEll("s", 66l); // violation 'Should use uppercase 'L'.'
    processUpperEll("s", 66L); // ok

    return 666l + 666L; // violation 'Should use uppercase 'L'.'
  }

  private void processUpperEll(long xyz) {
    long bad =
        (4 + 5 * 7 ^ 66l / 7 + 890) // violation 'Should use uppercase 'L'.'
            & (88l + 78 * 4); // violation 'Should use uppercase 'L'.'
    long good = (4 + 5 * 7 ^ 66L / 7 + 890) & (88L + 78 * 4); // ok
    long[] array = {
      66l, // violation 'Should use uppercase 'L'.'
      66L, // ok
    };
  }

  private void processUpperEll(String s, long l) {}

  class Inner {
    /** Test. * */
    private static final long IGNORE = 666l + 666L; // violation 'Should use uppercase 'L'.'

    private static final String notWarn = "666l"; // ok

    private long foo() {
      processUpperEll(66l); // violation 'Should use uppercase 'L'.'
      processUpperEll(66L); // ok
      processUpperEll("s", 66l); // violation 'Should use uppercase 'L'.'
      processUpperEll("s", 66L); // ok

      return 666l + 666L; // violation 'Should use uppercase 'L'.'
    }

    private void processUpperEll(long xyz) {
      long bad =
          (4 + 5 * 7 ^ 66l / 7 + 890) // violation 'Should use uppercase 'L'.'
              & (88l + 78 * 4); // violation 'Should use uppercase 'L'.'
      long good = (4 + 5 * 7 ^ 66L / 7 + 890) & (88L + 78 * 4); // ok
    }

    private void processUpperEll(String s, long l) {
      long[] array = {
        66l, // violation 'Should use uppercase 'L'.'
        66L, // ok
      };
    }

    void fooMethod() {
      Foo foo =
          new Foo() {
            /** Test. * */
            private final long ignore = 666l + 666L; // violation 'Should use uppercase 'L'.'

            private String notWarn = "666l"; // ok

            private long foo() {
              processUpperEll(66l); // violation 'Should use uppercase 'L'.'
              processUpperEll(66L); // ok
              processUpperEll("s", 66l); // violation 'Should use uppercase 'L'.'
              processUpperEll("s", 66L); // ok

              return 666l + 666L; // violation 'Should use uppercase 'L'.'
            }

            private void processUpperEll(long x) {
              long bad =
                  (4 + 5 * 7 ^ 66l / 7 + 890) // violation 'Should use uppercase 'L'.'
                      & (88l + 78 * 4); // violation 'Should use uppercase 'L'.'
              long good = (4 + 5 * 7 ^ 66L / 7 + 890) & (88L + 78 * 4); // ok
              long[] array = {
                66l, // violation 'Should use uppercase 'L'.'
                66L, // ok
              };
            }

            private void processUpperEll(String s, long x) {}
          };
    }
  }

  class Foo {}

  interface Long {
    public static final long IGNORE = 666l + 666L; // violation 'Should use uppercase 'L'.'
    public static final String notWarn = "666l"; // ok
    long bad =
        (4 + 5 * 7 ^ 66l / 7 + 890) // violation 'Should use uppercase 'L'.'
            & (88l + 78 * 4); // violation 'Should use uppercase 'L'.'
    long good = (4 + 5 * 7 ^ 66L / 7 + 890) & (88L + 78 * 4); // ok
  }
}
