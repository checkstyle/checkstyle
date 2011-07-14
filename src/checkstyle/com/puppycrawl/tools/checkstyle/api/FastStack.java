////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

/**
 * Simple implementation of a LIFO Stack that can be used instead of
 * {@link java.util.Vector} which is <tt>synchronized</tt>.
 * @author oliverb
 * @param <E> The type to hold.
 */
public class FastStack<E> implements Iterable<E>
{
    /** Hold the entries in the stack. */
    private final List<E> mEntries = Lists.newArrayList();

    /**
     * Pushes the supplied element onto the stack.
     * @param aElement the element to push onto the stack.
     */
    public void push(E aElement)
    {
        mEntries.add(aElement);
    }

    /**
     * Returns whether the stack is empty.
     * @return whether the stack is empty.
     */
    public boolean isEmpty()
    {
        return mEntries.isEmpty();
    }

    /**
     * Returns the number of entries in the stack.
     * @return the number of entries in the stack.
     */
    public int size()
    {
        return mEntries.size();
    }

    /**
     * Returns the entry at the top of the stack without removing it.
     * @return the top entry
     * @throws IllegalStateException if the stack is empty.
     */
    public E peek()
    {
        if (mEntries.isEmpty()) {
            throw new IllegalStateException("FastStack is empty");
        }
        return mEntries.get(mEntries.size() - 1);
    }

    /**
     * Returns the entry at the top of the stack by removing it.
     * @return the top entry
     * @throws IllegalStateException if the stack is empty.
     */
    public E pop()
    {
        if (mEntries.isEmpty()) {
            throw new IllegalStateException("FastStack is empty");
        }
        return mEntries.remove(mEntries.size() - 1);
    }

    /**
     * Return the element at the specified index. It does not remove the
     * element from the stack.
     * @param aIndex the index to return
     * @return the element at the index
     * @throws IllegalArgumentException if index out of range
     */
    public E peek(int aIndex)
    {
        if ((aIndex < 0) || (aIndex >= mEntries.size())) {
            throw new IllegalArgumentException("index out of range.");
        }
        return mEntries.get(aIndex);
    }

    /**
     * Returns if the stack contains the specified element.
     * @param aElement the element to find
     * @return whether the stack contains the entry
     */
    public boolean contains(E aElement)
    {
        return mEntries.contains(aElement);
    }

    /**
     * Clears the stack.
     */
    public void clear()
    {
        mEntries.clear();
    }

    /**
     * Returns an iterator that goes from the oldest element to the newest.
     * @return an iterator
     */
    public Iterator<E> iterator()
    {
        return mEntries.iterator();
    }

    /**
     * Factory method to create a new instance.
     * @param <T> the type of elements to hold in the stack.
     * @return a new instance of {@link FastStack}
     */
    public static <T> FastStack<T> newInstance()
    {
        return new FastStack<T>();
    }
}
