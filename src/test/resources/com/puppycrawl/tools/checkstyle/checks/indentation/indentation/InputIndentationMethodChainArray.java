// Indentation is correct here. This is a regression test.
public class InputIndentationMethodChainArray {

    InputIndentationMethodChainArray[] arr = {this};

    InputIndentationMethodChainArray getCurr() {
        return this;
    }

    InputIndentationMethodChainArray[] getCurrArr() {
        return arr;
    }

    void method() {
        InputIndentationMethodChainArray obj = new InputIndentationMethodChainArray();

        obj
            .getCurrArr()[0]
            .getCurr()
            .getCurr(); // No violation expected

        obj
            .getCurr()
            .getCurr()
            .getCurr(); // No violation expected
    }
} 