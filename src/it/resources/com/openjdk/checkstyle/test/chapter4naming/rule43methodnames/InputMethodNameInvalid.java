package com.openjdk.checkstyle.test.chapter4naming.rule43methodnames;

/** Invalid method names for OpenJDK style section 4.3. */
public class InputMethodNameInvalid {

    public void a() { // violation 'Name 'a' must match pattern'
    }

    public void _do() { // violation 'Name '_do' must match pattern'
    }

    public void BadName() { // violation 'Name 'BadName' must match pattern'
    }

    public void bad_name() { // violation 'Name 'bad_name' must match pattern'
    }

}
