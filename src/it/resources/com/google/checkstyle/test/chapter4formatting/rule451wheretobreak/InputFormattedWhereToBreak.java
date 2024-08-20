package com.google.checkstyle.test.chapter4formatting.rule451wheretobreak;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/** some javadoc. */
public class InputFormattedWhereToBreak {

  private List<String> names = Arrays.asList("Alice", "Bob", "Charlie");

  private BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;

  private Supplier<String> getMessage = () -> "Hello, world!";

  private void printNames() {
    names.forEach(name -> System.out.println(name));
  }

  private void runTask() {
    Runnable runTask =
        () -> {
          System.out.println("Starting task");
          System.out.println("Task completed");
        };
  }

  private void executeNestedLambda() {
    Function<Function<Integer, Integer>, Integer> applyFunc = f -> f.apply(5);
    System.out.println(applyFunc.apply(x -> x * x));
  }

  private void sumOfSquares() {
    List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
    int sum = numbers.stream().filter(n -> n % 2 == 1).mapToInt(n -> n * n).sum();
  }

  private void showComplexNestedLambda() {
    // terminology: () - parentheses, [] - brackets, {} - braces
    // Code below is definitely "unbraced expression" and it is single (but not a single-line),
    // fact that is parentheses-ed is still definition of single.
    // Multiple expressions will imply curly braces and `;`.
    // so case below is ok
    Function<Integer, Function<Integer, Integer>> createAdder = x -> (y -> x + y);
  }

  private void foo() {
    BiFunction<String, Long, Long> r =
        (String label, Long value) -> {
          return value;
        };

    java.util.function.Predicate<String> predicate =
        str -> {
          return str.isEmpty();
        };

    Function<String, BiFunction<String, String, String>> s =
        (String label) -> {
          return (a, b) -> {
            return a + " " + b;
          };
        };
  }
}

// violation below 'Top-level class ExtraInputMethodParamPad has to reside in its own source file.'
class ExtraInputMethodParamPad {
  class Inner {
    void testGenerics1() {
      Comparable<String> c = new String();
      Map<String, String> map = new HashMap<String, String>();
      boolean flag = false;

      int init = 9;
    }
  }

  Inner anon =
      new Inner() {
        void testGenerics1() {
          Comparable<String> c = new String();
          Map<String, String> map = new HashMap<String, String>();
          boolean flag = false;
          int init = 9;
        }
      };
}

// violation below 'Top-level class ExtraAsInput1 has to reside in its own source file.'
class ExtraAsInput1 {
  int abc = 0;
  String string = "string";
  double pi = 3.1415;
}

// violation below 'Top-level class ExtraTernary2 has to reside in its own source file.'
class ExtraTernary2 {
  void foo() {
    boolean flag = true;
    int i2 = flag == true ? 1 : 2;
    int i3 = flag == true ? 1 : 2;
  }
}

// violation below 'Top-level class ExtraAssignClass3 has to reside in its own source file.'
class ExtraAssignClass3 {
  void foo() {
    int i = 0;
    int j = 0;
    i += 1;
    j += 2;
    i -= 1;
    j -= 2;
    i /= 1;
    j /= 2;
    i *= 1;
    j *= 2;
    i %= 1;
    j %= 2;
    i ^= 1;
    j ^= 2;
    i |= 1;
    j |= 2;
    i &= 1;
    j &= 2;
    i >>= 1;
    j >>= 2;
    i >>>= 1;
    j >>>= 2;
    i <<= 1;
    j <<= 2;
  }

  class InnerClass {
    void foo() {
      int i = 0;
      int j = 0;
      i += 1;
      j += 2;
      i -= 1;
      j -= 2;
      i /= 1;
      j /= 2;
      i *= 1;
      j *= 2;
      i %= 1;
      j %= 2;
      i ^= 1;
      j ^= 2;
      i |= 1;
      j |= 2;
      i &= 1;
      j &= 2;
      i >>= 1;
      j >>= 2;
      i >>>= 1;
      j >>>= 2;
      i <<= 1;
      j <<= 2;
    }
  }

