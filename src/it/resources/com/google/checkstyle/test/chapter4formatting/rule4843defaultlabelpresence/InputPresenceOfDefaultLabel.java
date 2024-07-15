package com.google.checkstyle.test.chapter4formatting.rule4843defaultlabelpresence;

public class InputPresenceOfDefaultLabel {
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
}

class bad_test {
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

  class inner {
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
