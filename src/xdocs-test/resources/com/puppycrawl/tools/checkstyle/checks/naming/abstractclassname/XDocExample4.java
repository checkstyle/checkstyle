/*
AbstractClassName
format = ^Generator.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

class XDocExample4 {
    abstract class GeneratorFirstClass {} // OK
    abstract class SecondClass {} // violation '.* must match pattern '\^Generator\.\+\$''
    class GeneratorThirdClass {} // violation '.* must be declared as 'abstract''
    class FourthClass {} // OK
}
