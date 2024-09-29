package org.checkstyle.suppressionxpathfilter.classdataabstractioncoupling;

import java.io.ByteArrayInputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.StringWriter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import java.util.concurrent.atomic.AtomicInteger;

public class InputXpathClassDataAbstractionCouplingClass { // warn
    AtomicInteger atomicInteger = new AtomicInteger();
    BigInteger bigInteger = new BigInteger("0");
    BigDecimal bigDecimal = new BigDecimal("0");
    MathContext mathContext = new MathContext(0);
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[1]);
    CharArrayWriter charArrayWriter = new CharArrayWriter();
    StringWriter stringWriter = new StringWriter();
    File file = new File("path");
}
