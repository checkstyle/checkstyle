//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import java.util.Iterator;
import java.util.Set;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.Type;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.bcel.ReferenceVisitor;

/**
 * Checks for fields that hide fields in superclasses
 * @author Daniel Grenner
 */
public class HiddenStaticMethodCheck
    extends AbstractReferenceCheck
{
    /** @see AbstractReferenceCheck */
    public void setScope(String aFrom)
    {
        super.setScope(aFrom);
        ((ReferenceVisitor) getVisitor()).addFieldScope(
            Scope.getInstance(aFrom));
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void visitObject(Object aJavaClass)
    {
        final JavaClass javaClass = (JavaClass) aJavaClass;
        final String className = javaClass.getClassName();
        final JavaClass[] superClasses = javaClass.getSuperClasses();
        final Method[] methods = javaClass.getMethods();
        // Check all methods
        for (int i = 0; i < methods.length; i++) {
            final Method method = methods[i];
            // Check that the method is a possible match
            if (!method.isPrivate() && method.isStatic())  {
                // Go through all their superclasses
                for (int j = 0; j < superClasses.length; j++) {
                    final JavaClass superClass = superClasses[j];
                    final String superClassName = superClass.getClassName();
                    final Method[] superClassMethods = superClass.getMethods();
                    // Go through the methods of the superclasses
                    for (int k = 0; k < superClassMethods.length; k++) {
                        final Method superClassMethod = superClassMethods[k];
                        if (superClassMethod.getName().equals(method.getName()) &&
                            !ignore(className, method)) {
                            Type[] methodTypes = method.getArgumentTypes();
                            Type[] superTypes = superClassMethod.
                                getArgumentTypes();
                            if (methodTypes.length == superTypes.length) {
                                boolean match = true;
                                for (int arg = 0; arg < methodTypes.length; arg++) {
                                    if (!methodTypes[arg].equals(superTypes[arg])) {
                                        match = false;
                                    }
                                }
                                // Same method parameters
                                if (match) {
                                    log(
                                        javaClass,
                                        0,
                                        "hidden.static.method",
                                        new Object[] {method, superClassName});
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /** @see AbstractReferenceCheck */
    public boolean ignore(String aClassName, Method aMethod) {
        final String methodName = aMethod.getName();
        return (/*super.ignore(aClassName, aMethod)
                || */methodName.equals("<init>")
                || methodName.equals("<clinit>")
                || methodName.equals("class$")
                || aMethod.toString().indexOf("[Synthetic]") > -1);
    }

}
