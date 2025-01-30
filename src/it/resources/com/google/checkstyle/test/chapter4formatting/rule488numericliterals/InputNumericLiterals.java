package com.google.checkstyle.test.chapter4formatting.rule488numericliterals;

class InputNumericLiterals {
  /** test. * */
  private final long ignore = 666l + 666L; // violation 'Should use uppercase 'L'.'

  private String notWarn = "666l";  

  private long foo() {
    processUpperEll(66l); // violation 'Should use uppercase 'L'.'
    processUpperEll(66L);  
    processUpperEll("s", 66l); // violation 'Should use uppercase 'L'.'
    processUpperEll("s", 66L);  

    return 666l + 666L; // violation 'Should use uppercase 'L'.'
  }

  private void processUpperEll(long xyz) {
    long bad =
        (4 + 5 * 7 ^ 66l / 7 + 890) // violation 'Should use uppercase 'L'.'
            & (88l + 78 * 4); // violation 'Should use uppercase 'L'.'
    long good = (4 + 5 * 7 ^ 66L / 7 + 890) & (88L + 78 * 4);  
    long[] array = {
      66l, // violation 'Should use uppercase 'L'.'
      66L,  
    };
  }

  private void processUpperEll(String s, long l) {}

  class Inner {
    /** test. * */
    private static final long IGNORE = 666l + 666L; // violation 'Should use uppercase 'L'.'

    private static final String notWarn = "666l";  

    private long foo() {
      processUpperEll(66l); // violation 'Should use uppercase 'L'.'
      processUpperEll(66L);  
      processUpperEll("s", 66l); // violation 'Should use uppercase 'L'.'
      processUpperEll("s", 66L);  

      return 666l + 666L; // violation 'Should use uppercase 'L'.'
    }

    private void processUpperEll(long xyz) {
      long bad =
          (4 + 5 * 7 ^ 66l / 7 + 890) // violation 'Should use uppercase 'L'.'
              & (88l + 78 * 4); // violation 'Should use uppercase 'L'.'
      long good = (4 + 5 * 7 ^ 66L / 7 + 890) & (88L + 78 * 4);  
    }

    private void processUpperEll(String s, long l) {
      long[] array = {
        66l, // violation 'Should use uppercase 'L'.'
        66L,  
      };
    }

    void fooMethod() {
      Foo foo =
          new Foo() {
            /** test. * */
            private final long ignore = 666l + 666L; // violation 'Should use uppercase 'L'.'

            private String notWarn = "666l";  

            private long foo() {
              processUpperEll(66l); // violation 'Should use uppercase 'L'.'
              processUpperEll(66L);  
              processUpperEll("s", 66l); // violation 'Should use uppercase 'L'.'
              processUpperEll("s", 66L);  

              return 666l + 666L; // violation 'Should use uppercase 'L'.'
            }

            private void processUpperEll(long x) {
              long bad =
                  (4 + 5 * 7 ^ 66l / 7 + 890) // violation 'Should use uppercase 'L'.'
                      & (88l + 78 * 4); // violation 'Should use uppercase 'L'.'
              long good = (4 + 5 * 7 ^ 66L / 7 + 890) & (88L + 78 * 4);  
              long[] array = {
                66l, // violation 'Should use uppercase 'L'.'
                66L,  
              };
            }

            private void processUpperEll(String s, long x) {}
          };
    }
  }

  class Foo {}

  interface Long {
    public static final long IGNORE = 666l + 666L; // violation 'Should use uppercase 'L'.'
    public static final String notWarn = "666l";  
    long bad =
        (4 + 5 * 7 ^ 66l / 7 + 890) // violation 'Should use uppercase 'L'.'
            & (88l + 78 * 4); // violation 'Should use uppercase 'L'.'
    long good = (4 + 5 * 7 ^ 66L / 7 + 890) & (88L + 78 * 4);  
  }
}
