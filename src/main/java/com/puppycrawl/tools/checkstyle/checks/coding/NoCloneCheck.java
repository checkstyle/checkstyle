////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.coding;

/**
 * <p>
 * Checks that the clone method is not overridden from the
 * Object class.
 * </p>
 *
 * <p>Rationale: The clone method relies on strange/hard to follow rules that
 * do not work it all situations.  Consequently, it is difficult to
 * override correctly.  Below are some of the rules/reasons why the clone
 * method should be avoided.
 *
 * <ul>
 * <li>
 * Classes supporting the clone method should implement the Cloneable
 * interface but the Cloneable interface does not include the clone method.
 * As a result, it doesn't enforce the method override.
 * </li>
 * <li>
 * The Cloneable interface forces the Object's clone method to work
 * correctly. Without implementing it, the Object's clone method will
 * throw a CloneNotSupportedException.
 * </li>
 * <li>
 * Non-final classes must return the object returned from a call to
 * super.clone().
 * </li>
 * <li>
 * Final classes can use a constructor to create a clone which is different
 * from non-final classes.
 * </li>
 * <li>
 * If a super class implements the clone method incorrectly all subclasses
 * calling super.clone() are doomed to failure.
 * </li>
 * <li>
 * If a class has references to mutable objects then those object
 * references must be replaced with copies in the clone method
 * after calling super.clone().
 * </li>
 * <li>
 * The clone method does not work correctly with final mutable object
 * references because final references cannot be reassigned.
 * </li>
 * <li>
 * If a super class overrides the clone method then all subclasses must
 * provide a correct clone implementation.
 * </li>
 * </ul>
 *
 * <p>Two alternatives to the clone method, in some cases, is a copy constructor
 * or a static factory method to return copies of an object. Both of these
 * approaches are simpler and do not conflict with final fields. They do not
 * force the calling client to handle a CloneNotSupportedException.  They also
 * are typed therefore no casting is necessary. Finally, they are more
 * flexible since they can take interface types rather than concrete classes.
 *
 * <p>Sometimes a copy constructor or static factory is not an acceptable
 * alternative to the clone method.  The example below highlights the
 * limitation of a copy constructor (or static factory). Assume
 * Square is a subclass for Shape.
 *
 * <pre>
 * Shape s1 = new Square();
 * System.out.println(s1 instanceof Square); //true
 * </pre>
 * ...assume at this point the code knows nothing of s1 being a Square
 *    that's the beauty of polymorphism but the code wants to copy
 *    the Square which is declared as a Shape, its super type...
 *
 * <pre>
 * Shape s2 = new Shape(s1); //using the copy constructor
 * System.out.println(s2 instanceof Square); //false
 * </pre>
 * The working solution (without knowing about all subclasses and doing many
 * casts) is to do the following (assuming correct clone implementation).
 *
 * <pre>
 * Shape s2 = s1.clone();
 * System.out.println(s2 instanceof Square); //true
 * </pre>
 * Just keep in mind if this type of polymorphic cloning is required
 * then a properly implemented clone method may be the best choice.
 *
 * <p>Much of this information was taken from Effective Java:
 * Programming Language Guide First Edition by Joshua Bloch
 * pages 45-52.  Give Bloch credit for writing an excellent book.
 * </p>
 *
 * <p>This check is almost exactly the same as the {@link NoFinalizerCheck}
 *
 * @author Travis Schneeberger
 * @see Object#clone()
 */
public class NoCloneCheck extends AbstractIllegalMethodCheck {

    /**
     * A key is pointing to the warning message text in "messages.properties"
     * file.
     */
    public static final String MSG_KEY = "avoid.clone.method";

    /**
     * Creates an instance.
     */
    public NoCloneCheck() {
        super("clone", MSG_KEY);
    }
}
