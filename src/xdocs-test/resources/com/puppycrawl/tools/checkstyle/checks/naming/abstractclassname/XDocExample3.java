/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class XDocExample3 {
    abstract class AbstractFirstClass {} // OK
    abstract class SecondClass {} // OK
    class AbstractThirdClass {} // violation '.* must be declared as 'abstract''
    class FourthClass {} // OK
}
