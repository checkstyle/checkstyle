/*
EmptyBlock
option = text
tokens = LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

public class InputEmptyBlockTestUppercaseOptionProperty {

    void method1(int a) {
        switch (a) {}
        switch (a) {default: ; }
        switch (a) {default: {}}    // violation 'Empty default block'
        switch (a) {
            default:
        }
        switch (a) {
            default:
            {}  // violation 'Empty default block'
        }
        switch (a) {
            default:
            {   // ok as the block contains a comment
            }
        }
    }

}
