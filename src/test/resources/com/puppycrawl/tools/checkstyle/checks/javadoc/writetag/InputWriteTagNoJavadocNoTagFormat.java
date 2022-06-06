/*
WriteTag
tag = (default)null
tagFormat = \S
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

class InputWriteTagNoJavadocNoTagFormat // ok
{
    public void method()
    {
    }

}
