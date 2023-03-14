/*
EmptyBlock
option = (default)STATEMENT
tokens = LITERAL_CASE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

class InputEmptyBlockCase2
{
    void method1(int a) {
        switch (a) {}
        switch (a) {case 1: ; } // ok
        switch (a) {case 1:{}}  // violation 'Must have at least one statement'
        switch (a) {
            case 1:
        }
        switch (a) {
            case 1:
            {}  // violation 'Must have at least one statement'
        }
        switch (a) {
            case 1:
            {   // violation 'Must have at least one statement'
            }
        }
    }

    public void method2(char c) {
        switch(c) { case 0: }   // ok
        switch(c) { case 0: {} method1(1); }    // violation 'Must have at least one statement'
        switch(c) { case 0: method1(0); {} }    // ok
        switch(c) { case 0: case 1: {} }    // violation 'Must have at least one statement'
        switch(c) { case 0: {} case 1: {    // 2 violations
        }
        }
    }
}
