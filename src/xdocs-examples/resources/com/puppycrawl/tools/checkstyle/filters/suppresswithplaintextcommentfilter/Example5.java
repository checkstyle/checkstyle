/*xml
<module name="Checker">
  <property name="fileExtensions" value="java"/>

  <module name="SuppressWithPlainTextCommentFilter">
    <property name="offCommentFormat"
      value="CHECKSTYLE_OFF: ALMOST_ALL"/>
    <property name="onCommentFormat"
      value="CHECKSTYLE_ON: ALMOST_ALL"/>
    <property name="checkFormat"
      value="^((?!(FileTabCharacterCheck)).)*$"/>
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
