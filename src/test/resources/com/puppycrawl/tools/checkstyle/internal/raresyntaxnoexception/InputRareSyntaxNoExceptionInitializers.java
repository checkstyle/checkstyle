package com.puppycrawl.tools.checkstyle.internal.raresyntaxnoexception;

public class InputRareSyntaxNoExceptionInitializers {
    private static final String CREATED;
    private int state;

    static {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < 3; index++) {
            builder.append(index);
        }
        CREATED = builder.toString();
        try {
            Class.forName("java.lang.String");
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException("missing class", ex);
        }
    }

    {
        state = 0;
        int step = 0;
        while (step < 2) {
            state += step;
            step++;
        }
        switch (state) {
            case 0 -> state = 1;
            case 1 -> state = 2;
            default -> state = 0;
        }
        Runnable task = () -> {
            state = state + 1;
        };
        task.run();
    }

    public int state() {
        return state;
    }

    public static String created() {
        return CREATED;
    }
}
