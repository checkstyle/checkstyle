package com.puppycrawl.tools.checkstyle;

/**
 * Test input for MethodNameCheck specifically
 * whether the method name equals the class name.
 *
 * @author Travis Schneeberger
 */
public class InputMethNameEqualClsName {

	//illegal name
    public int InputMethNameEqualClsName() {
        return 0;
    }

    //illegal name
    private int PRIVATEInputMethNameEqualClsName() {
        return 0;
    }

    class Inner {
		//illegal name
        public int Inner() {
			return 0;
		}

		//OK name - name of the outter class's ctor
        public int InputMethNameEqualClsName() {
			return 0;
		}
	}

	public void anotherMethod() {
		new InputMethNameEqualClsName() {

			//illegal name
            public int InputMethNameEqualClsName() {
				return 1;
			}
		};
	}
}

interface SweetInterface {
	
	//illegal name
    int SweetInterface();
}

class Outter {
	
	//illegal name
    public void Outter() {
		
	}
}
