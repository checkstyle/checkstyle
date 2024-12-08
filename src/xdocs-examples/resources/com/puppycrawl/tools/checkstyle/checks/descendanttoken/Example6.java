/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="DescendantToken">
      <property name="tokens" value="LITERAL_FINALLY,LITERAL_CATCH"/>
      <property name="limitedTokens" value="LITERAL_RETURN"/>
      <property name="maximumNumber" value="0"/>
    </module>
  </module>
</module>
*/
package com.puppycrawl.tools.checkstyle.checks.descendanttoken;

// xdoc section -- start
class Example6 {
  void testMethod1() {
    try {}
    catch (Exception e) { // violation, 'Count of 1 for 'LITERAL_CATCH' descendant'
      System.out.println("xyz");
      return;
    }
    finally {
      System.out.println("xyz");
    }
  }

  void testMethod2() {
    try {}
    catch (Exception e) { // violation, 'Count of 1 for 'LITERAL_CATCH' descendant'
      System.out.println("xyz");
      return;
    }
    finally {
      System.out.println("xyz");
    }
    try {}
    catch (Exception e) {
      try {}
      catch (Exception ex) {
        // handle exception
      }
    }
    finally {
      try {}
      catch (Exception e) {
        // handle exception
      }
    }
  }
}
// xdoc section -- end
