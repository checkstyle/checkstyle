package com.puppycrawl.tools.checkstyle.checks.annotation.annotationonsameline;

import java.util.List;

@Ann
@Ann2 interface TestInterface {

    @Ann
    @Ann2 Integer getX();

}

public @Ann
@Ann2 class InputAnnotationOnSameLineCheckOnDifferentTokens implements @Ann
        @Ann2 TestInterface {

    @Ann
    @Ann2 private Integer x = new @Ann
            @Ann2 Integer(0);

    private List<@Ann
            @Ann2 Integer> integerList;

    @Ann
    @Ann2 enum TestEnum {
        A1, A2
    }

    @Ann
    @Ann2 public InputAnnotationOnSameLineCheckOnDifferentTokens() {
    }

    @Ann
    @Ann2 public void setX(@Ann
            @Ann2 int x) throws @Ann
                    @Ann2 Exception {
        this.<@Ann
                @Ann2 Integer> getXAs();
        this.x = x;
    }

    @Override public Integer getX() {
        return (@Ann
                @Ann2 Integer) x;
    }

    public <T> T getXAs() {
        return (T) x;
    }

}

@Ann
@Ann2 @interface TestAnnotation {

    @Ann
    @Ann2 int x();

}
