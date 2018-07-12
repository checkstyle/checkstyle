package com.puppycrawl.tools.checkstyle.checks.blocks.leftcurly;
/**
  * This test-input is intended to be checked using following configuration:
  *
  * option = EOL
  *
  */
public class InputLeftCurlyEolSwitch {

    public void doStuff() {
        int x = 1;
        switch (x) {
            case 0:
            { // warn
                break;
            }
            case (1+0):
            { // warn
                break;
            }
            case 2: {
                break;
            }
            default:
            { // warn
                break;
            }
            case 3:
            case 4:
                x++;
                { // OK, standalone block
                }
                break;
            case 5: {
                }
                break;
            case (5
                +1):
            { // warn
                break;
            }
            case 7
                :
            { // warn
                break;
            }
        }
        switch (x) {
            case 0: {
                break;
            }
            default:
                // do nothing
        }
    }

    public @interface SomeAnnotation {

        String value() default "";

    }

    public interface SomeInterface {

        default String method() {
            return null;
        }
    }

}
