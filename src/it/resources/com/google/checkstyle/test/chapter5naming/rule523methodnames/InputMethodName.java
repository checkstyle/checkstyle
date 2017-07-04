package com.google.checkstyle.test.chapter5naming.rule523methodnames;

/**
 * Test input for MethodNameCheck specifically
 * whether the method name equals the class name.
 *
 * @author Travis Schneeberger
 */
public class InputMethodName {
	void foo() {}
	void Foo() {} //warn
	void fOo() {} //warn
	void f0o() {}
	void f$o() {} //warn
	void f_oo() {} //warn
	void f() {} //warn
	void fO() {} //warn

	class InnerFoo {
		void foo() {}
		void Foo() {} //warn
		void fOo() {} //warn
		void f0o() {}
		void f$o() {} //warn
		void f_oo() {} //warn
		void f() {} //warn
		void fO() {} //warn
	}

	InnerFoo anon = new InnerFoo() {
		void foo() {}
		void Foo() {} //warn
		void fOo() {} //warn
		void f0o() {}
		void f$o() {} //warn
		void f_oo() {} //warn
		void f() {} //warn
		void fO() {} //warn
	};
}

interface FooIn {
	void foo();
	void Foo(); //warn
	void fOo(); //warn
	void f0o();
	void f$o(); //warn
	void f_oo(); //warn
	void f(); //warn
	void fO(); //warn
}
