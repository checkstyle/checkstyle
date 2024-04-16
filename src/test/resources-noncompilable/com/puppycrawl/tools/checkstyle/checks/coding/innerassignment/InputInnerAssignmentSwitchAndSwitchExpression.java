/*
InnerAssignment


*/


//non-compiled with javac: Compilable with Java14
public class InputInnerAssignmentSwitchRuleAndSwitchExpression {

    public void test1(int mode) {
        int x = 0;
        switch(mode) {
            case 2 -> {
                x = 2;
            }
            case 1 -> x = 1;
        }
    }

    public void test2(int mode) {
        int x = 0;
        x = switch (mode) {
            case 2 -> {
                yield x = 2; // violation
            }
            case 1 -> x = 1;  // violation
            default -> throw new UnsupportedOperationException();
        };
    }
    public void test3(int mode) {
        int x = 0;
        switch(mode) {
            case 2 : {
                x = 2;
            }
            case 1 : x = 1;
        }
    }

    public void test4(String operation) {
        boolean innerFlag;
        switch (operation) {
            case "Y" -> flag = innerFlag = true; // violation
            case "N" -> {
                flag = innerFlag = false; // violation
            }
        }
    }

}
