////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
package com.puppycrawl.tools.checkstyle;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import com.puppycrawl.tools.checkstyle.api.Check;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.BooleanArrayConverter;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.ByteArrayConverter;
import org.apache.commons.beanutils.converters.CharacterConverter;
import org.apache.commons.beanutils.converters.CharacterArrayConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.DoubleArrayConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.FloatArrayConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.LongArrayConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.apache.commons.beanutils.converters.ShortArrayConverter;
import org.apache.commons.beanutils.converters.StringArrayConverter;

/**
 * Represents the configuration for a check.
 *
 * @author <a href="mailto:checkstyle@puppycrawl.com">Oliver Burn</a>
 * @version 1.0
 */
class CheckConfiguration
{
    static
    {
        initConverters();
    }

    /**
     * Setup the jakarta-commons-beanutils type converters so they throw
     * a ConversionException instead of using the default value.
     */
    private static void initConverters()
    {
        // TODO: is there a smarter way to tell beanutils not to use defaults?

        boolean booleanArray[] = new boolean[0];
        byte byteArray[] = new byte[0];
        char charArray[] = new char[0];
        double doubleArray[] = new double[0];
        float floatArray[] = new float[0];
        int intArray[] = new int[0];
        long longArray[] = new long[0];
        short shortArray[] = new short[0];

        ConvertUtils.register(new BooleanConverter(), Boolean.TYPE);
        ConvertUtils.register(new BooleanConverter(), Boolean.class);
        ConvertUtils.register(new BooleanArrayConverter(),
                booleanArray.getClass());
        ConvertUtils.register(new ByteConverter(), Byte.TYPE);
        ConvertUtils.register(new ByteConverter(), Byte.class);
        ConvertUtils.register(new ByteArrayConverter(byteArray),
                byteArray.getClass());
        ConvertUtils.register(new CharacterConverter(), Character.TYPE);
        ConvertUtils.register(new CharacterConverter(), Character.class);
        ConvertUtils.register(new CharacterArrayConverter(),
                charArray.getClass());
        ConvertUtils.register(new DoubleConverter(), Double.TYPE);
        ConvertUtils.register(new DoubleConverter(), Double.class);
        ConvertUtils.register(new DoubleArrayConverter(doubleArray),
                doubleArray.getClass());
        ConvertUtils.register(new FloatConverter(), Float.TYPE);
        ConvertUtils.register(new FloatConverter(), Float.class);
        ConvertUtils.register(new FloatArrayConverter(),
                floatArray.getClass());
        ConvertUtils.register(new IntegerConverter(), Integer.TYPE);
        ConvertUtils.register(new IntegerConverter(), Integer.class);
        ConvertUtils.register(new IntegerArrayConverter(),
                intArray.getClass());
        ConvertUtils.register(new LongConverter(), Long.TYPE);
        ConvertUtils.register(new LongConverter(), Long.class);
        ConvertUtils.register(new LongArrayConverter(), longArray.getClass());
        ConvertUtils.register(new ShortConverter(), Short.TYPE);
        ConvertUtils.register(new ShortConverter(), Short.class);
        ConvertUtils.register(new ShortArrayConverter(),
                shortArray.getClass());
        ConvertUtils.register(new StringArrayConverter(),
                        String[].class );
        ConvertUtils.register(new IntegerArrayConverter(),
                        Integer[].class );

        // BigDecimal, BigInteger, Class, Date, String, Time, TimeStamp
        // do not use defaults in the default configuration of ConvertUtils

    }

    /** the classname for the check */
    private String mClassname;
    /** the tokens the check is interested in */
    private final Set mTokens = new HashSet();
    /** the properties for the check */
    private final Map mProperties = new HashMap();


    /**
     * Set the classname of the check.
     * @param aClassname the classname for the check
     */
    void setClassname(String aClassname)
    {
        mClassname = aClassname;
    }

    /**
     * Adds a set of tokens the check is interested in. The string is a comma
     * separated list of token names.
     * @param aStrRep the string representation of the tokens interested in
     */
    void addTokens(String aStrRep)
    {
        final String trimmed = aStrRep.trim();
        if (trimmed.length() == 0) {
            return;
        }

        final StringTokenizer st = new StringTokenizer(trimmed, ",");
        while (st.hasMoreTokens()) {
            mTokens.add(st.nextToken().trim());
        }
    }

    /**
     * Returns the tokens registered for the check.
     * @return the set of token names
     */
    Set getTokens()
    {
        return mTokens;
    }

    /**
     * Adds a property for the check.
     * @param aName name of the property
     * @param aValue value of the property
     */
    void addProperty(String aName, String aValue)
    {
        mProperties.put(aName, aValue);
    }

    /**
     * Create an instance of the check that is properly initialised.
     *
     * @param aLoader the <code>ClassLoader</code> to create the instance with
     * @return the created check
     * @throws ClassNotFoundException if an error occurs
     * @throws InstantiationException if an error occurs
     * @throws IllegalAccessException if an error occurs
     * @throws InvocationTargetException if an error occurs
     * @throws NoSuchMethodException if an error occurs
     */
    Check createInstance(ClassLoader aLoader)
        throws ClassNotFoundException, InstantiationException,
        IllegalAccessException, InvocationTargetException,
        NoSuchMethodException
    {
        final Class clazz = Class.forName(mClassname, true, aLoader);
        final Check check = (Check) clazz.newInstance();
        // TODO: need to set the properties
        // Loop setting the properties
        final Iterator keyIt = mProperties.keySet().iterator();
        while (keyIt.hasNext()) {
            final String key = (String) keyIt.next();
            final String value = (String) mProperties.get(key);
            BeanUtils.copyProperty(check, key, value);
        }
        return check;
    }
}
