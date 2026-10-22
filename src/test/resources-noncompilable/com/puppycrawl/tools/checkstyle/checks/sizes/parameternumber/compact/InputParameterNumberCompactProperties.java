/*xml
<module name="Checker">
  <module name="TreeWalker">
    <module name="ParameterNumber">
      <property name="max" value="2"/>
      <property name="ignoreOverriddenMethods" value="true"/>
      <property name="ignoreAnnotatedBy" value="SkipParameters,java.lang.Deprecated"/>
    </module>
  </module>
</module>
*/

// non-compiled with javac: Compilable with Java25

void main() { }

void atLimit(int a, int b) { }

// violation below 'More than 2 parameters (found 3).'
void overLimit(int a, int b, int c) { }

@SkipParameters
void ignored(int a, int b, int c) { }

@java.lang.Deprecated
void deprecated(int a, int b, int c) { }

// violation 2 lines below 'More than 2 parameters (found 3).'
@Other
void other(int a, int b, int c) { }

interface Handler {
    // violation below 'More than 2 parameters (found 3).'
    void handle(int a, int b, int c);
}

class Worker implements Handler {
    // violation below 'More than 2 parameters (found 3).'
    Worker(int a, int b, int c) { }

    @SkipParameters
    Worker(int a, int b, int c, int d) { }

    @Override
    public void handle(int a, int b, int c) { }
}

@interface SkipParameters { }

@interface Other { }
