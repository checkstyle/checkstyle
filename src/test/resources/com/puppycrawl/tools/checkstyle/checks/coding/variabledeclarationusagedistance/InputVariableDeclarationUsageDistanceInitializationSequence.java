/*
VariableDeclarationUsageDistance
allowedDistance = 1
ignoreVariablePattern = (default)
validateBetweenScopes = true
ignoreFinal = false


*/



import java.util.ArrayList;
import java.util.List;

public class InputVariableDeclarationUsageDistanceInitializationSequence {

    public void ifBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        if (check()) {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        if (check()) {
            System.out.println(list2.size());
        }
    }

    public void elseBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        if (check()) {
        } else {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        if (check()) {
        } else {
            System.out.println(list2.size());
        }
    }

    public void whileBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        while (check()) {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        while (check()) {
            list2.add(1);
        }
    }

    public void doWhileBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        do {
            System.out.println(list.size());
        } while (check());

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        do {
            list2.add(1);
        } while (check());
    }

    public void forBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        for (; check(); ) {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        for (; check(); ) {
            list2.add(1);
        }
    }

    public void forInit() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        for (System.out.println(list.size()); check(); ) {
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(1);
        for (list2.add(1); check(); ) {
        }
    }

    public void forIterator() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        for (; check(); System.out.println(list.size())) {
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(1);
        for (; check(); list2.add(1)) {
        }
    }

    public void forEnhanced() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        for (Integer i : list) {
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        System.out.println(1);
        for (Integer i : list2.subList(0, 1)) {
        }
    }

    public void tryBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        try {
            System.out.println(list.size());
        } catch (Exception e) {
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        try {
            list2.add(0);
        } catch (Exception e) {
        }
    }

    public void catchBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        try {
        } catch (Exception e) {
            System.out.println(list.size());
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        try {
        } catch (Exception e) {
            list2.add(0);
        }
    }

    public void finallyBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        try {
        } catch (Exception e) {
        } finally {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        try {
        } catch (Exception e) {
        } finally {
            list2.add(0);
        }
    }

    public void tryResource() {
        List<Integer> list = new ArrayList<>();
        this.getAutoCloseable(0);
        try (AutoCloseable a = this.getAutoCloseable(list.size())) {
        } catch (Exception e) {
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        try (AutoCloseable a = new Close(list2.size())) {
        } catch (Exception e) {
        }
    }

    public void codeBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        {
            list2.add(1);
        }
    }

    public void synchronizedBlock() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        synchronized (this) {
            System.out.println(list.size());
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 3'
        nothing();
        synchronized (this) {
            list2.add(1);
        }
    }

    public void synchronizedObject() {
        List<Integer> list = new ArrayList<>();
        int i = 0;
        synchronized (list.subList(i, 1)) {
        }

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        synchronized (list2.subList(0, 1)) {
        }
    }

    public void switchCase() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        switch (1 + 1) {
        case 2:
            nothing();
            break;
        case 3:
            System.out.println(list.size());
            break;
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        switch (1 + 1) {
        case 2:
            break;
        case 3:
            list2.add(0);
            break;
        }
    }

    public void switchDefault() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        switch (1 + 1) {
        case 2:
            nothing();
            break;
        default:
            System.out.println(list.size());
            break;
        }
        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        switch (1 + 1) {
        case 2:
            break;
        default:
            list2.add(0);
            break;
        }
    }

    public void innerClassMethod() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        class AClass extends Parent {
            @Override
            void method() {
                System.out.println(list.size());
            }
        }

        List<Integer> list2 = new ArrayList<>(); // violation 'Distance .* is 2'
        nothing();
        class BClass extends Parent {
            @Override
            void method() {
                list2.add(0);
            }
        }
    }

    public void innerClassField() {
        List<Integer> list = new ArrayList<>();
        Integer.valueOf(0);
        class AClass extends Parent {
            private int i = Integer.valueOf(list.size());
        }

        List<Integer> list2 = new ArrayList<>(); // violation 'Distance .* is 2'
        nothing();
        class BClass extends Parent {
            private int i = list2.size();
        }
    }

    public void innerClassInitializer() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        class AClass extends Parent {
            {
                System.out.println(list.size());
            }
        }

        List<Integer> list2 = new ArrayList<>(); // violation 'Distance .* is 3'
        nothing();
        System.out.println(1);
        class BClass extends Parent {
            {
                list2.add(1);
            }
        }
    }

    public void anonymousClassMethod() {
        List<Integer> list = new ArrayList<>();
        System.out.println(0);
        Parent aClass = new Parent() {
            @Override
            void method() {
                System.out.println(list.size());
            }
        };

        List<Integer> list2 = new ArrayList<>();  // violation 'Distance .* is 2'
        nothing();
        Parent bClass = new Parent() {
            @Override
            void method() {
                list2.add(0);
            }
        };
    }

    public void mixedNested1() {
        List<Integer> list = new ArrayList<>();
        class AClass extends Parent {
            @Override
            void method() {
                if (true) {
                    list.add(0);
                }
            }
        }
    }

    private void mixedNested2() {
        List<Integer> list = new ArrayList<>(); // violation 'Distance .* is 2'

        if (true) {
            int a = 3;
            while (check()) {
                int b = 2;
                nothing();
                {
                    list.add(2);
                }
            }
        }
    }

    private class Parent {
        void method() {
        }
    }

    private void nothing() {
    }

    private class Close implements AutoCloseable {
        public Close(int i) {

        }

        @Override
        public void close() throws Exception {

        }
    }

    private boolean check() {
        return true;
    }

    private AutoCloseable getAutoCloseable(int i) {
        return new Close(i);
    }
}
