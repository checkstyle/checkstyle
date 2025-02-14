/*
NeedBraces
allowSingleLineStatement = (default)false
allowEmptyLoopBody = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesLoopBodyFalse {

    private int value;
    private InputNeedBracesLoopBodyFalse fco = new InputNeedBracesLoopBodyFalse();

    {
        value = 0;
        while(incrementValue() < 5);; // violation, 'while' construct must use '{}'s
    }

    public void foo() {
        while(incrementValue() < 5); // violation, 'while' construct must use '{}'s
    }

    public void foo2() {
        for(int i = 0; i < 10; incrementValue()); // violation, 'for' construct must use '{}'s
        for(int i = 0; incrementValue() < 8; i++); // violation, 'for' construct must use '{}'s
    }

    public void foo3() {
        while(incrementValue() // violation, 'while' construct must use '{}'s
            < 5);
    }

    public void foo4() {
        while(incrementValue() < 5) // violation, 'while' construct must use '{}'s
            ;
    }

    public void foo5() {
        while(incrementValue() // violation, 'while' construct must use '{}'s
            < 5)
            ;
    }

    public void foo6() {
        while( // violation, 'while' construct must use '{}'s
            incrementValue() < 5
            );
    }

    public void foo7() {
        while( // violation, 'while' construct must use '{}'s
            incrementValue() < 5);
    }

    public void foo8() {
        for(int i = 0; incrementValue() < 8; i++); // violation, 'for' construct must use '{}'s
    }

    public void foo9() {
        for(int i = 0; // violation, 'for' construct must use '{}'s
            incrementValue() < 8;
            i++);
    }

    public void foo10() {
        for( // violation, 'for' construct must use '{}'s
            int i = 0;
            incrementValue() < 8;
            i++);
    }

    public void foo11() {
        for // violation, 'for' construct must use '{}'s
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
        while (true); // violation, 'while' construct must use '{}'s
    }

    public void foo16() {
        for (;;); // violation, 'for' construct must use '{}'s
    }

    public void foo17() {
        if(true); // violation, 'if' construct must use '{}'s
    }

    public void foo18() {
        if(true) {
            int a;
        }
    }

    @Override
    public String toString(){
        while(fco.removeAssignedRole(this)); // violation, 'while' construct must use '{}'s
        return "";
    }

    private boolean removeAssignedRole(InputNeedBracesLoopBodyFalse inputNeedBracesNoBodyLoops) {
        return true;
    }

    private int incrementValue() {
        return value++;
    }

    private void decrementValue() {
        value--;
    }
}
