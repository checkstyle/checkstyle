package com.puppycrawl.tools.checkstyle.checks.blocks.rightcurly;

public class InputRightCurlySame {
    static {
    }

    public InputRightCurlySame() {
        Thread t = new Thread(new Runnable() {
            {
            }

            @Override
            public void run() {
            }
        });
    }

    public void doLoop() {
        do {
        } while (true);
    }

    public void whileLoop() {
        while (true) {
        }
    }

    public void forLoop() {
        for (; ; ) {
        }
    }
}