  InnerClass anon =
      new InnerClass() {
        void foo() {
          int i = 0;
          int j = 0;
          i += 1;
          j += 2;
          i -= 1;
          j -= 2;
          i /= 1;
          j /= 2;
          i *= 1;
          j *= 2;
          i %= 1;
          j %= 2;
          i ^= 1;
          j ^= 2;
          i |= 1;
          j |= 2;
          i &= 1;
          j &= 2;
          i >>= 1;
          j >>= 2;
          i >>>= 1;
          j >>>= 2;
          i <<= 1;
          j <<= 2;
        }
      };

  enum TestEnum {
    FIRST() {},

    SECOND() {}
  }
}

// violation below 'Top-level class ExtraInputOperatorWrap has to reside in its own source file.'
class ExtraInputOperatorWrap {
  void test() {
    int x = 1 + 2 - 3 - 4;
    x = x + 2;
    boolean y = true && false;
    y = true && false;
    y = false && true;
    /* Note: The three tests below will be used when issue #3381 is closed */
    Arrays.sort(null, String::compareToIgnoreCase);
    Arrays.sort(null, String::compareToIgnoreCase);
    Arrays.sort(null, String::compareToIgnoreCase);
  }

  void testAssignment() {
    int x = 0;
    int y = 0;
  }

  <T extends Comparable & java.io.Serializable> void testGenerics1() {
    Comparable<String> c = new String();
    Map<String, String> map = new HashMap<String, String>();

    boolean flag = false;

    int init = 9;

    for (Map.Entry<String, String> entry : map.entrySet()) {
      int i = flag == true ? 1 : 2;
    }

    if (init != 9) {
      /* ignore */
    }

    while (init == 10) {}

    if (init > 10) {
      /* ignore */
    }

    while (init < 10 || !flag) {}
  }

  class Inner {
    void testGenerics1() {
      Comparable<String> c = new String();
      Map<String, String> map = new HashMap<String, String>();
      boolean flag = false;

      int init = 9;

      for (Map.Entry<String, String> entry : map.entrySet()) {
        int i = flag == true ? 1 : 2;
      }

      if (init != 9) {
        /* ignore */
      }

      while (init == 10) {}

      if (init > 10) {
        /* ignore */
      }

      while (init < 10 || !flag) {}
    }
  }

  Inner anon =
      new Inner() {
        void testGenerics1() {
          Comparable<String> c = new String();
          Map<String, String> map = new HashMap<String, String>();
          boolean flag = false;
          int init = 9;

          for (Map.Entry<String, String> entry : map.entrySet()) {
            int i = flag == true ? 1 : 2;
          }

          if (init != 9) {
            /* ignore */
          }

          while (init == 10) {}

          if (init > 10) {
            /* ignore */
          }

          while (init < 10 || !flag) {}
        }
      };

  class AsInput {
    int abc = 0;
    String string = "string";
    double pi = 3.1415;
  }

  class Ternary {
    void foo() {
      boolean flag = true;
      int i = flag == true ? 1 : 2;
      int i2 = flag == true ? 1 : 2;
      int i3 = flag == true ? 1 : 2;
    }
  }

  class AssignClass {
    void foo() {
      int i = 0;
      int j = 0;
      i += 1;
      j += 2;
      i -= 1;
      j -= 2;
      i /= 1;
      j /= 2;
      i *= 1;
      j *= 2;
      i %= 1;
      j %= 2;
      i ^= 1;
      j ^= 2;
      i |= 1;
      j |= 2;
      i &= 1;
      j &= 2;
      i >>= 1;
      j >>= 2;
      i >>>= 1;
      j >>>= 2;
      i <<= 1;
      j <<= 2;
    }

