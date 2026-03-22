/*
SayHello

*/

package com.puppycrawl.tools.checkstyle.checks.coding.sayhello;

public class InputSayHello { // violation 'Say Hello'
    public void sayHello() {
        // Hello!
    }
}

class InputSayHello2 { // ok, has hello variable
    int hello;
}

class InputSayHello3 { // violation, has variable but not 'hello'
    int world;
}
