package com.puppycrawl.tools.checkstyle.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FastStackTest
{
    @Test(expected = IllegalStateException.class)
    public void testPeek()
    {
        new FastStack<String>().peek();
    }

    @Test(expected = IllegalStateException.class)
    public void testPop()
    {
        new FastStack<String>().pop();
    }

    @Test
    public void testNormal()
    {
        final FastStack<Integer> fs = FastStack.newInstance();
        assertNotNull(fs);
        assertTrue(fs.isEmpty());
        assertEquals(0, fs.size());
        final int num = 100;
        for (int i = 0; i < num; i++)
        {
            fs.push(i);
        }
        assertEquals(num, fs.size());
        assertFalse(fs.isEmpty());
        assertEquals(1, fs.get(1).intValue());
        assertEquals(num - 1, fs.peek().intValue());
        assertEquals(num, fs.size());
        for (int i = 0; i < num; i++)
        {
            fs.pop();
        }
        assertTrue(fs.isEmpty());
        assertEquals(0, fs.size());

        fs.push(666);
        assertEquals(1, fs.size());
        assertFalse(fs.isEmpty());
        assertTrue(fs.contains(666));
        assertFalse(fs.contains(667));
        fs.clear();
        assertFalse(fs.contains(666));
        assertTrue(fs.isEmpty());
        assertEquals(0, fs.size());
        assertFalse(fs.iterator().hasNext());
    }
}
