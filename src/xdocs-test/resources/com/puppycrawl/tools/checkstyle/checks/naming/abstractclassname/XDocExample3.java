/*
AbstractClassName
format = (default)^Abstract.+$
ignoreModifier = (default)false
ignoreName = true


*/

package com.puppycrawl.tools.checkstyle.checks.naming.abstractclassname;

abstract class AbstractNinethClass {} // OK
abstract class TenthClass {} // OK
class AbstractEleventhClass {} // violation
class TwelvethClass {} // OK
