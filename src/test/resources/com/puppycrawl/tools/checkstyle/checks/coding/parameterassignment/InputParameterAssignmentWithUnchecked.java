package com.puppycrawl.tools.checkstyle.checks.coding.parameterassignment;
/**Input*/
public class InputParameterAssignmentWithUnchecked {
    int field;
    void foo1(int field) {
        int i = field;
        this.field = field;
        i++;
        field = 0;
        field += 1;
        this.field++;
        field--;
    }
    // without parameters
    void foo2() {
        field = 0;
    }
    @SuppressWarnings(value = "unchecked")
    void foo3(String field, int field1) {
        this.field = (field1 += field.length());
    }
}
