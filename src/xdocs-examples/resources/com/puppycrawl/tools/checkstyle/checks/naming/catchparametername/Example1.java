/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CatchParameterName"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.catchparametername;

// xdoc section - start
public class Example1 {
  public void myTest() {
    try {
      throw new InterruptedException();
    } catch (ArithmeticException e) {

    } catch (ArrayIndexOutOfBoundsException ex) {
      // violation above 'Name 'ex' must match pattern'
    } catch (IndexOutOfBoundsException e123) {
      // violation above 'Name 'e123' must match pattern'
    } catch (NullPointerException ab) {
      // violation above 'Name 'ab' must match pattern'
    } catch (ArrayStoreException abc) {
    } catch (InterruptedException aBC) {
      // violation above 'Name 'aBC' must match pattern'
    } catch (RuntimeException abC) {
    } catch (Exception EighthException) {
      // violation above 'Name 'EighthException' must match pattern'
    } catch (Throwable t) {

    }
  }
}
// xdoc section - end
