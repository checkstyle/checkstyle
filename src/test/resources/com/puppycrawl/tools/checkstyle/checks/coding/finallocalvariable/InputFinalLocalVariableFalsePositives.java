/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.ArrayList;
import java.util.Arrays;

public class InputFinalLocalVariableFalsePositives {

    // spring-framework/spring-web/.../CommonsFileUploadSupport.java
    private void foo1() {
        String value;
        if (true) {
            try {
                value = "";
            }
            catch (Exception ex) {
                if (true) {

                }
                value = "";
            }
        }
        else {
            value = "";
        }
    }

    // spring-framework/spring-messaging/.../StompBrokerRelayMessageHandler.java
    private void foo2() {
        String stompAccessor;

        if (true) {
            throw new IllegalStateException(
                "No header accessor (not using the SimpMessagingTemplate?): ");
        }
        else if (true) {
            stompAccessor = "";
        }
        else if (true) {
            stompAccessor = "";
        }
        else {
            throw new IllegalStateException("Unexpected header accessor type ");
        }

        if (true) {
            if (true) {
                if (true) {
                }
                return;
            }
            stompAccessor.toString();
        }

        if (true) {
            return;
        }

        if (true) {
            if (true) {
            }
            stompAccessor = "";
            stompAccessor.toString();
            stompAccessor.toString();
            if (true) {
                stompAccessor.toString();
            }
        }
        else if (true) {
            if (true) {
                if (true) {
                }
                return;
            }
        }
        else {
            if (true) {
                if (true) {
                }
                return;
            }
        }
    }

    // spring-framework/spring-jdbc/.../SQLErrorCodesFactory.java
    private void foo3() {
        String errorCodes;

        try {
            if (true) {
            }
            else {
            }

            if (true) {
            }

            errorCodes = "";
            if (true) {
            }
        }
        catch (Exception ex) {
            errorCodes = "";
        }

        final String s = errorCodes;
    }

    // spring-framework/spring-context/.../TaskExecutorFactoryBean.java
    private void foo4() {
        if (true) {
            try {
                int corePoolSize;
                if (true) {
                    corePoolSize = Integer.valueOf("1");
                    if (corePoolSize > 2) {
                    }
                    if (true) {
                        if (corePoolSize == 0) {
                            corePoolSize = 2;
                        }
                        else {
                        }
                    }
                }
                else {
                    corePoolSize = 1;
                }
            }
            catch (NumberFormatException ex) {
            }
        }
    }

    // spring-framework/spring-beans/.../SimpleInstantiationStrategy.java
    private void foo5() {
        if (true) {
            final String s = "";
            String constructorToUse;
            synchronized (s) {
                constructorToUse = "";
                if (constructorToUse == null) {
                    if (true) {
                    }
                    try {
                        if (System.getSecurityManager() != null) {
                            constructorToUse = "";
                        }
                        else {
                            constructorToUse = "";
                        }
                    }
                    catch (Exception ex) {
                    }
                }
            }
        }
        else {
        }
    }

