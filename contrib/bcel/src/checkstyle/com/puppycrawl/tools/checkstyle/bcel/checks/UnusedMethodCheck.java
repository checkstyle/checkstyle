//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import java.util.Iterator;
import java.util.Set;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import com.puppycrawl.tools.checkstyle.bcel.classfile.JavaClassDefinition;
import com.puppycrawl.tools.checkstyle.bcel.classfile.MethodDefinition;

/**
 * Checks for unused methods
 * @author Rick Giles
 */
public class UnusedMethodCheck
    extends AbstractReferenceCheck
{
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
                    final String methodName = methodDefs[i].getName();
                    final Method method = methodDefs[i].getMethod();
                    if (!ignore(className, method))
                    {
                        log(
                            0,
                            "unused.method",
                            new Object[] {className, methodDefs[i]});
                    }
                }
            }
        }
    }

    /**
     * Determines whether a class name and Method should be ignored.
     * Normally the Method is a Method of the named class.
     * @param aClassName the class name.
     * @param aMethod the Method.
     * @return true if aClassName and aMethod should be ignored.
     */    
    protected boolean ignore(String aClassName, Method aMethod)
    {
        return ignore(aClassName, aMethod.getName());
    }
        
    /** @see AbstractReferenceCheck */
    public boolean ignore(String aClassName, String aMethodName)
    {
        return (super.ignore(aClassName, aMethodName)
            || aMethodName.equals("<init>")
            || aMethodName.equals("<clinit>"));
    }
}