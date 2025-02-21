/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="IllegalType">
      <property name="validateAbstractClassNames" value="true"/>
      <property name="illegalAbstractClassNameFormat" value="Gitt"/>
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
// violation below 'Usage of type 'TreeSet' is not allowed'
public class Example4 extends TreeSet {
  // violation below 'Usage of type 'java.util.HashSet' is not allowed'
  public <T extends java.util.HashSet> void method() {
    // violation below 'Usage of type 'LinkedHashMap' is not allowed'
    LinkedHashMap<Integer, String> lhmap = new LinkedHashMap<Integer, String>();
    // violation below 'Usage of type 'TreeMap' is not allowed'
    TreeMap<Integer, String> treemap = new TreeMap<Integer, String>();
    java.lang.IllegalArgumentException illegalex;
    // violation below 'Usage of type 'java.util.TreeSet' is not allowed'
    java.util.TreeSet treeset;
  }
  // violation below 'Usage of type 'java.util.HashSet' is not allowed'
  public <T extends java.util.HashSet> void typeParam(T t) {}
  // violation below 'Usage of type 'HashMap' is not allowed'
  public HashMap<String, String> function1() {
    return null;
  }
  // violation below 'Usage of type 'HashMap' is not allowed'
  private HashMap<String, String> function2() {
    return null;
  }
  // violation below 'Usage of type 'HashMap' is not allowed'
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
  // violation below 'Usage of type 'Gitter' is not allowed'
  class B extends Gitter {}
  class C extends Github {}

  public Optional<String> field2;
  protected String field3;
  Optional<String> field4;

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
