/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CatchParameterName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.catchparametername;

// xdoc section -- start
public class Example1 {
  public void myTest() {
    try {
      // ...
      throw new InterruptedException();
    } catch (ArithmeticException e) { // OK
      // ...
    } catch (ArrayIndexOutOfBoundsException ex) { // OK
      // ...
    } catch (IndexOutOfBoundsException e123) { // violation, 'Name 'e123' must match pattern'
      // not allowed
      // ...
    } catch (NullPointerException ab) { // violation, 'Name 'ab' must match pattern'
      // ...
    } catch (ArrayStoreException abc) { // OK
      // ...
    } catch (InterruptedException aBC) { // violation, 'Name 'aBC' must match pattern'
      // should be in lowercase
      // ...
    } catch (RuntimeException abC) { // OK
      // ...
    } catch (Exception abCD) { // OK
      // ...
    } catch (Throwable t) { // OK
      // ...
    }
  }
}
// xdoc section -- end
