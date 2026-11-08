/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = (default)true
violateImpliedAbstractMethod = (default)true
violateImpliedPublicNested = (default)true
violateImpliedStaticNested = (default)true


*/

// non-compiled with javac: Compilable with Java25

int topLevelField = 1;

void main() {
}

void topLevelHelper() {
}

class TopLevelClass {
    int classField = 1;

    void classMethod() {
    }
}

interface Shape {
    public static final int EXPLICIT = 1;

    int IMPLIED = 2;
    // 3 violations above:
    //     'Implied modifier 'final' should be explicit.'
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    public abstract double area();

    double perimeter();
    // 2 violations above:
    //     'Implied modifier 'abstract' should be explicit.'
    //     'Implied modifier 'public' should be explicit.'

    // violation below 'Implied modifier 'public' should be explicit.'
    default String name() {
        return "shape";
    }

    // violation below 'Implied modifier 'public' should be explicit.'
    static Shape unit() {
        return null;
    }

    private void helper() {
    }

    public static class ExplicitNested {
    }

    class ImpliedNested {}
    // 2 violations above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    enum ImpliedEnum { A }
    // 2 violations above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'

    interface ImpliedInterface {}
    // 2 violations above:
    //     'Implied modifier 'public' should be explicit.'
    //     'Implied modifier 'static' should be explicit.'
}

class Holder {
    interface Inner {
        int VALUE = 1;
        // 3 violations above:
        //     'Implied modifier 'final' should be explicit.'
        //     'Implied modifier 'public' should be explicit.'
        //     'Implied modifier 'static' should be explicit.'

        void run();
        // 2 violations above:
        //     'Implied modifier 'abstract' should be explicit.'
        //     'Implied modifier 'public' should be explicit.'
    }
}
