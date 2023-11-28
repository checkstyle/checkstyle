//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.grammar.java21;

public class InputUnnamedVariableSwitch {
        int testMultiValuesTopLevel(Object o) {
        return switch (o) {
            case R1 _, R2 _ -> 1;
            default -> 2;
        };
    }

    int testMultiValuesTopLevel2(Base o) {
        return switch (o) {
            case R1 r -> 1;
            case R2 _, R3 _, R4 _ -> 2;
        };
    }

    int testMultiValuesNested(Box<?> b) {
        return switch (b) {
            case Box(R1 _), Box(R2 _) -> 1;
            case Box(R3 _) -> 2;
            case Box(_)  -> 3;
        };
    }

    int testMultiValuesNestedUnnamedVarAndPattern(Box<?> b) {
        return switch (b) {
            case Box(R1 _), Box(R2 _) -> 1;
            case Box(R3 _), Box(_) -> 2;
        };
    }

    int testMultiValuesNestedMix(Object b) {
        return switch (b) {
            case Box(_), Box2(_) -> 1;
            default -> 2;
        };
    }

    int testMultiValuesNestedMix2(Object b) {
        return switch (b) {
            case Box(_), String _ -> 1;
            default -> 2;
        };
    }

    int testMultiValuesStatementBlock(Object o) {
        switch (o) {
            case Integer _:
            case Number _:
                return 1;
            default:
                return 2;
        }
    }

    int testMultiValuesStatementBlock2(Object o) {
        switch (o) {
            case Box(_):
            case String _:
                return 1;
            default:
                return 2;
        }
    }

    int testMultiValuesGuards(Base b, int x) {
        return switch (b) {
            case R1 r -> 1;
            case R2 _, R3 _, R4 _ when x == 1 -> 2;
            case R2 _, R3 _, R4 _ -> 3;
        };
    }

    int testMixVarWithExplicit(Box<?> t) {
        int success = -1;
        success = switch(t) {
            case Box(R1 _) : {
                yield 1;
            }
            case Box(R2 _), Box(var _) : {
                yield 2;
            }
            default : {
                yield -2;
            }
        };
        return success;
    }

    String unnamedGuardAddsBindings(Object o1, Object o2) {
        return switch (o1) {
            case String _, Object _ when o2 instanceof String s: yield s;
            case Object _: yield "any";
        };
    }

    boolean testUnnamedPrimitiveAndExhaustiveness(RecordWithPrimitive a) {
        boolean r1 = switch (a) {
            case Prim1(var _) -> true;
            case Prim2(_) -> false;
        };

        boolean r2 = switch (a) {
            case Prim1(var _) -> true;
            case Prim2(var _) -> false;
        };

        boolean r3 = switch (a) {
            case Prim1(_) -> true;
            case Prim2(_) -> false;
        };

        return r1 && r2 && r3;
    }

    abstract sealed class Base permits R1, R2, R3, R4 { }
    final  class R1  extends Base { }
    final  class R2  extends Base { }
    final  class R3  extends Base { }
    final  class R4  extends Base { }
    sealed interface RecordWithPrimitive permits Prim1, Prim2 {};
    record Prim1(int n1) implements RecordWithPrimitive {};
    record Prim2(int n2) implements RecordWithPrimitive {};
    record Box<T extends Base>(T content) { }
    record Box2<T extends Base>(T content) { }
}
