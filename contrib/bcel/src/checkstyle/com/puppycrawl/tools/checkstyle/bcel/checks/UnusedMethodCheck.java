//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import java.util.Iterator;
import java.util.Set;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.bcel.ReferenceVisitor;
import com.puppycrawl.tools.checkstyle.bcel.classfile.JavaClassDefinition;
import com.puppycrawl.tools.checkstyle.bcel.classfile.MethodDefinition;

/**
 * Checks for unused methods
 * @author Rick Giles
 */
public class UnusedMethodCheck
    extends AbstractReferenceCheck
{
    /** @see AbstractReferenceCheck */
    public void setScope(String aScopeName)
    {
        super.setScope(aScopeName);
        ((ReferenceVisitor) getVisitor()).addMethodScope(
            Scope.getInstance(aScopeName));
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void leaveSet(Set aJavaClasses)
    {
        final Iterator it = aJavaClasses.iterator();
        while (it.hasNext()) {
            final JavaClass javaClass = (JavaClass) it.next();
            final String className = javaClass.getClassName();
            final JavaClassDefinition classDef = findJavaClassDef(javaClass);
            final MethodDefinition[] methodDefs = classDef.getMethodDefs();
            for (int i = 0; i < methodDefs.length; i++) {
                if (!classDef.hasReference(methodDefs[i], getReferenceDAO())) {
                    final Method method = methodDefs[i].getMethod();
                    if (!ignore(className, method)) {
                        log(
                            javaClass,
                            0,
                            "unused.method",
                            new Object[] {methodDefs[i]});
                    }
                }
            }
        }
    }

    /** @see AbstractReferenceCheck */
    public boolean ignore(String aClassName, Method aMethod)
    {
        final String methodName = aMethod.getName();
        return (super.ignore(aClassName, aMethod)
            || methodName.equals("<init>")
            || methodName.equals("<clinit>"));
    }
}
