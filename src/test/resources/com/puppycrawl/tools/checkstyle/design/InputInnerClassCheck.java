package com.puppycrawl.tools.checkstyle.design;

public class InputInnerClassCheck {
	public int test1 = 100;

	public void methodTestInner1() {
		double test2 = 200;

		class InnerInMethod1 {
			void methodTest1() {
				System.out.println("test1");
			}
		}

		int test3 = 300; //error
	}

	public void methodTestInner2() {
		int test5 = 500;

		class InnerInMethod2 {
			int test6 = 500;
		}

		int test6 = 600; //error
		int test8 = 800; //error
	}

	class Inner1 {
		int test4 = 400;

		public void methodTestInner3() {
			int test9 = 500;

			class InnerInMethod3 {
				int test10 = 500;
			}

			int test11 = 600; //error
			int test12 = 800; //error
		}
	}

	void methodTest2() { //error
		System.out.println("test2");
	}
}

class Temp2 {
	class Inner1 {
		int test4 = 400;

		public void methodTestInner3() {
			int test9 = 500;

			class InnerInMethod3 {
				int test10 = 500;
			}

			int test11 = 600; //error
			int test12 = 800; //error
		}
	}

	void methodTest2() { //error
		System.out.println("test2");
	}

	private int i = 0; //error
}

class Temp3 {
    
    class InnerCheck {
        private int I = 0;
    }
    
    public int[] getDefaultTokens()
    {
        return new int[]{1, };
    }
}
