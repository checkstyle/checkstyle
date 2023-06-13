/*
EmptyForInitializerPad


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptyforinitializerpad;

class Example1 {
  int i = 0;
  void example() {
    for ( ; i < 1; i++ );  // violation '';' is preceded with whitespace'
    for (; i < 2; i++ );
    for (;i<2;i++);
    for ( ;i<2;i++);       // violation '';' is preceded with whitespace'
    for (
          ; i < 2; i++ );
    }
}
