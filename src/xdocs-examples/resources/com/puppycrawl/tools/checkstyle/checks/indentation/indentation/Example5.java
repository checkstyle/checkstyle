/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="Indentation">
      <property name="basicOffset" value="2"/>
    </module>
  </module>
</module>
*/

package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;

// xdoc section -- start
class Example5 {
    int a;             // violation, 'level 4, expected level should be 2'
    boolean x, y;      // violation, 'level 4, expected level should be 2'
    String field = "example"; // violation, 'level 4, expected level should be 2'
    int[] values = {   // violation, 'level 4, expected level should be 2'
        1, 2, 3
    };
    int[] values2 = {  // violation, 'level 4, expected level should be 2'
  10 // violation,'level 2, expected level should be one of the following: 8, 23, 24'
    };

    void method2()     // violation, 'level 4, expected level should be 2'
        throws Exception {
        switch (a) {   // violation, 'level 8, expected level should be 4'
            case 1:    // violation, 'level 12, expected level should be 8'
                break; // violation, 'level 16, expected level should be 10'
            case 2:    // violation, 'level 12, expected level should be 8'
                break; // violation, 'level 16, expected level should be 10'
        }              // violation, 'level 8, expected level should be 4'
    }                  // violation, 'level 4, expected level should be 2'
    void method3(int a,  // violation, 'level 4, expected level should be 2'
                 int b) {
        if (x            // violation, 'level 8, expected level should be 4'
                && y) {
            method3(a, b); // violation, 'level 12, expected level should be 6'
        }                  // violation, 'level 8, expected level should be 4'
    }                      // violation, 'level 4, expected level should be 2'
                           // violation below 'level 4, expected level should be 2'
    void handleValue(String aFooString,
                     int aFooInt) {
        // violation below 'level 8, expected level should be 4'
        boolean cond1,cond2,cond3,cond4,cond5,cond6;
        // violation below 'level 8, expected level should be 4'
        cond1=cond2=cond3=cond4=cond5=cond6=false;
        if (cond1        // violation, 'level 8, expected level should be 4'
                || cond2) { // violation below 'level 12, expected level should be 6'
            field = field.toUpperCase()
                    .concat(" TASK");
        }                // violation, 'level 8, expected level should be 4'
    }                    // violation, 'level 4, expected level should be 2'

    void methodBrace()   // violation, 'level 4, expected level should be 2'
    {                    // violation, 'level 4, expected level should be 2'
    }                    // violation, 'level 4, expected level should be 2'
}
// xdoc section -- end
