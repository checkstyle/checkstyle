/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @deprecated, @exception, @param, @return, \
           @see, @serial, @serialData, @serialField, @since, @throws, @version


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.awt.*;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Type;
import java.util.Set;

import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public interface InputAtclauseOrderNewArrayDeclaratorStructure
        <D extends GenericDeclaration> extends Type, AnnotatedElement {
     /**
      * Returns an array of {@code Type} objects representing the
      * upper bound(s) of this type variable.  If no upper bound is
      * explicitly declared, the upper bound is {@code Object}.
      *
      * <p>For each upper bound B: <ul> <li>if B is a parameterized
      * type or a type variable, it is created, (see {@link
      * java.lang.reflect.ParameterizedType ParameterizedType} for the
      * details of the creation process for parameterized types).
      * <li>Otherwise, B is resolved.  </ul>
      *
      * @throws TypeNotPresentException  if any of the
      *     bounds refers to a non-existent type declaration
      * @throws MalformedParameterizedTypeException if any of the
      *     bounds refer to a parameterized type that cannot be instantiated
      *     for any reason
      * @return an array of {@code Type}s representing the upper  // violation
      *     bound(s) of this type variable
      */
     Type[] getBounds();

      /**
       * Obtains a list of prepared transaction branches from a resource
       * manager. The transaction manager calls this method during recovery
       * to obtain the list of transaction branches that are currently in
       * prepared or heuristically completed states.
       *
       * @param flag One of TMSTARTRSCAN, TMENDRSCAN, TMNOFLAGS. TMNOFLAGS
       * must be used when no other flags are set in the parameter.
       *
       * @exception XAException An error has occurred. Possible values are
       * XAER_RMERR, XAER_RMFAIL, XAER_INVAL, and XAER_PROTO.
       *
       * @return The resource manager returns zero or more XIDs of the   // violation
       * transaction branches that are currently in a prepared or
       * heuristically completed state. If an error occurs during the
       * operation, the resource manager should throw the appropriate
       * XAException.
       */
      Xid[] recover(int flag) throws XAException;

}

class Other {
    /**
     * The focus traversal keys. These keys will generate focus traversal
     * behavior for Components for which focus traversal keys are enabled. If a
     * value of null is specified for a traversal key, this Component inherits
     * that traversal key from its parent. If all ancestors of this Component
     * have null specified for that traversal key, then the current
     * KeyboardFocusManager's default traversal key is used.
     *
     * @serial
     * @see #setFocusTraversalKeys      // violation
     * @see #getFocusTraversalKeys      // violation
     * @since 1.4                       // violation
     */
    Set<AWTKeyStroke>[] focusTraversalKeys;
}
