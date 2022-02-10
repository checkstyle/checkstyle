/* Config:                                                                    //indent:0 exp:0
 * This test-input is intended to be checked using following configuration:   //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 * arrayInitIndent = 4                                                        //indent:1 exp:1
 * basicOffset = 4                                                            //indent:1 exp:1
 * braceAdjustment = 0                                                        //indent:1 exp:1
 * caseIndent = 4                                                             //indent:1 exp:1
 * forceStrictCondition = false                                               //indent:1 exp:1
 * lineWrappingIndentation = 4                                                //indent:1 exp:1
 * tabWidth = 4                                                               //indent:1 exp:1
 * throwsIndent = 4                                                           //indent:1 exp:1
 *                                                                            //indent:1 exp:1
 */                                                                           //indent:1 exp:1
package com.puppycrawl.tools.checkstyle.checks.indentation.indentation; //indent:0 exp:0

import java.util.function.Consumer; //indent:0 exp:0
import java.util.function.DoubleBinaryOperator; //indent:0 exp:0
import java.util.function.Function; //indent:0 exp:0

public class InputIndentationLambdaInEnum { //indent:0 exp:0

    enum Operation implements DoubleBinaryOperator { //indent:4 exp:4

        MULTIPLY( //indent:8 exp:8
            (l, r) //indent:12 exp:12
                -> l * r //indent:16 exp:16
            , "*"),//indent:12 exp:12
        DIVIDE( //indent:8 exp:8
    (l, r) -> l / r, "/"); //indent:4 exp:8 warn

        private final String symbol; //indent:8 exp:8
        private final DoubleBinaryOperator binaryOperator; //indent:8 exp:8

        private Operation(final DoubleBinaryOperator binaryOperator, final String symbol) { //indent:8 exp:8
            this.symbol = symbol; //indent:12 exp:12
            this.binaryOperator = binaryOperator; //indent:12 exp:12
        } //indent:8 exp:8

        public String getSymbol() { //indent:8 exp:8
            return symbol;//indent:12 exp:12
        }//indent:8 exp:8

        @Override //indent:8 exp:8
        public double applyAsDouble(final double left, final double right) { //indent:8 exp:8
            return binaryOperator.applyAsDouble(left, right); //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4
    public enum SomeEnum { //indent:4 exp:4
        ENUM_VALUE( //indent:8 exp:8
            v -> v, //indent:12 exp:12
            new Object() //indent:12 exp:12
        ); //indent:8 exp:8

        private final Function<Object, Object> function;//indent:8 exp:8
        private final Object object; //indent:8 exp:8

        SomeEnum(Function<Object, Object> function, Object object) { //indent:8 exp:8
            this.function = function; //indent:12 exp:12
            this.object = object; //indent:12 exp:12
        } //indent:8 exp:8
    } //indent:4 exp:4

    public enum SomeEnum2 { //indent:4 exp:4
        A( //indent:8 exp:8
            v -> //indent:12 exp:12
                    System.out.println(), //indent:20 exp:20
            "new Object()" //indent:12 exp:12
        ); //indent:8 exp:8

        private Object object = null; //indent:8 exp:8

        SomeEnum2(Consumer<String> o, String s ) { //indent:8 exp:8
        } //indent:8 exp:8
    } //indent:4 exp:4

    public enum SomeEnum3 { //indent:4 exp:4
        A( //indent:8 exp:8
            "new Object()", //indent:12 exp:12
                v //indent:16 exp:16
                    -> //indent:20 exp:20
                    System.out.println() //indent:20 exp:20
        ); //indent:8 exp:8

        private Object object = null; //indent:8 exp:8

        SomeEnum3( String s, Consumer<String> o) { //indent:8 exp:8
        } //indent:8 exp:8
    } //indent:4 exp:4

    public enum SomeEnum4 { //indent:4 exp:4
        A( //indent:8 exp:8
            v -> //indent:12 exp:12
                System.out.println(), //indent:16 exp:16
            "new Object()" //indent:12 exp:12
        ); //indent:8 exp:8

        private Object object = null; //indent:8 exp:8

        SomeEnum4(Consumer<String> o, String s) { //indent:8 exp:8
        } //indent:8 exp:8
    } //indent:4 exp:4

} //indent:0 exp:0
