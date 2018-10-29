package com.puppycrawl.tools.checkstyle.checks.coding.nestedtrydepth;

public class InputNestedTryDepth
{
    void foo() {
        // nesting == 0
        try {
        } catch (Exception e) {
        }

        // nesting == 1
        try {
            try {
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }

        // nesting == 2
        try {
            try {
                try {
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }

        // nesting == 3
        try {
            try {
                try {
                    try {
                    } catch (Exception e) {
                    }
                } catch (Exception e) {
                }
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }
}
