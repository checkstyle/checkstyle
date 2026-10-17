/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber"/>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { }

void atLimit(int a, int b, int c, int d, int e, int f, int g) { }

// violation below 'More than 7 parameters (found 8).'
void overLimit(int a, int b, int c, int d, int e, int f, int g, int h) { }

// violation 2 lines below 'More than 7 parameters (found 8).'
@Deprecated
void annotated(int a, int b, int c, int d, int e, int f, int g, int h) { }

interface Handler {
    // violation below 'More than 7 parameters (found 8).'
    void handle(int a, int b, int c, int d, int e, int f, int g, int h);
}

class Worker implements Handler {
    // violation below 'More than 7 parameters (found 8).'
    Worker(int a, int b, int c, int d, int e, int f, int g, int h) { }

    // violation 2 lines below 'More than 7 parameters (found 8).'
    @Override
    public void handle(int a, int b, int c, int d, int e, int f, int g, int h) { }
}
