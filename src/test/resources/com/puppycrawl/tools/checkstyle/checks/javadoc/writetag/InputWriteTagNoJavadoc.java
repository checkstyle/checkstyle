/*
WriteTag
tag = (default)null
tagFormat = (default)null
tagSeverity = (default)info
tokens = (default)INTERFACE_DEF, CLASS_DEF, ENUM_DEF, ANNOTATION_DEF, RECORD_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;
// violation 1 lines below , 'Javadoc is missing.*'
class InputWriteTagNoJavadoc // violation
{
    public void method()
    {
    }
}
