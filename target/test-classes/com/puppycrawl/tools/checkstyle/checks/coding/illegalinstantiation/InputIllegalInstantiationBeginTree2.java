/*
IllegalInstantiation
classes = java.lang.Boolean,com.puppycrawl.tools.checkstyle.checks.coding.illegalinstantiation.\
          InputIllegalInstantiationBeginTree2.InputModifier
tokens = (default)CLASS_DEF


*/


public class InputIllegalInstantiationBeginTree2 {

    void otherInstantiations()
    {
        // instantiation of classes in the same package
        Object o1 = new InputIllegalInstantiationBeginTree2.InputBraces();
        Object o2 = new InputIllegalInstantiationBeginTree2.InputModifier();
    }

    private class InputBraces {

    }

    private class InputModifier {

    }
}
