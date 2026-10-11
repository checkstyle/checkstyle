package com.google.checkstyle.test.chapter4formatting.rule462horizontalwhitespace;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/** Some javadoc. */
public class InputFormattedParenPad {
  boolean fooo = this.bar((true && false) && true);

  String foo() {
    return ((Object) bar((1 > 2) ? ((3 < 4) ? false : true) : ((1 == 1) ? false : true)))
        .toString();
  }

  /** Some javadoc. */
  @MyAnnotation
  public boolean bar(boolean a) {
    assert (true);
    return true;
  }

  class ParenPadNoSpace {
    ParenPadNoSpace() {
      this(0);
    }

    ParenPadNoSpace(int i) {
      super();
    }

    @SuppressWarnings("")
    void method(boolean status) {
      try (Writer writer = new StringWriter()) {
        do {
          writer.append("a");
        } while (status);
      } catch (IOException e) {
        while (status) {
          for (int i = 0; i < (long) (2 * (4 / 2)); i++) {
            if (i > 2) {
              synchronized (this) {
                switch (i) {
                  case 3:
                  case (4):
                  case 5:
                    break;
                  default:
                }
              }
            }
          }
        }
      }
    }
  }

  class ParenPadSpaceLeft {
    ParenPadSpaceLeft() {
      this(0);
    }

    ParenPadSpaceLeft(int i) {
      super();
    }

    @SuppressWarnings("")
    void method(boolean status) {
      try (Writer writer = new StringWriter()) {
        do {
          writer.append("a");
        } while (status);
      } catch (IOException e) {
        while (status) {
          for (int i = 0; i < (long) (2 * (4 / 2)); i++) {
            if (i > 2) {
              synchronized (this) {
                switch (i) {
                  case 3:
                  case (4):
                  case 5:
                    break;
                  default:
                }
              }
            }
          }
        }
      }
    }
  }

  class ParenPadSpaceRight {
    ParenPadSpaceRight() {
      this(0);
    }

    ParenPadSpaceRight(int i) {
      super();
    }

    @SuppressWarnings("")
    void method(boolean status) {
      try (Writer writer = new StringWriter()) {
        do {
          writer.append("a");
        } while (status);
      } catch (IOException e) {
        while (status) {
          for (int i = 0; i < (long) (2 * (4 / 2)); i++) {
            if (i > 2) {
              synchronized (this) {
                switch (i) {
                  case 3:
                  case (4):
                  case 5:
                    break;
                  default:
                }
              }
            }
          }
        }
      }
    }
  }

  enum MyEnum {
    SOME_CONSTANT() {
      final int testing = 2 * (4 / 2);
    };

    private Object exam;

    private static String getterName(Exception t) {
      if (t instanceof ClassNotFoundException) {
        return ((ClassNotFoundException) t).getMessage();
      } else {
        return "?";
      }
    }

    /** Some javadoc. */
    public void myMethod() {
      String s = "test";
      Object o = s;
      ((String) o).length();
      ((String) o).length();
    }

    /** Some javadoc. */
    public void crisRon() {
      Object leo = "messi";
      Object ibra = leo;
      ((String) leo).compareTo((String) ibra);
      Math.random();
    }

    /** Some javadoc. */
    public void intStringConv() {
      Object a = 5;
      Object b = "string";
      int w = Integer.parseInt((String) a);
      int x = Integer.parseInt((String) a);
      double y = Double.parseDouble((String) a);
      float z = Float.parseFloat((String) a);
      String d = ((String) b);
    }

    /** Some javadoc. */
    public int something(Object o) {
      if (o == null || !(o instanceof Float)) {
        return -1;
      }
      return Integer.valueOf(22).compareTo((Integer) o);
    }

    private void launch(Integer number) {
      String myInt = (number.toString() + '\0');
      boolean result = number == 123;
    }

    /** Some javadoc. */
    public String testing() {
      return (this.exam != null) ? ((Enum) this.exam).name() : null;
    }

    Object stringReturnValue(Object result) {
      if (result instanceof String) {
        result = ((String) result).length();
      }
      return result;
    }

    private void except() {
      java.util.ArrayList<Integer> arrlist = new java.util.ArrayList<Integer>(5);
      arrlist.add(20);
      arrlist.add(15);
      arrlist.add(30);
      arrlist.add(45);
      try {
        (arrlist).remove(2);
      } catch (IndexOutOfBoundsException x) {
        x.getMessage();
      }
      org.junit.Assert.assertThat("123", org.hamcrest.CoreMatchers.is("123"));
      org.junit.Assert.assertThat("Help! Integers don't work", 0, org.hamcrest.CoreMatchers.is(1));
    }

    private void tryWithResources() throws Exception {
      try (AutoCloseable a = null) {
        /* foo */
      }
      try (AutoCloseable a = null;
          AutoCloseable b = null) {
        /* foo */
      }
      try (AutoCloseable a = null;
          AutoCloseable b = null) {
        /* foo */
      }
      try (AutoCloseable a = null;
          AutoCloseable b = null) {
        /* foo */
      }
      try (AutoCloseable a = null) {
        /* foo */
      }
      try (AutoCloseable a = null;
          AutoCloseable b = null) {
        /* foo */
      }
    }
  }

  @interface MyAnnotation {
    String someField() default "Hello world";
  }
}
