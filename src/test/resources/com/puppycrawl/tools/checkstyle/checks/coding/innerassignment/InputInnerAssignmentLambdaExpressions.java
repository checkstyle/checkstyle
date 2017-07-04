package com.puppycrawl.tools.checkstyle.checks.coding.innerassignment;

import java.util.function.Supplier;


public class InputInnerAssignmentLambdaExpressions {
    interface MyLambda {
        void e(Object e);
    }
    private static class MyButton {
        public void setOnAction(MyLambda my) {
        }
    }
    private void setAction() {
        MyButton button = new MyButton();
        button.setOnAction(e -> { boolean pressed = true; });  //No violation here
    }
}
