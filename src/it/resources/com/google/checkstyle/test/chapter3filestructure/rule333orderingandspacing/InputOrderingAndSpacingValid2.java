package com.google.checkstyle.test.chapter3filestructure.rule333orderingandspacing;

import static com.puppycrawl.tools.checkstyle.utils.AnnotationUtil.containsAnnotation;
import static com.puppycrawl.tools.checkstyle.utils.AnnotationUtil.getAnnotation;
// comments

// comments
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck;
import com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheck;
import com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck;
import java.util.Arrays;
// comments
import java.util.BitSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import javax.accessibility.Accessible;
import org.apache.commons.beanutils.converters.ArrayConverter;

/** Some javadoc. */
public class InputOrderingAndSpacingValid2 {
  /** Some javadoc. */
  public static void main(String[] args) {
    // Use of static imports
    boolean hasAnnotation = containsAnnotation((DetailAST) new Object(), "Override");
    Object annotation = getAnnotation((DetailAST) new Object(), "Override");

    // comments

    // Use of com.puppycrawl.tools.checkstyle classes
    FinalClassCheck finalClassCheck = new FinalClassCheck();
    ThrowsCountCheck throwsCountCheck = new ThrowsCountCheck();
    VisibilityModifierCheck visibilityModifierCheck = new VisibilityModifierCheck();

    // Use of java.util classes
    int[] numbers = {1, 2, 3};
    Arrays.sort(numbers);
    BitSet bitSet = new BitSet();
    Map<String, String> map;
    Entry<String, String> entry = Map.entry("key", "value");
    try {
      throw new NoSuchElementException();
    } catch (NoSuchElementException e) {
      e.printStackTrace();
    }

    // comments

    // Use of javax.accessibility classes
    Accessible accessible;

    // Use of org.apache.commons.beanutils.converters classes
    ArrayConverter arrayConverter = new ArrayConverter(int[].class, null);
  }
}
