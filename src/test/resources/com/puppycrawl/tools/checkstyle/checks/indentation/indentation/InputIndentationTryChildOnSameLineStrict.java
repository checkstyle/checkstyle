package com.puppycrawl.tools.checkstyle.checks.indentation.indentation;          //indent:0 exp:0

import java.awt.Component;                                                       //indent:0 exp:0

public class InputIndentationTryChildOnSameLineStrict {                          //indent:0 exp:0
    public static boolean doEquals(final Object a, final Object b,               //indent:4 exp:4
        Component c) {                                                           //indent:8 exp:8
        if (a == b) return true;                                                 //indent:8 exp:8
        final boolean[] ret = new boolean[1];                                    //indent:8 exp:8
        try {                                                                    //indent:8 exp:8
            invokeAndWait(new Runnable() { @Override                             //indent:12 exp:12
                public void run() {                                              //indent:16 exp:16
                    synchronized (ret) {                                         //indent:20 exp:20
                        ret[0] = a.equals(b);                                    //indent:24 exp:24
                    }                                                            //indent:20 exp:20
                }                                                                //indent:16 exp:16
            }, c);                                                               //indent:12 exp:12
        }                                                                        //indent:8 exp:8
        catch (Exception e) {                                                    //indent:8 exp:8
            e.printStackTrace();                                                 //indent:12 exp:12
        }                                                                        //indent:8 exp:8
        synchronized (ret) {                                                     //indent:8 exp:8
            return ret[0];                                                       //indent:12 exp:12
        }                                                                        //indent:8 exp:8
    }                                                                            //indent:4 exp:4

    private static void invokeAndWait(Runnable r, Object o) {                    //indent:4 exp:4
    }                                                                            //indent:4 exp:4
}                                                                                //indent:0 exp:0
