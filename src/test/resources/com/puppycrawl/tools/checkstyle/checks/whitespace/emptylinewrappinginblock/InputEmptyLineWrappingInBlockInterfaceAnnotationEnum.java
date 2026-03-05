/*
EmptyLineWrappingInBlock
tokens = (default)CLASS_DEF, INTERFACE_DEF, ANNOTATION_DEF, ENUM_DEF, ENUM_CONSTANT_DEF
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public interface InputEmptyLineWrappingInBlockInterfaceAnnotationEnum { // violation ''{' must have exactly one empty line after.'
    int getValue();
} // violation ''}' must have exactly one empty line before'

public @interface InputEmptyLineWrappingInBlockInterfaceAnnotationEnumAnnot { // violation ''{' must have exactly one empty line after.'
    String value();
} // violation ''}' must have exactly one empty line before'

public enum InputEmptyLineWrappingInBlockInterfaceAnnotationEnumEnum { // violation ''{' must have exactly one empty line after.'
    A,
    B
} // violation ''}' must have exactly one empty line before'
