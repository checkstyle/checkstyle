//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

import java.lang.annotation.Inherited;

/* Config:
 * option = alone
 * tokens = {CLASS_DEF, METHOD_DEF, CTOR_DEF, LITERAL_FOR, LITERAL_WHILE, STATIC_INIT,
 *           INSTANCE_INIT, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF, RECORD_DEF,
 *           COMPACT_CTOR_DEF}
 *
 */
class InputRightCurlyDoubleBrace {{
    @Inherited int vec;
}} // violation
