////////////////////////////////////////////////////////////////////////////////
// Input test file for testing empty LITERAL_CASE.
// Created: 2017
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.blocks.emptyblock;

class InputEmptyBlockCase
{
    void method1(int a) {
        switch (a) {}
        switch (a) {case 1: ; } // no violation
        switch (a) {case 1:{}}  // 1 violation
        switch (a) {
            case 1:
        }
        switch (a) {
            case 1: // 1 violation
            {}
        }
        switch (a) {
            case 1: // 1 violation if checking statements
            {// none if checking text
            }
        }
    }

    public void method2(char c) {
        switch(c) { case 0: } // no violation
        switch(c) { case 0: {} method1(1); } // 1 violation
        switch(c) { case 0: method1(0); {} } // no violation
        switch(c) { case 0: case 1: {} } // 1 violation
        switch(c) { case 0: {} case 1: {// 2 violations if checking statements
        }
        }
    }
}
