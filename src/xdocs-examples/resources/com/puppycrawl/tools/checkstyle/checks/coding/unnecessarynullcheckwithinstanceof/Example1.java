/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="UnnecessaryNullCheckWithInstanceOf"/>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.coding.unnecessarynullcheckwithinstanceof;

import java.util.ArrayList;
import java.util.List;

// xdoc section -- start
public class Example1 {

  public void methodWithUnnecessaryNullCheck1(Object obj) {
    // violation below, 'Unnecessary nullity check'
    if (obj != null && obj instanceof String) {
      String str = (String) obj;
    }
    // violation below, 'Unnecessary nullity check'
    boolean isValid = obj != null && obj instanceof String;
  }

  interface Validator {
    boolean validate(Object obj);
  }

  public void anonymousClassImplementation() {
    Validator v = new Validator() {
      @Override
      public boolean validate(Object obj) {
        // violation below, 'Unnecessary nullity check'
        return obj != null && obj instanceof String;
      }
    };
  }

  private final List<Object> objects = new ArrayList<>();
  public void simpleLambdas() {
    objects.forEach(obj -> {
      // violation below, 'Unnecessary nullity check'
      if (obj != null && obj instanceof String) {
        String str = (String) obj;
      }
    });
  }

  public String basicTernary(Object obj) {
    // violation below, 'Unnecessary nullity check'
    return obj != null && obj instanceof String ? ((String) obj) : "";
  }

  public void methodWithValidNullCheck(Object obj) {
    if (obj != null) {
      CharSequence cs = (CharSequence) obj;
    }
  }
}
// xdoc section -- end
