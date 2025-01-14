/*xml
<module name="Checker">
  <property name="fileExtensions" value="xml"/>

  <module name="RegexpSingleline">
    <property name="format"
        value="param\s+name=&quot;type&quot;\s+value=&quot;code&quot;"/>
    <property name="message"
        value="Type code is not allowed. Use type raw instead."/>
  </module>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat" value="CSOFF\: ALMOST_ALL"/>
    <property name="onCommentFormat" value="CSON\: ALMOST_ALL"/>
    <property name="checkFormat" value="^((?!(RegexpSinglelineCheck)).)*$"/>
  </module>

</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithplaintextcommentfilter;

// xdoc section -- start
public class Example5 {
  // CHECKSTYLE_OFF: ALMOST_ALL
  public static final int MAX_ITEMS = 100;
  private String[] stringArray;
  // CHECKSTYLE_ON: ALMOST_ALL
  private int[] intArray;
}
// xdoc section -- end