    class InnerClass {
      void foo() {
        int i = 0;
        int j = 0;
        i += 1;
        j += 2;
        i -= 1;
        j -= 2;
        i /= 1;
        j /= 2;
        i *= 1;
        j *= 2;
        i %= 1;
        j %= 2;
        i ^= 1;
        j ^= 2;
        i |= 1;
        j |= 2;
        i &= 1;
        j &= 2;
        i >>= 1;
        j >>= 2;
        i >>>= 1;
        j >>>= 2;
        i <<= 1;
        j <<= 2;
      }
    }

    InnerClass anon =
        new InnerClass() {
          void foo() {
            int i = 0;
            int j = 0;
            i += 1;
            j += 2;
            i -= 1;
            j -= 2;
            i /= 1;
            j /= 2;
            i *= 1;
            j *= 2;
            i %= 1;
            j %= 2;
            i ^= 1;
            j ^= 2;
            i |= 1;
            j |= 2;
            i &= 1;
            j &= 2;
            i >>= 1;
            j >>= 2;
            i >>>= 1;
            j >>>= 2;
            i <<= 1;
            j <<= 2;
          }
        };

    <T extends Comparable & java.io.Serializable> void testWrapBeforeOperator() {}
  }
}

// violation below 'Top-level class ExtraInputSeparatorWrap has to reside in its own source file.'
class ExtraInputSeparatorWrap {
  /** Some javadoc. */
  public void goodCase() {
    int i = 0;
    String s = "ffffooooString";
    s.isEmpty(); // ok
    s.isEmpty();

    foo(i, s); // ok
  }

  public static void foo(int i, String s) {}
}

// violation below 'Top-level class ExtraBadCase has to reside in its own source file.'
class ExtraBadCase {

  public void goodCase(int... foo) {
    int i = 0;

    String s = "ffffooooString";
    boolean b = s.isEmpty();
    foo(i, s);
    int[] j;
  }

  public static String foo(int i, String s) {
    String maxLength = "123";
    int truncationLength = 1;
    CharSequence seq = null;
    Object truncationIndicator = null;
    return new StringBuilder(maxLength)
        .append(seq, 0, truncationLength)
        .append(truncationIndicator)
        .toString();
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputSeparatorWrapArrayDeclarator {

  protected int[] arrayDeclarationWithGoodWrapping = new int[] {1, 2};

  protected int[] arrayDeclarationWithBadWrapping = new int[] {1, 2};
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputSeparatorWrapComma {
  /** Some javadoc. */
  public void goodCase() {
    int i = 0;
    String s = "ffffooooString";
    s.isEmpty(); // ok
    s.isEmpty();

    foo(i, s); // ok
  }

  public static void foo(int i, String s) {}
}

// violation below 'Top-level class ExtraBadCaseComma has to reside in its own source file.'
class ExtraBadCaseComma {

  /** Some javadoc. */
  public void goodCase(int... foo) {
    int i = 0;

    String s = "ffffooooString";
    boolean b = s.isEmpty();
    foo(i, s);
    int[] j;
  }

  /** Some javadoc. */
  public static String foo(int i, String s) {
    String maxLength = "123";
    int truncationLength = 1;
    CharSequence seq = null;
    Object truncationIndicator = null;
    return new StringBuilder(maxLength)
        .append(seq, 0, truncationLength)
        .append(truncationIndicator)
        .toString();
  }
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputSeparatorWrapEllipsis {

  public void testMethodWithGoodWrapping(String... parameters) {}

  public void testMethodWithBadWrapping(String... parameters) {}
}

// violation below 'Top-level class .* has to reside in its own source file.'
class ExtraInputSeparatorWrapMethodRef {

  void goodCase() {
    String[] stringArray = {
      "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda"
    };
    Arrays.sort(stringArray, String::compareToIgnoreCase);
  }

  void badCase() {
    String[] stringArray = {
      "Barbara", "James", "Mary", "John", "Patricia", "Robert", "Michael", "Linda"
    };
    Arrays.sort(stringArray, String::compareToIgnoreCase);
  }
}
