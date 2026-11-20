package com.puppycrawl.tools.checkstyle.grammar.comments;

class InputComments3 {
    public void a() { } // trailing
    // own-line comment
    /** javadoc */
    public void b() { }

    void c() { } // trailing no modifiers
    // own-line comment
    /** javadoc */
    void d() { }

    void e() { } // trailing before field
    int f;

    void g() { } // trailing before field with javadoc
    /** javadoc */
    int h;

    void i() { } // trailing before constructor
    InputComments3() { }

    void j() { } // trailing before constructor with javadoc
    /** javadoc */
    InputComments3(int k) { }

    void l() { } /* between */ ; // trails the semicolon, not the brace
    void m() { }

    void n() { } ; // trails the semicolon directly after the brace
    void o() { }
}
