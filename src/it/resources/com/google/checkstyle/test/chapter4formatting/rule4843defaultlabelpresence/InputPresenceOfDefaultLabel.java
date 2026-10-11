package com.google.checkstyle.test.chapter4formatting.rule4843defaultlabelpresence;

/** Some javadoc. */
public class InputPresenceOfDefaultLabel {
  /** Some javadoc. */
  public void foo() {
    int i = 1;
    switch (i) {
      case 1:
        i++;
        break;
      case 2:
        i--;
        break;
      default:
        return;
    }
    switch (i) { // violation 'switch without "default" clause.'
    }
  }

  class BadTest {
    /** Some javadoc. */
    public void foo() {
      int i = 1;
      switch (i) { // violation 'switch without "default" clause.'
        case 1:
          i++;
          break;
        case 2:
          i--;
          break;
      }
      switch (i) { // violation 'switch without "default" clause.'
      }
    }

    class Inner {
      /** Some javadoc. */
      public void foo1() {
        int i = 1;
        switch (i) { // violation 'switch without "default" clause.'
          case 1:
            i++;
            break;
          case 2:
            i--;
            break;
        }
        Foo foo =
            new Foo() {
              /** Some javadoc. */
              public void foo() {
                int i = 1;
                switch (i) { // violation 'switch without "default" clause.'
                  case 1:
                    i++;
                    break;
                  case 2:
                    i--;
                    break;
                }
                switch (i) { // violation 'switch without "default" clause.'
                }
              }
            };
      }
    }
  }

  interface Foo {
    public void foo();
  }
}
