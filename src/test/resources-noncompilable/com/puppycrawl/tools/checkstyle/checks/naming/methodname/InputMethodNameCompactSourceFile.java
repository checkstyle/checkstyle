/*
MethodName
format = (default)^[a-z][a-zA-Z0-9]*$
allowClassName = (default)false
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

// non-compiled with javac: Compilable with Java25

String greeting = "Hello";

void main() {
  System.out.println(greeting);
  helper();
  withLocalTypes();
}

void helper() {}

void Helper() {} // violation 'Name 'Helper' must match pattern'

void h3lper() {}

void h$elper() {} // violation 'Name 'h\$elper' must match pattern'

void h_elper() {} // violation 'Name 'h_elper' must match pattern'

void withLocalTypes() {
    class Local {
        void local() {}

        void lo_cal() {} // violation 'Name 'lo_cal' must match pattern'
    }

  new Object() {
    void anon() {}

    void Anon() {} // violation 'Name 'Anon' must match pattern'

    void an_on() {} // violation 'Name 'an_on' must match pattern'
  };
}

interface Contract {
  void goodName();

  void BadName(); // violation 'Name 'BadName' must match pattern'
}

class Implementer implements Contract {
  @Override
  public void BadName() { } // overridden; MethodName should not report this

  @Override
  public void goodName() { }
}

record R(int x) {
  void ok123() {}

  void NotOk() {} // violation 'Name 'NotOk' must match pattern'
}

@interface TestTag {}

@TestTag
void tagged_Method() {} // violation 'Name 'tagged_Method' must match pattern'
