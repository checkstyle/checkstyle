package com.puppycrawl.tools.checkstyle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

/**
 * Loads a list of package names from a package name XML file.
 * @author Rick Giles
 * @version 4-Dec-2002
 */
public class PackageNamesLoader extends DefaultHandler
{
    /** Name of default checkstyle package names resource file .
     * The file must be in the classpath.
     */
    private static final String DEFAULT_PACKAGES =
        "checkstyle_packages.xml";
            
    /** list of class names */
    private final List mPackageNames = new ArrayList();
    
    /** The loaded package names */   
    private Stack mPackageStack = new Stack();
    
    /** parser to read XML files **/
    private XMLReader mParser;
    
    /**
     * Creates a new <code>PackageNameLoader</code> instance.
     * @throws ParserConfigurationException if an error occurs
     * @throws SAXException if an error occurs
     */
    private PackageNamesLoader()
        throws ParserConfigurationException, SAXException
    {
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        mParser = factory.newSAXParser().getXMLReader();
        mParser.setContentHandler(this);
    }
    
    /**
     * Returns the set of package names in the last file parsed.
     * @return the name to class name map.
     */
    private String[] getPackageNames()
    {
        return (String[]) mPackageNames.
            toArray(new String[mPackageNames.size()]);
    }

    /**
     * Parses the specified file, loading its package names.
     * @param aFilename the file to parse
     * @throws FileNotFoundException if an error occurs
     * @throws IOException if an error occurs
     * @throws SAXException if an error occurs
     */
    private void parseInputSource(InputSource aInputSource)
        throws FileNotFoundException, IOException, SAXException
    {
        mPackageStack.clear();
        mParser.parse(aInputSource);
    }
        
    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void startElement(String aNamespaceURI,
                             String aLocalName,
                             String aQName,
                             Attributes aAtts)
            throws SAXException
    {
        if (aQName.equals("package")) {
            //push package name
            final String name = (String) aAtts.getValue("name");
            if (name == null) {
                throw new SAXException("missing package name");
            }
            mPackageStack.push(name);
        }
   }
    
    /**
     * Creates a full package name from the package names on the stack.
     * @return the full name of the current package.
     */ 
    private String getPackageName()
    {
        if (mPackageStack.isEmpty()) {
            return "";
        }
        final StringBuffer buf = new StringBuffer();
        final Iterator it = mPackageStack.iterator();
        while (it.hasNext()) {
            String subPackage = (String) it.next();
            if (!subPackage.endsWith(".")) {
                subPackage += ".";
            }
            buf.append(subPackage);
        }
        return buf.toString();
    }
    
    /** @see org.xml.sax.helpers.DefaultHandler **/
    public void endElement(String aNamespaceURI,
                           String aLocalName,
                           String aQName)
    {
        if (aQName.equals("package")) {
            mPackageNames.add(getPackageName());
            mPackageStack.pop();
        }
    }

    /**
     * Returns the default list of package names.
     * @param aClassLoader the class loader that gets the
     * default package names.
     * @return the default list of package names.
     * @throws CheckstyleException if an error occurs.
     */
    public static String[] loadPackageNames(ClassLoader aClassLoader)
        throws CheckstyleException
    {

        final InputStream stream =
            aClassLoader.getResourceAsStream(DEFAULT_PACKAGES);
        final InputSource source = new InputSource(stream);
        return loadPackageNames(source, "default package names");
    }

    /**
     * Returns the package names in a specified file.
     * @param aFilename name of the package file.
     * @return the list of package names stored in the
     * map file. 
     * @throws CheckstyleException if an error occurs.
     */      
    public static String[] loadPackageNames(String aFilename)
        throws CheckstyleException
    {
        FileReader reader = null;
        try {
            reader = new FileReader(aFilename);
        }
        catch (FileNotFoundException e) {
            throw new CheckstyleException("unable to find " + aFilename);
        }
        final InputSource source = new InputSource(reader);
        return loadPackageNames(source, aFilename);
    }
    
    /**
     * Returns the list of package names in a specified source.
     * @param aSource the source for the list.
     * @param aSourceName the name of the source.
     * @return the list ofpackage names stored in aSource. 
     * @throws CheckstyleException if an error occurs.
     */          
    private static String[] loadPackageNames(InputSource aSource,
            String aSourceName)
        throws CheckstyleException
    {
        try {
            final PackageNamesLoader nameLoader = new PackageNamesLoader();
            nameLoader.parseInputSource(aSource);
            return nameLoader.getPackageNames();
        }
        catch (FileNotFoundException e) {
            throw new CheckstyleException("unable to find " + aSourceName);
        }
        catch (ParserConfigurationException e) {
            throw new CheckstyleException("unable to parse " + aSourceName);
        }
        catch (SAXException e) {
            throw new CheckstyleException("unable to parse "
                    + aSourceName + " - " + e.getMessage());
        }
        catch (IOException e) {
            throw new CheckstyleException("unable to read " + aSourceName);
        }
    }
}
