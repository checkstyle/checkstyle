/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="OpenjdkLineWrapping"/>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.openjdklinewrapping;

// xdoc section - start
class Example1 {
    int sum(int first, int second) {
        int fixed = first
                + second;
        int expression = first
                                 + second;
        int incorrect = first
                     + second; // violation 'incorrect indentation level 21'
        return sum(first,
                   second);
    }
}
// xdoc section - end
