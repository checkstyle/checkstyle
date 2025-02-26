/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalType">
      <property name="illegalClassNames" value="Boolean, Foo"/>
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
public class Example6 extends TreeSet {

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
  // violation below 'Usage of type 'Boolean' is not allowed'
  public <T extends Boolean, U extends Serializable> void typeParam(T a) {}

  public <T extends java.util.Optional> void method(T t) {
    Optional<T> i;
  }

  abstract class A {
    // violation below 'Usage of type 'Boolean' is not allowed'
    public abstract Set<Boolean> shortName(Set<? super Boolean> a);
    // violation above 'Usage of type 'Boolean' is not allowed'
  }

  class B extends Gitter {}
  class C extends Github {}

  public Optional<String> field2;
  protected String field3;
  Optional<String> field4;
  // violation below 'Usage of type 'Foo' is not allowed'
  private void method(List<Foo> list, Boolean value) {}
  // violation above 'Usage of type 'Boolean' is not allowed'
  void foo() {
    Optional<String> i;
  }
  // violation below 'Usage of type 'Foo' is not allowed'
  final Consumer<Foo> consumer = Foo<Boolean>::foo;
  // violation above 'Usage of type 'Boolean' is not allowed'

  public void var() {
    var message = "Hello, World!";
  }
}
// xdoc section -- end

