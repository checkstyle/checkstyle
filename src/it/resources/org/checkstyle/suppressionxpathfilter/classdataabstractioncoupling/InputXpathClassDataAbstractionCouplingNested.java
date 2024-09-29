package org.checkstyle.suppressionxpathfilter.classdataabstractioncoupling;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.StringWriter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import java.util.concurrent.atomic.AtomicInteger;

public class InputXpathClassDataAbstractionCouplingNested { // warn

    class InnerClass {
        AtomicInteger innerAtomicInteger = new AtomicInteger();
        BigDecimal innerBigDecimal = new BigDecimal("1.0");
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            BigInteger bigInteger = new BigInteger("2");
            MathContext mathContext = new MathContext(2);
        }
    };

    BigDecimal bigDecimal = new BigDecimal("0");
    File file = new File("path");

    public void someMethod() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CharArrayWriter charArrayWriter = new CharArrayWriter();
                StringWriter stringWriter = new StringWriter();
            }
        }).start();
    }

    static class StaticInnerClass {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[1]);
    }
}

