/*
RightCurly
option = (default)same
tokens = LITERAL_DO, LITERAL_FOR, LITERAL_WHILE, STATIC_INIT, INSTANCE_INIT, \
         CLASS_DEF, METHOD_DEF, CTOR_DEF, ANNOTATION_DEF, ENUM_DEF, INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

/*
 * @see https://github.com/checkstyle/checkstyle/issues/1416
 * @author <a href="mailto:piotr.listkiewicz@gmail.com">liscju</a>
 */
public class InputRightCurlyTestSameOmitOneLiners {
    public static void main(String[] args) {
        boolean after = false;
        try {
        } finally { after = true; } // ok
    }
}
