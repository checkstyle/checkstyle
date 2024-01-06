/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="CatchParameterName">
      <property name="format" value="^[a-z][a-zA-Z0-9]+$"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.naming.catchparametername;

// xdoc section -- start
public class Example2 {
  public void myTest() {
    try {
      throw new InterruptedException();
    } catch (ArithmeticException e) {
      // violation above, 'Name 'e' must match pattern'

    } catch (ArrayIndexOutOfBoundsException ex) {

    } catch (IndexOutOfBoundsException e123) {

    } catch (NullPointerException ab) {

    } catch (ArrayStoreException abc) {

    } catch (InterruptedException aBC) {

    } catch (RuntimeException abC) {

    } catch (Exception EighthException) {
      // violation above, 'Name 'EighthException' must match pattern'

    } catch (Throwable t) {
      // violation above, 'Name 't' must match pattern'

    }
  }
}
// xdoc section -- end
