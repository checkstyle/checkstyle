/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.coding.variabledeclarationusagedistance;

public class InputVariableDeclarationUsageDistanceInitializationStatements2 {

    public void testGenerics() {
        InputVariableDeclarationUsageDistanceInitializationStatements2 obj =
            new InputVariableDeclarationUsageDistanceInitializationStatements2();
        int c = 12;
        int d = 13; // violation 'Dist.* between variable 'd'.* and its first usage is 6'
        obj.<Integer>get().getClass();
        obj.<Integer>get().getClass();
        obj.<Integer>get().getClass();
        obj.<Integer>get().getClass();
        obj.<Integer>get(c).getClass();
        int k = d + 12;
    }

    public void testTypecast() {
        ClassUtil1 obj = ClassUtil1.get();
        int k = 13;
        int s = 2; // violation 'Dist.* between variable 's'.* and its first usage is 6'
        ((subClass) (obj.setVal1(1))).setVal1(2).setVal1(3);
        ((subClass) (obj.setVal1(1))).setVal1(2).setVal1(3);
        ((subClass) (obj.setVal1(1))).setVal1(2).setVal1(3);
        ((subClass) (obj.setVal1(1))).setVal1(2).setVal1(3);
        ((subClass) (obj.setVal1(1))).setVal1(2).setVal1(k);
        int j = s + 1;
    }

    public int testMethodCallInVariableDeclaration(int aa) {
        int a = 12; // violation 'Dist.* between variable 'a'.* and its first usage is 4'
        testMethodCallInVariableDeclaration(aa);
        testMethodCallInVariableDeclaration(aa);
        testMethodCallInVariableDeclaration(aa);
        final int b = testMethodCallInVariableDeclaration(a);
        return 0;
    }

    private <T> InputVariableDeclarationUsageDistanceInitializationStatements2 get(T t) {
        return null;
    }

    private Object get() {
        return null;
    }

}

class ClassUtil1 {
    private ClassUtil1[] arr = {this};

    static ClassUtil1 get() {
        return new ClassUtil1();
    }

    ClassUtil1 setVal1(int val1) {
        return this;
    }
}

class subClass<T> extends ClassUtil1 {
}
