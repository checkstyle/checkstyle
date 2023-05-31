/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = true
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class XDocExample2 {
    abstract class AbstractFirstClass {} // OK
    abstract class SecondClass {} // violation '.* must match pattern '\^Abstract\.\+\$''
    class AbstractThirdClass {} // OK
    class FourthClass {} // OK
}
