// non-compiled with javac: Compilable with Java17

package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** some javadoc. */
public class InputPatternVariableNameEnhancedInstanceofTestDefault {
  private Object obj;

  static boolean doStuff(Object obj) {
    return obj instanceof Integer OTHER && OTHER > 0;
    // 2 violations above:
    //  'Abbreviation in name 'OTHER' must contain no more than '1' consecutive capital letters.'
    //  'Pattern variable name 'OTHER' must match pattern'
  }

  static {
    Object o = "";
    if (o instanceof String s) {
      System.out.println(s.toLowerCase(Locale.forLanguageTag(s)));
      boolean stringCheck = "test".equals(s);
    }

    if (o instanceof Integer Count) {
      // violation above 'Pattern variable name 'Count' must match pattern'
      int value = Count.byteValue();
      if (Count.equals(value)) {
        value = 25;
      }
    }
  }

  interface VoidPredicate {
    public boolean get();
  }

  /** some javadoc. */
  public void testing(Object o1, Object o2) {
    Object b;
    Object c;
    if (!(o1 instanceof String aA)
            // violation above 'Pattern variable name 'aA' must match pattern'
            && (o2 instanceof String a1_a)) {
      // violation above 'Pattern variable name 'a1_a' must match pattern'
    }

    if (o1 instanceof String A_A
            // violation above 'Pattern variable name 'A_A' must match pattern'
            || !(o2 instanceof String aa2_a)) {
      // violation above 'Pattern variable name 'aa2_a' must match pattern'
    }
    b = ((VoidPredicate) () -> o1 instanceof String s).get();

    List<Integer> arrayList = new ArrayList<Integer>();
    if (arrayList instanceof ArrayList<Integer> ai) {
      System.out.println("Blah");
    }

    boolean result =
            (o1 instanceof String a) ? (o1 instanceof String x) : (!(o1 instanceof String y));

    // violation below 'Pattern variable name '_a' must match pattern'
    if (!(o1 instanceof Integer _a)
            ?
            false
            : _a > 0) {
      System.out.println("done");
    }

    {
      while (!(o1 instanceof String _aa)) {
        // violation above 'Pattern variable name '_aa' must match pattern'
        L3:
        break L3;
      }
      while (o1 instanceof String aa_) {
        // violation above 'Pattern variable name 'aa_' must match pattern'
        aa_.length();
      }
    }

    int x = o1 instanceof String aaa$aaa ? aaa$aaa.length() : 2;
    // violation above 'Pattern variable name .* must match pattern'
    x = !(o1 instanceof String $aaaaaa) ? 2 : $aaaaaa.length();
    // violation above 'Pattern variable name .* must match pattern'
    for (; o1 instanceof String aaaaaa$; aaaaaa$.length()) {
      // violation above 'Pattern variable name .* must match pattern'
      System.out.println(aaaaaa$);
    }

    do {
      L4:
      break L4;
    } while (!(o1 instanceof String _A_aa_B));
    // violation above 'Pattern variable name '_A_aa_B' must match pattern'
  }
}
