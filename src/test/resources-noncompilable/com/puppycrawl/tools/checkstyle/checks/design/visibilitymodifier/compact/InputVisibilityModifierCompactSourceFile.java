/*
VisibilityModifier
packageAllowed = (default)false
protectedAllowed = (default)false
publicMemberPattern = ^f[A-Z][a-zA-Z0-9]*$
allowPublicFinalFields = (default)false
allowPublicImmutableFields = (default)false
immutableClassCanonicalNames = (default)java.io.File, java.lang.Boolean, java.lang.Byte, \
                               java.lang.Character, java.lang.Double, java.lang.Float, \
                               java.lang.Integer, java.lang.Long, java.lang.Short, \
                               java.lang.StackTraceElement, java.lang.String, \
                               java.math.BigDecimal, java.math.BigInteger, \
                               java.net.Inet4Address, java.net.Inet6Address, \
                               java.net.InetSocketAddress, java.net.URI, java.net.URL, \
                               java.util.Locale, java.util.UUID
ignoreAnnotationCanonicalNames = (default)com.google.common.annotations.VisibleForTesting, \
                                 org.junit.ClassRule, org.junit.Rule


*/

// non-compiled with javac: Compilable with Java25

public int publicField = 1;
protected int protectedField = 2;
int packageField = 3;
private int privateField = 4;

void main() {
    int localVariable = privateField;
}
