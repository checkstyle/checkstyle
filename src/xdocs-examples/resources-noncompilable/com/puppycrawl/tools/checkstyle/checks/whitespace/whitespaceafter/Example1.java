/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="WhitespaceAfter"/>
  </module>
</module>


*/
//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.io.InputStream;

// xdoc section -- start
class Example1 {
  void example() throws Exception {
    if (true) {
    } else if(false) { // violation 'not followed by whitespace'
    }

    testOne("x", "y");
    testOne("z","o"); // violation 'not followed by whitespace'

    for (int i = 0; i < 10; i++){}
    for(int i = 0; i < 10; i++){} // violation 'not followed by whitespace'

    try (InputStream ignored = System.in) {}
    try(InputStream ignored = System.in) {} // violation 'not followed by whitespace'

    try {} catch (Exception e){}
    try{} catch (Exception e) {} // violation ''try' is not followed by whitespace'

    try {} finally {}
    try {} finally{} // violation 'not followed by whitespace'

    try {} catch (Error e){} finally {}
    try {} catch (Error e){} finally{} // violation 'not followed by whitespace'

    try {} catch (Exception e){}
    try {} catch(Exception e){} // violation 'not followed by whitespace'

    synchronized (this) { }
    synchronized(this) { } // violation 'not followed by whitespace'
  }

  public String testOne(String a, String b) {
    return (a + b);
  }
  public String testTwo(String a, String b) {
    return(a + b); // violation 'not followed by whitespace'
  }

  void switchExample() {
    int a = switch ("hello") {
      case "got":
        yield (1); // OK, followed by whitespace
      case "my":
        yield(3); // violation ''yield' is not followed by whitespace'
      default:
        yield 2;
    };
  }
}
// xdoc section -- end
