/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterAssignment"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

int field;

void assignments(int value) {
    value = 1; // violation 'Assignment of parameter .value. is not allowed'
    value += 2; // violation 'Assignment of parameter .value. is not allowed'
    value++; // violation 'Assignment of parameter .value. is not allowed'
    --value; // violation 'Assignment of parameter .value. is not allowed'
}

void allowedAssignments(int value, int[] values) {
    int local = value;
    local++;
    field = value;
    values[0] = value;
}

void lambdaParameters(int value) {
    java.util.function.IntUnaryOperator operation = argument -> {
        argument += 1; // violation 'Assignment of parameter .argument. is not allowed'
        return argument;
    };
    value = operation.applyAsInt(value);
    // violation above 'Assignment of parameter .value. is not allowed'
}

void independentMethod() {
    int value = 0;
    value++;
}

void main() {
    assignments(0);
    allowedAssignments(1, new int[1]);
    lambdaParameters(2);
    independentMethod();
}
