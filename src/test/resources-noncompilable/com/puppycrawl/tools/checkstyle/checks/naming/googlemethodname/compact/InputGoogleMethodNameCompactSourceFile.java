/*
GoogleMethodName

*/

// non-compiled with javac: Compilable with Java25

import org.junit.jupiter.api.Test;

void main() {
    int a = "Hello".length();
}

// violation below 'Method name 'InvalidMethodName' must be .* start lowercase'
int InvalidMethodName() {
    return 1;
}

int validMethodName() {
    return 0;
}


// violation 2 lines below 'Test method name 'Bad_Test' segment must .* start lowercase'
@Test
void Bad_Test(int a, int b) {
}

@Test
void good_test(int a, int b) {
}