    // openjdk8/src/windows/classes/sun/print/Win32PrintJob.java
    private void foo6() {
        String[] attrs;
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
            }
        }

        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] instanceof Object) {
                }
                if (attrs[i] instanceof Object) {
                }
            }
        }
    }

    // openjdk8/src/windows/classes/sun/awt/windows/WInputMethod.java
    private void foo7() {
        int index;
        if (true) {
            index = 0;
        }
        else if (true) {
            index = 2;
        }
        else {
            return;
        }
        if (true) {
            index += 1;
        }
    }

    // openjdk8/src/solaris/classes/sun/print/UnixPrintJob.java
    private void foo8() {
        String[] attrs;
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] instanceof String) {
                }
            }
        }
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] instanceof Object) {
                }
                if (attrs[i] instanceof String) {
                }
            }
        }
    }

    // openjdk8/src/solaris/classes/sun/java2d/xr/XRRenderer.java
    private void foo9() {
        int transx, transy;

        if (true) {
            if (true) {
                if (true) {
                }
                else {
                }
                transx = 2;
                transy = 1;
            }
            else {
                transx = 0;
                transy = 0;
            }
        }
        if (true) {
            transx = 1;
            transy = 2;
        }
        else {
            transx = transy = 0;
        }

        try {
        }
        finally {
        }
    }

    // openjdk8/src/solaris/classes/sun/awt/X11InputMethod.java
    private void foo10() {
        int index;
        if (false) {
            index = 0;
        }
        else if (true) {
            index = 2;
        }
        else {
            return;
        }
        if (false) {
            index += 1;
        }
    }

    // jdk8/src/solaris/classes/java/util/prefs/FileSystemPreferences.java
    private void foo11() {
        if (true)
            if (true)
                return;
        long lastModifiedTime;
        if (true) {
            lastModifiedTime = 1L;
            if (true) {
            }
        }
        else if (true) {
        }
        if (true) {
            lastModifiedTime = 2L;
            if (true) {
            }
        }
    }

    // openjdk8/src/share/classes/sun/util/locale/provider/LocaleResources.java
    private void foo12() {
        String numElemKey;
        if (true) {
            numElemKey = ".NumberElements";
            if (true) {
            }
        }
        if (true) {
            numElemKey = "";
            if (true) {
            }
        }
    }

    // openjdk8/src/share/classes/sun/tools/jar/Main.java
    private void foo13() {
        String out;
        if (true) {
            out = "";
        }
        else {
            out = "";
            if (true) {
            }
        }
        if (true) {
            out = "";
        }
        if (true) {
        }
        out.toString();
        if (true) {
            try {
                out = "";
            }
            catch (Exception ioe) {
            }
            finally {
                if (true) {
                }
                if (true) {
                    out.toString();
                }
                if (true) {
                }
                if (true) {
                }
                if (true) {
                }
            }
        }
    }

    // openjdk8/src/share/classes/sun/text/normalizer/NormalizerBase.java
    private void foo14() {
        int c, c2; // violation, "Variable 'c2' should be declared final"
        if (true) {
        }
        if (true) {
            if ((c = 'd') >= 0) {
                if (true) {
                    c2 = 'a';
                    if (true) {
                        if (true) {
                            if (true) {
                            }
                            c = c2;
                        }
                        else {
                        }
                    }
                }

                if (true) {
                }
            }
            return;
        }

    }

    // openjdk8/src/share/classes/sun/text/normalizer/CharTrie.java
    private void foo15() {
        int limit;
        if (true) {
            limit = 0xdc00 >> 1;
            limit = 2 + 1;

        }
    }

    // openjdk8/src/share/classes/sun/text/bidi/BidiBase.java
    private void foo16() {
        final int a = 1;
        byte level;
        if (true) {
            switch (a) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    if (true) {
                    }
                    if ((true) ||
                        (true)) {
                        level = 1;
                        if (true) {
                        }
                        if (true) {
                        }
                        break;
                    }
                    for (int i = 0; i < 14; i++) {
                    }
                    if (true) {
                    }
                    break;
                case 4:
                    if (true)
                        break;
                case 5:
                    if (true) {
                        if (true) {
                            break;
                        }
                        if (true) {
                        }
                        break;
                    }
                    if (true) {
                    }
                    break;
                case 6:
                    break;
                case 7:
                    for (int i = 0; i < 15458; i++) {
                    }
                    if (true) {
                    }
                    break;

                case 8:
                    break;

                case 9:
                    if (true) {
                    }
                    break;
                case 10:
                    level = (byte) 1;
                    for (int i = 0; i < 12; i++) {
                        if (true) {
                        }
                    }
                    break;
                case 11:
                    level = 1;
                    for (int i = 0; i < 12; i++) {
                        if (true) {
                            while (level < 2) {
                            }
                            while (true) {
                            }
                        }
                        if (true) {
                            continue;
                        }
                    }
                    break;
                case 12:
                    level = (byte) 2;
                    for (int i = 0; i < 12; i++) {
                        if (true) {
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException("Internal ICU error in processPropertySeq");
            }
        }
        if (true) {
            level = (byte) 12;
            for (; ; ) {
            }
        }
    }

    // openjdk8/src/share/classes/sun/security/ssl/CipherBox.java
    private void foo17() {
        try {
            int newLen;
            if (true) {
                try {
                    newLen = 1;
                }
                catch (Exception ibse) {
                }
            }
            else {
                newLen = 2;
                if (true) {
                }
            }
            if (true) {
                try {
                }
                catch (Exception e) {
                }
            }
            if (true) {
                newLen = 3;
                if (true) {
                    if (true) {
                    }
                }
            }
            return;
        }
        catch (Exception e) {
            throw new ArrayIndexOutOfBoundsException(e.toString());
        }
    }

    // openjdk8/src/share/classes/sun/security/ssl/CipherBox.java
    private void foo18() throws Exception {
        int newLen;
        if (true) {
            try {
                newLen = 1;
            }
            catch (Exception ibse) {
            }
        }
        else {
            newLen = 2;
            if (true) {
            }
        }
        if (true) {
            try {
            }
            catch (Exception e) {
            }
        }
        if (true) {
            newLen = 1;
            if (true) {
                if (true) {
                    throw new Exception("invalid explicit IV");
                }
            }
        }
        return;
    }

    // openjdk8/src/share/classes/sun/security/rsa/RSACore.java
    private String foo19() {
        String params;
        synchronized (this) {
            params = "blindingCache.get(modulus)";
        }
        if (true) {
            return params;
        }
        params = "new BlindingParameters(e, re, rInv)";
        synchronized (this) {
        }
        return params;
    }

    // openjdk8/src/share/classes/sun/security/provider/certpath/ldap/LDAPCertStore.java
    private void foo20() {
        String cert;
        if (true) {
            cert = "certPair.getForward()";
            if (true) {
            }
        }
        if (true) {
            cert = "certPair.getReverse()";
            if (true) {
            }
        }
    }

    // openjdk8/src/share/classes/sun/security/pkcs11/P11KeyGenerator.java
    private void foo21() throws Exception {
        try {
            final int keyType = 8;
            String[] attributes;
            switch (keyType) {
                case 1:
                case 2:
                case 3:
                    attributes = null;
                    break;
                default:
                    attributes = null;
                    break;
            }
            attributes = null;
            return;
        }
        catch (Exception e) {
            throw new Exception("Could not generate key", e);
        }
        finally {
        }
    }

    // openjdk8/src/share/classes/sun/security/krb5/internal/KDCReqBody.java
    private void foo22() throws Exception {
        String subDer;
        if (true) {
            subDer = "";
            if (true) {
                while ("".equals(subDer)) {
                }
                for (int i = 0; i < 1541; i++) {

                }
            }
            else {
                throw new Exception();
            }
        }
        else {
            throw new Exception();
        }
        if (true) {
        }
        if (true) {
        }
        if (true) {
            if (true) {
                subDer = "";
                if (true) {
                    while ("".equals(subDer)) {
                    }
                }
                else {
                    throw new Exception();
                }
                if (true) {
                }
            }
            else {
                throw new Exception();
            }
        }
        if (true) {
            throw new Exception();
        }
    }

    // openjdk8/src/share/classes/sun/security/krb5/internal/KDCReq.java
    private void foo23() throws Exception {
        int bint;
        if (true) {
            throw new Exception();
        }
        if (true) {
            throw new Exception();
        }
        if (true) {
            bint = 1;
            if (true) {
                throw new Exception("");
            }
        }
        else {
            throw new Exception();
        }
        if (true) {
            bint = 2;
            if (bint == 4) {
                throw new Exception();
            }
        }
        else {
            throw new Exception();
        }
    }

    // openjdk8/src/share/classes/sun/rmi/server/LoaderHandler.java
    private void foo24() {
        final String a = "";
        String loader;
        synchronized (this) {
            while ("".equals(a)) {
                if (true) {
                }
            }
            if (true) {
                if (true) {
                }
                loader = "";
            }
            loader = "";
        }
    }

    // openjdk8/src/share/classes/sun/print/RasterPrinterJob.java
    private void foo25() {
        double w, h;
        if (true) {
            if (true) {
                w = 11.0;
                h = 12.2;
            }
        }
        if (true) {
            w = 1.0;
            h = 2.5;
        }
        return;
    }

    // openjdk8/src/share/classes/sun/print/PSStreamPrintJob.java
    private void foo26() {
        String[] attrs;
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (true) {
                }
            }
        }
        if (true) {
            attrs = null;
            for (int i = 0; i < attrs.length; i++) {
                if (attrs[i] instanceof String) {
                }
                if (attrs[i] instanceof String) {
                }
            }
        }
    }

    // openjdk8/src/share/classes/sun/net/www/protocol/http/HttpURLConnection.java
    private void foo27() {
        String locUrl;
        try {
            locUrl = "";
            if (true) {
                return;
            }
        }
        catch (Exception mue) {

            locUrl = "";
        }
    }

    // openjdk8/src/share/classes/sun/misc/Launcher.java
    private void foo28() {
        final String a = "";
        String[] path;
        if (true) {
            while ("".equals(a)) {
            }
            path = new String[5];
            while ("".equals(a)) {
                if (true) {
                    path[1] = "";
                }
                else {
                    path[2] = "";
                }
            }
            if (true) {
                path[3] = "";
            }
            else {
                path[4] = "";
            }
            if (true) {
                path = new String[14];
            }
        }
        else {
            path = new String[81];
        }
        return;
    }

    // openjdk8/src/share/classes/sun/misc/FloatingDecimal.java
    private void foo29() {
        int decExp;
        parseNumber:
        try {
            if (true) {
                throw new NumberFormatException("empty String");
            }
            int i = 0;
            switch (i) {
                case '-':
                case '+':
                    i++;
            }
            if (true) {
                if (true) {
                    return;
                }
                break parseNumber;
            }
            else if (true) {
                if (true) {
                    return;
                }
                break parseNumber;
            }
            else if (true) {
                if (true) {
                    if (true) {
                        return;
                    }
                }
            }
            skipLeadingZerosLoop:
            while (i < 15) {
                if (true) {
                }
                else if (true) {
                    if (true) {
                        throw new NumberFormatException("multiple points");
                    }
                    if (true) {
                    }
                }
                else {
                    break skipLeadingZerosLoop;
                }
                i++;
            }
            digitLoop:
            while (i < 12) {
                if (true) {
                }
                else if (true) {
                }
                else if (true) {
                    if (true) {
                        throw new NumberFormatException("multiple points");
                    }
                    if (true) {
                    }
                }
                else {
                    break digitLoop;
                }
                i++;
            }
            if (true) {
                break parseNumber;
            }
            if (true) {
                decExp = 1;
            }
            else {
                decExp = 2;
            }
            if (true) {
                switch (i) {
                    case '-':
                    case '+':
                        i++;
                }
                final int expAt = i;
                expLoop:
                while (i < 54) {
                    if (true) {
                    }
                    if (true) {
                    }
                    else {
                        i--;
                        break expLoop;
                    }
                }
                if (true) {
                    decExp = 12;
                }
                else {
                    decExp = decExp + 123;
                }
                if (i == expAt) {
                    break parseNumber;
                }
            }
            if (i < 9788) {
                break parseNumber;
            }
            if (true) {
                return;
            }
            return;
        }
        catch (StringIndexOutOfBoundsException e) {
        }
        throw new NumberFormatException();
    }

    // openjdk8/src/share/classes/sun/java2d/pipe/BufferedRenderPipe.java
    private void foo30() {
        int transx, transy;
        if (true) {
            if (true) {
                if (true) {
                }
                else {
                }
                transx = 1;
                transy = 2;
            }
            else {
                transx = 0;
                transy = 0;
            }
        }
        if (true) {
            transx = 4;
            transy = 1;
        }
        else {
            transx = transy = 0;
        }
    }

    // openjdk8/src/share/classes/sun/java2d/cmm/lcms/LCMSTransform.java
    private void foo31() throws Exception {
        String srcIL;
        if (true) {
            srcIL = "";
            if (srcIL != null) {
                return;
            }
        }
        if (true) {
            try {
                srcIL = "";
            }
            catch (Exception e) {
                throw new Exception("Unable to convert rasters");
            }
            for (int y = 0; y < 12; y++) {
                for (int x = 0; x < 12; x++) {
                    for (int i = 0; i < 5478; i++) {
                    }
                }
                for (int x = 0; x < 5; x++) {
                    for (int i = 0; i < 541; i++) {
                    }
                }
            }
        }
        else {
            try {
                srcIL = "";
            }
            catch (Exception e) {
                throw new Exception("Unable to convert rasters");
            }
        }
    }

    // openjdk8/src/share/classes/sun/java2d/cmm/lcms/LCMSTransform.java
    private void foo32() throws Exception {
       String srcIL, dstIL;
        try {
            if (true) {
                dstIL = "";

                if (dstIL != null) {
                    srcIL = "";
                    if (srcIL != null) {
                        return;
                    }
                }
            }
        }
        catch (Exception e) {
            throw new Exception("Unable to convert images");
        }
        if (true) {
            if (true) {
            }
            try {
                srcIL = "";
                dstIL = "";
            }
            catch (Exception e) {
                throw new Exception("Unable to convert images");
            }
        }
        else {
                    dstIL = "";
        }
    }

    // openjdk8/src/share/classes/sun/font/FileFontStrike.java
    private void foo33() {
        float advance;
        if (true) {
            advance = 1234;
            if (advance != Float.MAX_VALUE) {
                if (true) {
                    return;
                }
                else {
                    return;
                }
            }
        }
        else if (true) {
            if (true) {
                advance = 123;
                if (advance != Float.MAX_VALUE) {
                    if (true) {
                        return;
                    }
                    else {
                        return;
                    }
                }
            }
        }
        if (true) {
            return;
        }
        if (true) {
            advance = 12435;
        }
        else {
            if (true) {
            }
            else {
            }
            if (true) {
                advance = 123;
            }
            else {
                advance = 123414;
            }
        }
        if (true) {
        }
        else if (true) {
            if (true) {
                for (int i = 0; i < 214; i++) {
                }
            }
        }
        return;
    }

    // openjdk8/src/share/classes/sun/awt/geom/AreaOp.java
    private void foo34() {
        final String s = "";
        int etag;
        if (true) {
            etag = 1;
            do {
                if (true) {
                }
                if (true) {
                }
            } while ("".equals(s));
            if (true) {
                etag = 13213;
            }
            else {
            }
        }
        else {
            etag = 12312;
        }
    }

    // openjdk8/src/share/classes/javax/swing/text/html/parser/Parser.java
    private void foo35() {
        final int a = 8;
        String elem;
        switch (a) {
            case 1:
                switch (a) {
                    case '-':
                        while (true) {
                            if (true) {
                            }
                        }
                    default:
                }
            case '/':
                switch (a) {
                    case '>':
                    case '<':
                        if (true) {

                        }
                        elem = "";
                        break;
                    default:
                        if (true) {
                            return;
                        }
                        switch (a) {
                            case '>':
                            case '<':
                                break;
                            default:
                        }
                        if (true) {
                            elem = "";
                        }
                        else {
                            elem = "";
                        }
                        break;
                }
                if (true) {
                    elem = "";
                    if (true) {
                        return;
                    }
                }
                else {
                    if (true) {
                        elem = "";
                    }
                    else {
                        elem = "";
                    }
                }
                elem = "";
        }
    }

    // openjdk8/src/share/classes/javax/swing/text/html/parser/Parser.java
    private void foo36() {
        String attname;
        if (true) {
            attname = "";
            if (true) {
            }
            else {
                if (true) {
                    if (true) {
                    }
                    else {
                    }
                }
            }
        }
        else if (true) {
        }
        else if (true) {
            if (true) {
                attname = "";
                if (true) {
                }
                if (true) {
                }
                else {
                    if (true) {
                        if (true) {
                        }
                    }
                }
            }
            else {
            }
        }
        else if (true) {
            attname = "";
        }
        else if (true) {
            return;
        }
        else {
            if (true) {
            }
            else {
                return;
            }
        }
        if (true) {
            attname = "";
        }
        else {
        }
    }

    // openjdk8/src/share/classes/javax/swing/text/JTextComponent.java
    private void foo37() {
        int dot;
        if (true) {
            dot = 213213;
            if (true) {
                if (true) {
                }
            }
            if (true) {
                dot += 1;
                if (true) {
                    try {
                    }
                    catch (Exception ble) {
                    }
                }
            }
        }
        else if (true) {
            dot = 31;
        }
    }

    // openjdk8/src/share/classes/javax/swing/plaf/synth/ParsedSynthStyle.java
    private void foo38() {
        String painter;
        if (true) {
            if ((painter = "").equals("")) {
                return;
            }
        }
        if ((painter = "").equals("")) {
            return;
        }
    }

    // openjdk8/src/share/classes/javax/swing/plaf/metal/MetalInternalFrameTitlePane.java
    private void foo39() {
        int spacing;
        if (true) {
            if (true) {
                if (true);
            }
            else {
                spacing = 4;
                if (true) return;
            }
        }
        if (true) {
            spacing = 123;
            if (true) return;
        }
        if (true) {
            spacing = 12;
        }
    }

    // openjdk8/src/share/classes/javax/swing/plaf/basic/BasicBorders.java
    private void foo40() {
        String cBounds;
        if (true) {
            if (true) {
                cBounds = "";
            }
            if (true) {
                cBounds = "";
            }
        }
        else {
            if (true) {
                cBounds = "";
            }
            if (true) {
                cBounds = "";
            }
        }
    }

    // openjdk8/src/share/classes/javax/swing/border/CompoundBorder.java
    private void foo41() {
        String nextInsets;
        if (true) {
            nextInsets = "";
        }
        if (true) {
            nextInsets = "";
        }
    }

    // openjdk8/src/share/classes/javax/swing/JTree.java
    private String foo42() {
        if (true) {
            String rowBounds;
            if (true) {
                rowBounds = "";
                if (true) {
                    if (true) {
                        return "";
                    }
                    return "";
                }
                if (true) {
                    if (true) {
                        rowBounds = "";
                        return rowBounds;
                    }
                }
                else {
                    return rowBounds;
                }
            }
            return "";
        }
        return "";
    }

    // openjdk8/src/share/classes/javax/sql/rowset/spi/SyncFactory.java
    private void foo43() {
        String providerImpls;
        try {
            providerImpls = "";
        }
        catch (Exception ex) {
            providerImpls = null;
        }
        String strRowsetProperties;
        try {
            strRowsetProperties = "";
        }
        catch (Exception ex) {
            strRowsetProperties = "";
        }
    }

    // openjdk8/src/share/classes/javax/sound/sampled/AudioSystem.java
    private void foo45() {
        String mixer;
        if (true) {
            if (true) {
                if (true) {
                    mixer = "";
                    if (mixer != null) {
                        return;
                    }
                }
                else {
                    mixer = "";
                    if (mixer != null) {
                        return;
                    }
                }

            }
        }
        if (true) {
            mixer = "";
            if (mixer != null) {
                return;
            }
        }
    }

    // openjdk8/src/share/classes/javax/security/auth/SubjectDomainCombiner.java
    private void foo46() {
        String e;
        if (true) {
            synchronized (this) {
                e = "";

            }
            synchronized (this) {
                e = "";

            }
        }
    }

    // openjdk8/src/share/classes/javax/naming/spi/NamingManager.java
    private void foo47() {
        final String a = "";
        String factory;
        if (true) {
            factory = "";
        }
        if ("".equals(a)) {
            factory = "";
        }
    }

    // openjdk8/src/share/classes/java/util/regex/Pattern.java
    private void foo48() {
        int ch;
        if (true) {
            ch = 1;
        }
        if (true) {
            ch = 2;
        }
    }

    // openjdk8/src/share/classes/java/util/concurrent/locks/StampedLock.java
    private String foo49() {
        long next;
        if (!Thread.interrupted()) {
            if (true) {
                if (true) {
                    if ((next = 5) == 5)
                        return "next";
                }
                else if ((next = 5) == 5)
                    return "next";
            }
            if (true)
                return "0L";
            if (true)
                if ((next = 5) == 5)
                    return "next";
        }
        return "";
    }

    // openjdk8/src/share/classes/java/time/Duration.java
    private void foo50() {
        long nanos;
        try {
            nanos = 1L;
        }
        catch (Exception ex2) {
            nanos = 0L;
        }
    }

    // openjdk8/src/share/classes/java/text/CollationElementIterator.java
    private void foo51() {
        if (true) {
            int vowel;
            if (true) {
                vowel = 1;
            }
            if (true) {
                vowel = 2;
            }
        }
        if (true) {
            int consonant;
            if (true) {
                consonant = 23;
            }
            if (true) {
                consonant =2;
            }
        }
    }

    // openjdk8/src/share/classes/java/sql/DriverManager.java
    private void foo52() {
        String drivers;
        try {
            drivers = "";
        }
        catch (Exception ex) {
            drivers = null;
        }
    }

    // openjdk8/src/share/classes/java/security/Policy.java
    private void foo53() {
        String pc;
        synchronized (this) {
            pc = "Hello";
        }
        pc = "";
    }

    // openjdk8/src/share/classes/java/lang/invoke/DirectMethodHandle.java
    private void foo54() {
        final int a = 1;
        final String linkerName;
        String lambdaName;
        switch (a) {
            case 1:
                linkerName = "linkToVirtual";
                lambdaName = "DMH"
                    + ".invokeVirtual";
                break;
            case 2:
                linkerName = "linkToStatic";
                lambdaName = "DMH"
                    + ".invokeStatic";
                break;
            case 3:
                linkerName = "linkToStatic";
                lambdaName = "DMH.invokeStaticInit";
                break;
            case 4:
                linkerName = "linkToSpecial";
                lambdaName = "DMH"
                    + ".invokeSpecial";
                break;
            case 5:
                linkerName = "linkToInterface";
                lambdaName = "DMH"
                    + ".invokeInterface";
                break;
            case 6:
                linkerName = "linkToSpecial";
                lambdaName = "DMH"
                    + ".newInvokeSpecial";
                break;
            default:
                throw new InternalError("which=");
        }
        lambdaName += "_";
    }

    // openjdk8/src/share/classes/java/lang/Class.java
    private String foo55() {
        String res;
        if (true) {
            res = "";
            if (res != null) return res;
        }
        if (true) {
            res = "temporaryRes";
        }
        else {
            res = "";
        }
        if (true) {
            if (true) {
            }
            else {
            }
        }
        return "";
    }

    // openjdk8/src/share/classes/java/awt/geom/CubicCurve2D.java
    private void foo56() {
        int num;
        if (true) {
            num = 3;
        }
        else {
            num = 1;
            if (true) {
                num = 2;
            }
        }
        if (num > 1) {
            num = 2;
        }
    }

    // openjdk8/src/share/classes/java/awt/geom/AffineTransform.java
    private void foo57() {
        final int a = 1;
        double T01, T10;
        switch (a) {
            case 1:
                break;
            case 9:
            case 11:
            case 12:
                break;
            case 123:
            case 14:
                break;
            case 1234:
            case 12345:
                break;
            case 566:
            case 8678:
                break;
            case 534:
            case 7968:
            case 86785678:
            case 86759789:
                break;
            case 346547:
                T01 =1;
                T10 = 2;
                break;
            case 432534523:
            case 5345235:
                break;
            case 235345:
            case 523452345:
                break;
            case 75675467:
                break;
        }
        T01 = 21;
        T10 = 53;
    }

    // openjdk8/src/share/classes/java/awt/event/WindowEvent.java
    private void foo58() {
        final int a = 54;
        String typeStr;
        switch (a) {
            case 1:
                typeStr = "WINDOW_OPENED";
                break;
            case 2:
                typeStr = "WINDOW_CLOSING";
                break;
            case 3:
                typeStr = "WINDOW_CLOSED";
                break;
            case 4:
                typeStr = "WINDOW_ICONIFIED";
                break;
            case 5:
                typeStr = "WINDOW_DEICONIFIED";
                break;
            case 6:
                typeStr = "WINDOW_ACTIVATED";
                break;
            case 7:
                typeStr = "WINDOW_DEACTIVATED";
                break;
            case 8:
                typeStr = "WINDOW_GAINED_FOCUS";
                break;
            case 9:
                typeStr = "WINDOW_LOST_FOCUS";
                break;
            case 10:
                typeStr = "WINDOW_STATE_CHANGED";
                break;
            default:
                typeStr = "unknown type";
        }
        typeStr += ",";
    }

    // openjdk8/src/share/classes/com/sun/tools/example/debug/tty/Commands.java
    private void foo59() {
        String spec;
        if (true) {
            spec = "";
        }
        if (true) {
            spec = "";
        }
    }

    // openjdk8/src/share/classes/com/sun/security/auth/callback/TextCallbackHandler.java
    private void foo60() throws Exception {
        final int a = 1;
        String text;
        switch (a) {
            case 1:
                text = "";
                break;
            case 2:
                text = "Warning: ";
                break;
            case 3:
                text = "Error: ";
                break;
            default:
                throw new Exception();
        }
        final String message = "";
        if (message != null) {
            text += message;
        }
    }

    // openjdk8/src/share/classes/com/sun/net/ssl/SSLSecurity.java
    private void foo61() {
        String tmaw;
        if (true) {
            tmaw = "";
            for (int i = 0; i < 10; i++) {
                if (true) {
                    if (true) {
                        tmaw = "";
                    }
                }
                else {
                    tmaw = "";
                }
            }
            if (true) {
                tmaw = "";
            }
        }
        else {
            tmaw = null;
        }
    }

    // openjdk8/src/share/classes/com/sun/jndi/dns/DnsContext.java
    private void foo62() {
        String znode;
        synchronized (this) {
            znode = "";
        }
        if (znode != null) {
            synchronized (znode) {
            }
            if (true) {
                if (true) {
                    synchronized (znode) {
                        if (true) {
                        }
                        else if (true) {
                        }
                        else {
                            return;
                        }
                    }
                    if (true) {
                        return;
                    }
                }
            }
        }
        synchronized (this) {
            znode = "";
        }
    }

    // openjdk8/src/share/classes/com/sun/java/swing/plaf/gtk/GTKColorType.java
    private void foo63() {
        float h;
        float l;
        float s;
        synchronized (this) {
            h = 1;
            l = 2;
            s = 3;
        }
        h = 4;
        l = 5;
        s = 6;
    }

    // openjdk8/src/macosx/classes/sun/lwawt/macosx/CInputMethod.java
    private void foo64() {
        int index;
        if (true) {
            index = 0;
        }
        else if (true) {
            index = 2;
        }
        else {
            return;
        }
        if (true) {
            index += 1;
        }
    }

    // apache-struts/core/.../mapper/DefaultActionMapper.java
    private void foo65() {
        String name;
        if (true) {
            name = "uri";
        }
        else if (true) {
            name = "";
        }
        else if (true) {
            name = "";
        }
        else {
            name = "";
        }
        if (true) {
            if (true) {
                name = "";
            }
        }
    }

    // infinispan/remoting/transport/jgroups/JGroupsTransport.java
    private void foo66() {
        String cfg;
        if (true) {
            if (true) {
                cfg = "";
            }
            if (true) {
                cfg = "";
            }
            if (true) {
                cfg = "";
            }
        }
    }

    // hibernate-core/.../internal/javassist/BytecodeProviderImpl.java
    private void foo67() {
        String fastClass;
        try {
            fastClass = "";
            if (true) {
                if (fastClass == null) {
                }
                else {
                }
            }
        }
        catch (Throwable t) {
            fastClass = null;
        }
    }

    // guava-mvnstyle/guava/src/com/google/common/net/InetAddresses.java
    private void foo68() {
        int partsLo;
        if (true) {
            partsLo = 1;
            if ( --partsLo != 0) {
                return;
            }
            if (true) {
                return;
            }
        }
        else {
            partsLo = 0;
        }
    }

    // guava-mvnstyle/guava/src/com/google/common/collect/TreeMultiset.java
    private void foo69() {
        String node;
        if (true) {
            node = "";
            if (node == null) {
                return;
            }
            if (true) {
                node = "";
            }
        }
        else {
            node = "";
        }
    }

    // guava-mvnstyle/guava/src/com/google/common/cache/LongAddables.java
    private void foo70() {
        String supplier;
        try {
            supplier = "";
        }
        catch (Throwable t) {
            supplier = "";
        }
    }

    // findbugs/src/java/edu/umd/cs/findbugs/detect/FindNullDeref.java
    private void foo71() {
        int priority;
        if (true) {
            priority = 1;
        }
        else if (true) {
            priority = 2;
        }
        else {
            return;
        }
        if (true) {
            priority++;
        }
        if (true) {
            priority++;
        }
    }

    // findbugs/src/java/edu/umd/cs/findbugs/ba/PruneUnconditionalExceptionThrowerEdges.java
    private void foo72() {
        String p;
        try {
            p = "";
        }
        catch (RuntimeException e) {
            p = "";
        }
    }

    // findbugs/eclipsePlugin/src/de/tobject/findbugs/actions/MarkerRulerAction.java
    private void foo73() {
        String control;
        if (true) {
            if (true) {
                control = "";
                if (control != null) {
                }
            }
        }
        if (true) {
            if (true) {
            }
            if (true) {
                control = "";
                if (control != null) {
                }
            }
        }
    }

    // findbugs/eclipsePlugin/src/de/tobject/findbugs/DetectorsExtensionHelper.java
    private void foo75() {
        String libPathAsString;
        try {
            libPathAsString = "";
            if (libPathAsString == null) {
            }
            libPathAsString = "";
            if (libPathAsString == null) {
            }

        } catch(Exception ex) {

        }
    }

    // findbugs/src/java/edu/umd/cs/findbugs/detect/FindNullDeref.java
    private void foo76() {
        int priority;
        if (true) {
            priority = 2;
        }
        else if (true) {
            priority = 3;
        }
        else {
            return;
        }

        if (true) {
            priority++;
        }
        if (true) {
            priority++;
        }
    }

    // elasticsearch/src/main/java/org/elasticsearch/index/search/child/ParentIdsFilter.java
    private void foo77() {
        if (true) {
            int docId;
            if (true) {
                docId = 21;
                if (docId != 1254) {
                }
                else {
                }
            }
            else {
                docId = 45234;
                if (true) {
                }
            }
            if (true) {
                docId = 3213213;
            }
        }
    }

    // elasticsearch/src/main/java/org/elasticsearch/common/inject/internal/BindingBuilder.java
    private void foo78() {
        String injectionPoints;
        if (true) {
            try {
                injectionPoints = "";
            }
            catch (Exception e) {
                injectionPoints = "";
            }
        }
        else {
            injectionPoints = "";
        }
    }

    // apache-struts/core/.../dispatcher/mapper/DefaultActionMapper.java
    private void foo79() {
        String name;
        if (true) {
            name = "";
        }
        else if (true) {
            name = "";
        }
        else if (true) {
            name = "";
        }
        else {
            name = "";
        }
        if (true) {
            if (true) {
                name = "";
            }
        }
    }

    // apache-ant/src/main/org/apache/tools/ant/types/PropertySet.java
    private void foo80() {
        String names;
        if (true) {
            names = "";
            if (true) {
                names = "";
            }
        }
        else {
            names = "";
        }
    }

    // apache-ant/src/main/org/apache/tools/ant/taskdefs/optional/net/FTPTaskMirrorImpl.java
    private void foo81() {
        StringBuffer msg;
        synchronized (this) {
            msg = new StringBuffer("   [");
        }
        synchronized (this) {
            msg = new StringBuffer("   [");
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/regionserver/HStore.java
    private void foo82() {
       String filesToCompact;
        try {
            synchronized (this) {
                filesToCompact = "";
                filesToCompact = "";
            }
        }
        finally {
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/regionserver/HStore.java
    private void foo83() {
        String key;
        if (true) {
            try {
                key = "";
            }
            catch (Exception e) {
                if (true) {
                    try {
                        key = "";
                    }
                    catch (Exception ex) {
                    }
                }
            }
        }
        else {
            key = "";
        }
    }

    // Hbase/hbase-server/.../hadoop/hbase/regionserver/DefaultHeapMemoryTuner.java
    private void foo84() {
        float newMemstoreSize;
        float newBlockCacheSize;
        if (true) {
            newBlockCacheSize = 354;
            newMemstoreSize = 534;
        }
        else if (true) {
            newBlockCacheSize = 213213;
            newMemstoreSize = 53425;
        }
        else {
            return;
        }
        if (true) {
            newMemstoreSize = 2;
        }
        else if (true) {
            newMemstoreSize = 3;
        }
        if (true) {
            newBlockCacheSize = 354;
        }
        else if (true) {
            newBlockCacheSize = 14;
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/master/AssignmentManager.java
    private void foo85() {
        boolean needNewPlan;
        try {
            if (true) {
            }
            if (true) {
                needNewPlan = true;
            }
            else {
                if (true) {
                }
                return;
            }
        }
        catch (Throwable t) {
            if (t instanceof Object) {
            }
            if (true) {
                if (true) {
                    if (t instanceof Object) {
                    }
                    else {
                    }
                }
                try {
                    needNewPlan = false;
                    if (true) {
                    }
                    else if (true) {
                        needNewPlan = true;
                    }
                }
                catch (Exception ie) {
                    return;
                }
            }
            else if (true) {
                needNewPlan = false;
            }
            else {
                needNewPlan = true;
            }
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/mapred/TableRecordReaderImpl.java
    private void foo86() {
        String result;
        try {
            try {
                result = "";
                if (true) {
                    if (true) {
                    }
                }
            }
            catch (Exception e) {
                if (true) {
                }
                if (true) {
                }
                else {
                }
                result = "";
            }
        }
        catch (Exception ex) {
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/ipc/RpcServer.java
    private void foo87() {
        int count;
        if (true) {
            return;
        }
        try {
            count = 2;
        }
        catch (Exception ieo) {
            throw ieo;
        }
        catch (Error e) {
            count = -1;
        }
    }

    // Hbase/hbase-server/src/main/java/org/apache/hadoop/hbase/ipc/RpcServer.java
    private void foo88() {
        String c;
        synchronized (this) {
            try {
                c = "";
            }
            catch (Exception e) {
                return;
            }
        }
        if (true) {
            if (true)
                c = null;
        }
    }

    private void foo89() {
        final int a = 8;
        String b; // violation, "Variable 'b' should be declared final"
        switch (a) {
            case 8:
                b = "b";
                break;
            default:
                b = "c";
                break;
        }
    }

    private void foo90() {
        final int a = 8;
        String b;
        switch (a) {
            default:
                b = "c";
        }
        b = "b";
    }

    private void foo91() {
        Integer[] s;
        if (true) {
            s = new Integer[0];
        } else {
            s = new Integer[5];
            if (true) {
                final ArrayList<Integer> retain = new ArrayList<>();
                for (final Integer c : s) {
                    if (true) {
                        retain.add(c);
                    }
                }
                s = retain.toArray(new Integer[retain.size()]);
            }
            if (true && s.length > 0) {
                s = new Integer[] {s[0]};
            }
        }
        Arrays.toString(s);
    }

    // findbugs/src/java/edu/umd/cs/findbugs/ba/jsr305/TypeQualifierApplications.java
    private void foo92() {
        String tqa;
        if (true) {
            tqa = null;
            if (true) {
            }
        }
        else {
            if (true) {
            }
            tqa = "a";
            if (true) {
            }
            if (tqa == null) {
                if (true) {
                }
                tqa = "b";
                if (true) {
                    if (tqa == "abc") {
                    } else if (tqa != null) {
                    } else {
                    }
                }
            }
            if (tqa == "bcd") {
                tqa = null;
            }
            if (tqa == null) {
                if (true) {
                    if (true) {
                    }
                } else {
                    if (true) {
                    }
                    tqa = "c";
                    if (true) {
                    }
                }
            }
        }
    }

    // hibernate-core/.../internal/util/collections/BoundedConcurrentHashMap.java
    private void foo93() {
        if (true) {
            String evictedCopy;
            if (true) {
                final String evictedEntry = "a";
                evictedCopy = evictedEntry;
            } else {
                evictedCopy = "c";
                for (int i = 0; i < 0; ++i) {
                    evictedCopy.trim();
                }
                evictedCopy = evictedCopy.trim();
            }
        }
    }

    // spring-framework/spring-core/src/main/java/org/springframework/core/Conventions.java
    private void foo94() {
        String valueClass;
        if (true) {
            valueClass = "a";
        }
        else if (true) {
            valueClass = "b";
            if (valueClass == null) {
                if (true) {
                }
                if (true) {
                }
                valueClass = "c";
            }
        }
        else {
            valueClass = "d";
        }
    }

    // spring-framework/spring-test/.../springframework/test/annotation/ProfileValueUtils.java
    private void foo95() {
        String profileValueSource;
        if (true) {
            profileValueSource = "a";
        }
        else {
            try {
                profileValueSource = "b";
            }
            catch (Exception e) {
                if (true) {
                }
                profileValueSource = "c";
            }
        }
    }

    // spring-framework/spring-webmvc/.../method/annotation/RequestPartMethodArgumentResolver.java
    private void foo96() {
        Object arg;
        if (true) {
            arg = "a";
        }
        else if (true) {
            arg = "b";
        }
        else if (true) {
            arg = "c";
        }
        else if (true) {
            arg = "d";
        }
        else if (true) {
            arg = "e";
        }
        else if (true) {
            arg = "f";
        }
        else {
            try {
                arg = "g";
                if (arg != null) {
                    if (true) {
                    }
                }
            }
            catch (Exception ex) {
                arg = null;
            }
        }
    }

    // infinispan/commons/.../concurrent/jdk8backported/BoundedEquivalentConcurrentHashMapV8.java
    private void foo97() {
        if (true) {
            String evictedCopy;
            if (true) {
                evictedCopy = "b";
            } else {
                evictedCopy = "c";
                for (int i = 0; i < 0; ++i) {
                }
                evictedCopy = "d";
            }
        }
    }

    // infinispan/core/src/main/java/org/infinispan/util/concurrent/BoundedConcurrentHashMap.java
    private void foo98() {
        if (true) {
            String evictedCopy;
            if (true) {
                evictedCopy = "b";
            } else {
                evictedCopy = "c";
                for (int i = 0; i < 0; ++i) {
                }
                evictedCopy = "d";
            }
        }
    }

    // infinispan/persistence/soft-index/.../infinispan/persistence/sifs/IndexNode.java
    private void foo99() {
        int insertionPoint;
        if (true) {
            insertionPoint = 0;
        } else if (true) {
            insertionPoint = 1;
        } else {
            insertionPoint = 2;
            if (insertionPoint < 0) {
                insertionPoint = -insertionPoint - 1;
            } else {
                insertionPoint++;
            }
        }
    }

    // apache-struts/core/src/main/java/com/opensymphony/xwork2/util/LocalizedTextUtil.java
    private void foo100() {
        String result;
        if (true) {
            result = "a";
        } else {
            result = "b";
            if (result != null) {
            }
            result = "c";
        }
    }
}
