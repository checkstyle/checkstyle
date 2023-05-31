/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract class AbstractFirstClass {} // OK
abstract class SecondClass {} // violation '.* must match pattern '\^Abstract\.\+\$''
class AbstractThirdClass {} // violation '.* must be declared as 'abstract''
class FourthClass {} // OK
