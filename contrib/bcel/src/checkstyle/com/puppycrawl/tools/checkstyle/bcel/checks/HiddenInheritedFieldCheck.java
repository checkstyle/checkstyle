//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.bcel.ReferenceVisitor;

/**
 * Checks for fields that hide fields in superclasses
 * @author Daniel Grenner
 */
public class HiddenInheritedFieldCheck
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
        final Field[] fields = javaClass.getFields();
        // Check all fields
        for (int i = 0; i < fields.length; i++) {
            final Field field = fields[i];
            // Go through all superclasses
            for (int j = 0; j < superClasses.length; j++) {
                final JavaClass superClass = superClasses[j];
                final String superClassName = superClass.getClassName();
                final Field[] superClassFields = superClass.getFields();
                // Go through the filds of the superclasses
                for (int k = 0; k < superClassFields.length; k++) {
                    final Field superClassField = superClassFields[k];
                    if (!superClassField.isPrivate() &&
                        superClassField.getName().equals(field.getName()) &&
                        !ignore(className, field)) {
                        log(
                            javaClass,
                            0,
                            "hidden.inherited.field",
                            new Object[] {fields[i], superClassName});
                    }
                }
            }
        }
    }

    /** @see AbstractReferenceCheck */
    public boolean ignore(String aClassName, Field aField) {
        final String fieldName = aField.toString();
        return (/*super.ignore(aClassName, aField)
                || */fieldName.indexOf("[Synthetic]") > -1);
    }
}
