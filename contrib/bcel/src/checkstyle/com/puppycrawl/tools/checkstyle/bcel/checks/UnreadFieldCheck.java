//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel.checks;

import java.util.Iterator;
import java.util.Set;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.bcel.ReferenceVisitor;
import com.puppycrawl.tools.checkstyle.bcel.classfile.FieldDefinition;
import com.puppycrawl.tools.checkstyle.bcel.classfile.JavaClassDefinition;

/**
 * Checks for unread, non-final fields
 * @author Rick Giles
 */
public class UnreadFieldCheck
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
    public void leaveSet(Set aJavaClasses)
    {
        final Iterator it = aJavaClasses.iterator();
        while (it.hasNext()) {
            final JavaClass javaClass = (JavaClass) it.next();
            final String className = javaClass.getClassName();
            final JavaClassDefinition classDef = findJavaClassDef(javaClass);
            final FieldDefinition[] fieldDefs = classDef.getFieldDefs();
            for (int i = 0; i < fieldDefs.length; i++) {
                if (fieldDefs[i].getReadReferenceCount() == 0) {
                    final Field field = fieldDefs[i].getField();
                    if (!field.isFinal()
                        && (!ignore(className, field))
                        )
                    {
                        log(
                            javaClass,
                            0,
                            "unread.field",
                            new Object[] {fieldDefs[i]});
                    }
                }
            }
        }
    }
}
