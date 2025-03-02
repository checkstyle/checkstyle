/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalType">
      <property name="illegalClassNames" value="java.util.Optional"/>
      <property name="tokens" value="VARIABLE_DEF"/>
      <property name="id" value="IllegalTypeOptionalAsField"/>
    </module>
    <module name="SuppressionXpathSingleFilter">
      <property name="query" value="//METHOD_DEF//*"/>
      <property name="id" value="IllegalTypeOptionalAsField"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.TreeSet;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Set;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.List;

// xdoc section -- start
public class Example7 extends TreeSet {

  public <T extends java.util.HashSet> void method() {

    LinkedHashMap<Integer, String> lhmap = new LinkedHashMap<Integer, String>();

    TreeMap<Integer, String> treemap = new TreeMap<Integer, String>();
    java.lang.IllegalArgumentException illegalex;

    java.util.TreeSet treeset;
  }

  public <T extends java.util.HashSet> void typeParam(T t) {}

  public HashMap<String, String> function1() {
    return null;
  }

  private HashMap<String, String> function2() {
    return null;
  }

  protected HashMap<Integer, String> function3() {
    return null;
  }

  public <T extends Boolean, U extends Serializable> void typeParam(T a) {}

  public <T extends java.util.Optional> void method(T t) {
    Optional<T> i;
  }

  abstract class A {

    public abstract Set<Boolean> shortName(Set<? super Boolean> a);

  }

  class B extends Gitter {}
  class C extends Github {}
  // violation below 'Usage of type 'Optional' is not allowed'
  public Optional<String> field2;
  protected String field3;
  Optional<String> field4; // violation, 'Usage of type 'Optional' is not allowed'

  private void method(List<Foo> list, Boolean value) {}

  void foo() {
    Optional<String> i;
  }

  final Consumer<Foo> consumer = Foo<Boolean>::foo;

  public void var() {
    var message = "Hello, World!";
  }
}
// xdoc section -- end
