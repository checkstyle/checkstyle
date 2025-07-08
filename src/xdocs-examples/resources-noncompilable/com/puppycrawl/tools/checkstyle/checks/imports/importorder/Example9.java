/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ImportOrder">
      <property name="useContainerOrderingForStatic" value="false"/>
      <property name="ordered" value="true"/>
      <property name="option" value="top"/>
      <property name="caseSensitive" value="false"/>
      <property name="sortStaticImportsAlphabetically" value="true"/>
    </module>
  </module>
</module>
*/
// non-compiled with javac: Compilable with java11
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

// xdoc section -- start
import static io.netty.handler.codec.http.HttpConstants.COLON;
import static io.netty.handler.codec.http.HttpHeaders.addHeader;
import static io.netty.handler.codec.http.HttpHeaders.setHeader;
import static io.netty.handler.codec.http.HttpHeaders.Names.DATE;
// violation above 'Wrong order for...'
// xdoc section -- end

public class Example9 { }
