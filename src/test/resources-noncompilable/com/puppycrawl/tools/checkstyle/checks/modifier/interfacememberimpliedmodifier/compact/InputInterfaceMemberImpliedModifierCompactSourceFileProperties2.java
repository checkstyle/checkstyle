/*
InterfaceMemberImpliedModifier
violateImpliedPublicField = (default)true
violateImpliedStaticField = (default)true
violateImpliedFinalField = (default)true
violateImpliedPublicMethod = false
violateImpliedAbstractMethod = false
violateImpliedPublicNested = false
violateImpliedStaticNested = false


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

    default String name() {
        return "shape";
    }

    static Shape unit() {
        return null;
    }

    private void helper() {
    }

    public static class ExplicitNested {
    }

    class ImpliedNested {}

    enum ImpliedEnum { A }

    interface ImpliedInterface {}
}

class Holder {
    interface Inner {
        int VALUE = 1;
        // 3 violations above:
        //     'Implied modifier 'final' should be explicit.'
        //     'Implied modifier 'public' should be explicit.'
        //     'Implied modifier 'static' should be explicit.'

        void run();
    }
}
