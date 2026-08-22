/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="InappropriateJavadocBlockTagsOnType"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.inappropriatejavadocblocktagsontype;

// xdoc section - start
/**
 * @return a value
 */
class MyClass1 {
  // violation above 'Invalid '@return' tag for 'MyClass1'.'
}
/**
 * @param <T> a value
 */
class MyClass2<T> {
}

/**
 * @return a value
 */
enum MyEnum1 {
  // violation above 'Invalid '@return' tag for 'MyEnum1'.'
}
/**
 * Valid Javadoc
 */
enum MyEnum2 {
}

/**
 * @return a value
 * @param <T> a value
 */
interface MyInterface1<T> {
  // violation above 'Invalid '@return' tag for 'MyInterface1'.'
}
/**
 * Valid Javadoc
 */
interface MyInterface2 {
}

/**
 * @return a value
 */
record MyRecord1(String name, int age) {
  // violation above 'Invalid '@return' tag for 'MyRecord1'.'
}
/**
 * Valid Javadoc
 */
record MyRecord2(String name, int age) {
}
// xdoc section - end
