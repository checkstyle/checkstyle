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

import java.util.List; //indent:0 exp:0
import java.util.function.DoubleBinaryOperator; //indent:0 exp:0
import java.util.function.Function; //indent:0 exp:0
import java.util.function.Predicate;  //indent:0 exp:0

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

        Operation(final DoubleBinaryOperator binaryOperator, final String symbol) { //indent:8 exp:8
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

    public enum Operation2 { //indent:4 exp:4

        GET(-1, false, 1, m -> m.isEmpty()  //indent:8 exp:8
            && (m.getClass().getSimpleName().startsWith("get") //indent:12 exp:12
            || m.getClass().getSimpleName().startsWith("get"))), //indent:12 exp:12
        SET(0, false, 1, m  //indent:8 exp:8
            -> m.size() == 1 && m.getClass().getSimpleName().startsWith("set")), //indent:12 exp:12
        ADD1(0, true, 0, m -> { //indent:8 exp:8
                if (m.getClass().getSimpleName() == "1") { //indent:16 exp:12 warn
                if (m.getClass().getSimpleName().startsWith("add") || //indent:16 exp:16
                    m.getClass().getSimpleName().startsWith("insert")) { //indent:20 exp:20
                    return m.getClass().getSimpleName().endsWith("AtTop") //indent:20 exp:20
                        || m.getClass().getSimpleName().endsWith("Begin"); //indent:24 exp:24
                } //indent:16 exp:16
            } //indent:12 exp:12
            return false; //indent:12 exp:12
        }), //indent:8 exp:8
        ADD2(0, true, 1, m -> { //indent:8 exp:8
        if (m.size() == 1) { //indent:8 exp:12 warn
                return m.getClass().getSimpleName().startsWith("add") //indent:16 exp:16
                    || m.getClass().getSimpleName().startsWith("insert"); //indent:20 exp:20
            } //indent:12 exp:12
            return false; //indent:12 exp:12
        }), //indent:8 exp:8

        ON(1, true, 1, //indent:8 exp:8
            m -> { //indent:12 exp:12
            if (m.size() == 2 && "i".equals //indent:12 exp:12
                (m.get(0).getClass().getSimpleName())) { //indent:16 exp:16
                return m.getClass().getSimpleName().startsWith("add") || //indent:16 exp:16
                m.getClass().getSimpleName().startsWith("insert"); //indent:16 exp:16
            } //indent:12 exp:12
            return false; //indent:12 exp:12
        }); //indent:8 exp:8

        private final Predicate<List<String>> detector; //indent:8 exp:8
        private final int level; //indent:8 exp:8
        private final boolean multi; //indent:8 exp:8
        private final int valueParameterIndex; //indent:8 exp:8

        Operation2(int valueParameterIndex, boolean multi, int level, //indent:8 exp:8
                Predicate<List<String>> detector) { //indent:16 exp:16
            this.multi = multi; //indent:12 exp:12
            this.level = level; //indent:12 exp:12
            this.detector = detector; //indent:12 exp:12
            this.valueParameterIndex = valueParameterIndex; //indent:12 exp:12
        } //indent:8 exp:8
    }//indent:4 exp:4

} //indent:0 exp:0
