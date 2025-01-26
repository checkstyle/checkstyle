/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="SuppressWithNearbyCommentFilter">
      <property name="commentFormat" value="@cs-\: ([\w\|]+) influence (\d+)"/>
      <property name="checkFormat" value="$1"/>
      <property name="influenceFormat" value="$2"/>
    </module>
    <module name="ClassDataAbstractionCoupling">
      <property name="max" value="1" />
    </module>
    <module name="MagicNumber"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbycommentfilter;
// xdoc section -- start
// @cs-: ClassDataAbstractionCoupling influence 2
// @cs-: MagicNumber influence 4
public class Example8 { // no violations from ClassDataAbstractionCoupling here
  private Example1 foo = new Example1();
  private Example2 bar = new Example2();
  private int value = 10022; // no violations from MagicNumber here
}
// xdoc section -- end
