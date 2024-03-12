//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.classtypeparametername;

public record InputRecordTypeParameterName<t>(Integer x, String str) { // warn

}
