/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = true
ignoreName = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract class AbstractFifthClass {} // OK
abstract class SixthClass {} // violation '.* must match pattern '\^Abstract\.\+\$''
class AbstractSeventhClass {} // OK
class EighthClass {} // OK
