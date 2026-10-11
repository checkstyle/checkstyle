/*
EmptyLineWrappingInBlock
tokens = (default)CLASS_DEF, INTERFACE_DEF, ANNOTATION_DEF, ENUM_DEF, ENUM_CONSTANT_DEF
topSeparator = (default)empty_line
bottomSeparator = (default)empty_line

*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylinewrappinginblock;

public interface InputEmptyLineWrappingInBlockInterfaceAnnotationEnum {
    int getValue(); // violation above ''{' must have exactly one empty line after.'
} // violation ''}' must have exactly one empty line before'

@interface InputEmptyLineWrappingInBlockInterfaceAnnotationEnumAnnot {
    String value(); // violation above ''{' must have exactly one empty line after.'
} // violation ''}' must have exactly one empty line before'

enum InputEmptyLineWrappingInBlockInterfaceAnnotationEnumEnum {
    A, // violation above ''{' must have exactly one empty line after.'
    B
} // violation ''}' must have exactly one empty line before'
