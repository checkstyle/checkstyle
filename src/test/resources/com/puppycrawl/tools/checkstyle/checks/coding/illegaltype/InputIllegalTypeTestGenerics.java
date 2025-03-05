/*
IllegalType
validateAbstractClassNames = (default)false
illegalClassNames = Boolean, Foo, Serializable
legalAbstractClassNames = (default)
ignoredMethodNames = (default)getEnvironment, getInitialContext
illegalAbstractClassNameFormat = (default)^(.*[.])?Abstract.*$
memberModifiers = LITERAL_PUBLIC, FINAL
tokens = (default)ANNOTATION_FIELD_DEF, CLASS_DEF, INTERFACE_DEF, METHOD_CALL, METHOD_DEF, \
         METHOD_REF, PARAMETER_DEF, VARIABLE_DEF, PATTERN_VARIABLE_DEF, RECORD_DEF, \
         RECORD_COMPONENT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.illegaltype;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;

public abstract class InputIllegalTypeTestGenerics {

    private Set<Boolean> privateSet;
    private java.util.List<Map<Boolean, Foo>> privateList;
    public Set<Boolean> set; // violation, 'Usage of type Set is not allowed'.
    public java.util.List<Map<Boolean, Foo>> list;
        // 2 violations above:
        //                    'Usage of type 'Boolean' is not allowed.'
        //                    'Usage of type 'Foo' is not allowed'

    private void methodCall() {
        Bounded.<Boolean>foo(); // violation, 'Usage of type Boolean is not allowed'.
        final Consumer<Foo> consumer = Foo<Boolean>::foo;
        // 2 violations above:
        //                    'Usage of type 'Foo' is not allowed.'
        //                    'Usage of type 'Boolean' is not allowed'
    }

    public <T extends Boolean, U extends Serializable> void typeParameter(T a) {}
        // 2 violations above:
        //                    'Usage of type 'Boolean' is not allowed.'
        //                    'Usage of type 'Serializable' is not allowed'

    public void fullName(java.util.ArrayList<? super Boolean> a) {}
        // violation above, 'Usage of type 'Boolean' is not allowed'

    public abstract Set<Boolean> shortName(Set<? super Set<Boolean>> a);
        // 2 violations above:
        //                    'Usage of type 'Boolean' is not allowed.'
        //                    'Usage of type 'Boolean' is not allowed'

    public Set<? extends Foo<Boolean>> typeArgument() {
        // 2 violations above:
        //                    'Usage of type 'Foo' is not allowed.'
        //                    'Usage of type 'Boolean' is not allowed'
        return new TreeSet<Foo<Boolean>>();
    }

    public class MyClass<Foo extends Boolean> {}
    // 2 violations above:
    //                    'Usage of type 'Foo' is not allowed.'
    //                    'Usage of type 'Boolean' is not allowed'

}

class Bounded {

    public boolean match = new TreeSet<Integer>().stream()
            .allMatch(new TreeSet<>()::add);

    public static <Boolean> void foo() {} // violation, 'Usage of type Boolean is not allowed'.

}

class Foo<T extends Boolean & Serializable> {

    void foo() {}

}

@interface Annotation {

    Class<? extends Boolean>[] nonPublic();
    public Class<? extends Boolean>[] value(); // violation, 'Usage of type Boolean is not allowed'.

}
