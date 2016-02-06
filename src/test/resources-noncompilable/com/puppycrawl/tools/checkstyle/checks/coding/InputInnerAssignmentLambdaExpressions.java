package com.puppycrawl.tools.checkstyle.checks.coding;

import java.util.function.Supplier;

public class InputInnerAssignmentLambdaExpressions {
    interface MyLambda {
        boolean e(Object e);
    }
    private static class MyButton {
        public void setOnAction(MyLambda my) {
        }
    }
    private void setAction() {
        MyButton button = new MyButton();
        boolean pressed = false;
        button.setOnAction(e -> pressed = true);  //No violation here
        button.setOnAction(e -> { pressed = true; });  //No violation here
    }
}
