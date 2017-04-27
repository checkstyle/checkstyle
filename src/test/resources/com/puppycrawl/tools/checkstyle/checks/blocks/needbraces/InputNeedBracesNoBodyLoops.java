package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesNoBodyLoops {

    private int value;
    private InputNeedBracesNoBodyLoops fco = new InputNeedBracesNoBodyLoops();

    {
        value = 0;
        while(incrementValue() < 5);;
    }

    public void foo() {
        while(incrementValue() < 5);
    }

    public void foo2() {
        for(int i = 0; i < 10; incrementValue());
        for(int i = 0; incrementValue() < 8; i++);
    }

    public void foo3() {
        while(incrementValue()
            < 5);
    }

    public void foo4() {
        while(incrementValue() < 5)
            ;
    }

    public void foo5() {
        while(incrementValue()
            < 5)
            ;
    }

    public void foo6() {
        while(
            incrementValue() < 5
            );
    }

    public void foo7() {
        while(
            incrementValue() < 5);
    }

    public void foo8() {
        for(int i = 0; incrementValue() < 8; i++);
    }

    public void foo9() {
        for(int i = 0;
            incrementValue() < 8;
            i++);
    }

    public void foo10() {
        for(
            int i = 0;
            incrementValue() < 8;
            i++);
    }

    public void foo11() {
        for
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
        while (true);
    }

    public void foo16() {
        for (;;);
    }

    public void foo17() {
        if(true);
    }

    public void foo18() {
        if(true) {
            int a;
        }
    }

    @Override
    public String toString(){
        while(fco.removeAssignedRole(this));
        return "";
    }

    private boolean removeAssignedRole(InputNeedBracesNoBodyLoops inputNeedBracesNoBodyLoops) {
        return true;
    }

    private int incrementValue() {
        return value++;
    }

    private void decrementValue() {
        value--;
    }
}
