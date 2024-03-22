/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HideUtilityClassConstructor">
      <property name="ignoreAnnotatedBy" value="SkipMe, java.lang.Deprecated" />
    </module>
   </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.design.hideutilityclassconstructor;

// xdoc section -- start
class Example2 { // violation

  public Example2() {

  }

  public static void fun() {

  }
}

@SkipMe
class IgnoredClass1 { // ok, ignored by annotation
  public IgnoredClass1() {

  }

  public static void fun() {

  }
}

@java.lang.Deprecated
class IgnoredClass2 { // ok, ignored by annotation
  public IgnoredClass2() {

  }

  public static void fun() {

  }
}

@interface SkipMe {}
// xdoc section -- end
