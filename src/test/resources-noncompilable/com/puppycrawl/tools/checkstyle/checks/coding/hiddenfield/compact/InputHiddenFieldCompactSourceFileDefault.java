/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="HiddenField"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

String name = "default";
static int count;

void main() { }

void setName(String name) { // violation ''name' hides a field'
    this.name = name;
}

void locals(Object value) {
    String name = "local"; // violation ''name' hides a field'
    if (value instanceof Integer count) { // violation ''count' hides a field'
    }
}

void lambdas() {
    Runnable task = () -> {
        int count = 0; // violation ''count' hides a field'
    };
    java.util.function.Consumer<String> printer = name -> { }; // violation ''name' hides a field'
}

static void staticMethod(String name, int count) { } // violation ''count' hides a field'

record Point(int x) {
    void move(int x) { } // violation ''x' hides a field'

    void rename(String name) { }
}

class Inner {
    void rename(String name) { } // violation ''name' hides a field'
}
