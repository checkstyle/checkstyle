/*
TypeBodyPadding
starting = (default)true
ending = (default)true
allowEmpty = false
skipLocal = (default)true
skipInner = (default)true
tokens = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, RECORD_DEF, ANNOTATION_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.typebodypadding;

// violation below 'Blank line required after the opening brace of type definition.'
class InputTypeBodyPaddingAllowEmpty {}
// violation above 'Blank line required before the closing brace of type definition.'

class Empty2 { // violation 'Blank line required after the opening brace of type definition.'
} // violation 'Blank line required before the closing brace of type definition.'

class Empty3
{} // violation 'Blank line required after the opening brace of type definition.'
// violation above 'Blank line required before the closing brace of type definition.'

class Empty4
{ // violation 'Blank line required after the opening brace of type definition.'
} // violation 'Blank line required before the closing brace of type definition.'

class WithBlankLineOnly {

}

class Allowed1 {

    private int a;

}

// 2 violations 3 lines below:
//                    'Blank line required after the opening brace of type definition.'
//                    'Blank line required before the closing brace of type definition.'
class InputTypeBodyPaddingAllowEmptySameLineWithPaddingOutside {}
