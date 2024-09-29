package org.checkstyle.suppressionxpathfilter.classdataabstractioncoupling;

import java.io.CharArrayWriter;
import java.io.File;
import java.io.StringWriter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public enum InputXpathClassDataAbstractionCouplingEnum { // warn
    Dummy;

    Thread create() {
       return new Thread(new Runnable() {
            @Override
            public void run() {
                CharArrayWriter charArrayWriter = new CharArrayWriter();
                StringWriter stringWriter = new StringWriter();
            }
        });
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
}

