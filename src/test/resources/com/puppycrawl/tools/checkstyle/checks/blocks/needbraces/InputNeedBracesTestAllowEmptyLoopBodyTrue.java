package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

/*
 * Config:
 * allowEmptyLoopBody = true
 */
public class InputNeedBracesTestAllowEmptyLoopBodyTrue {

    private int value;
    private InputNeedBracesTestAllowEmptyLoopBodyTrue fco = new InputNeedBracesTestAllowEmptyLoopBodyTrue();

    {
        value = 0;
        while(incrementValue() < 5);; // ok
    }

    public void foo() {
        while(incrementValue() < 5); // ok
    }

    public void foo2() {
        for(int i = 0; i < 10; incrementValue()); // ok
        for(int i = 0; incrementValue() < 8; i++); // ok
    }

    public void foo3() {
        while(incrementValue() // ok
            < 5);
    }

    public void foo4() {
        while(incrementValue() < 5) // ok
            ;
    }

    public void foo5() {
        while(incrementValue() // ok
            < 5)
            ;
    }

    public void foo6() {
        while( // ok
            incrementValue() < 5
            );
    }

    public void foo7() {
        while( // ok
            incrementValue() < 5);
    }

    public void foo8() {
        for(int i = 0; incrementValue() < 8; i++); // ok
    }

    public void foo9() {
        for(int i = 0; // ok
            incrementValue() < 8;
            i++);
    }

    public void foo10() {
        for( // ok
            int i = 0;
            incrementValue() < 8;
            i++);
    }

    public void foo11() {
        for // ok
            (
            int i = 0;
            incrementValue() < 8;
            i++
            )
            ;
    }

    public void foo12() {
        for (int i = 0; incrementValue() < 8; i++) {
            int a = 5;
        }
    }

    public void foo14() {
        while (incrementValue() < 5) {
            return;
        }
    }

    public void foo15() {
        while (true); // ok
    }

    public void foo16() {
        for (;;); // ok
    }

    public void foo17() {
        if(true); // violation
    }

    public void foo18() {
        if(true) {
            int a;
        }
    }

    @Override
    public String toString(){
        while(fco.removeAssignedRole(this)); // ok
        return "";
    }

    private boolean removeAssignedRole(InputNeedBracesTestAllowEmptyLoopBodyTrue inputNeedBracesNoBodyLoops) {
        return true;
    }

    private int incrementValue() {
        return value++;
    }

    private void decrementValue() {
        value--;
    }
}
