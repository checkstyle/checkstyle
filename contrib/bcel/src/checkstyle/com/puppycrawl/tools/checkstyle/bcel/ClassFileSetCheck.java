//Tested with BCEL-5.1
//http://jakarta.apache.org/builds/jakarta-bcel/release/v5.1/

package com.puppycrawl.tools.checkstyle.bcel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Visitor;
import org.apache.bcel.util.ClassLoaderRepository;
import com.puppycrawl.tools.checkstyle.DefaultContext;
import com.puppycrawl.tools.checkstyle.ModuleFactory;
import com.puppycrawl.tools.checkstyle.api.AbstractFileSetCheck;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.api.Context;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessages;

/**
 * Checks a set of class files using BCEL
 * @author Rick Giles
 */
//TODO: Refactor with AbstractFileSetCheck and TreeWalker
public class ClassFileSetCheck
    extends AbstractFileSetCheck
    implements IObjectSetVisitor
{
    /** visitors for BCEL parse tree walk */
    private final Set mTreeVisitors = new HashSet();

    /** all the registered checks */
    private final Set mAllChecks = new HashSet();

    /** all visitors for IObjectSetVisitor visits */
    private final Set mObjectSetVisitors = new HashSet();

    /** class loader to resolve classes with. **/
    private ClassLoader mClassLoader;

    /** context of child components */
    private Context mChildContext;

    /** a factory for creating submodules (i.e. the Checks) */
    private ModuleFactory mModuleFactory;

    /** Error messages */
    HashMap mMessageMap = new HashMap();

    /**
     * Creates a new <code>ClassFileSetCheck</code> instance.
     * Initializes the acceptable file extensions.
     */
    public ClassFileSetCheck()
    {
        setFileExtensions(new String[]{"class", "jar", "zip"});
    }

    /**
     * Stores the class loader and makes it the Repository's class loader.
     * @param aClassLoader class loader to resolve classes with.
     */
    public void setClassLoader(ClassLoader aClassLoader)
    {
        Repository.setRepository(new ClassLoaderRepository(aClassLoader));
        mClassLoader = aClassLoader;
    }

    /**
     * Sets the module factory for creating child modules (Checks).
     * @param aModuleFactory the factory
     */
    public void setModuleFactory(ModuleFactory aModuleFactory)
    {
        mModuleFactory = aModuleFactory;
    }

    /**
     * Instantiates, configures and registers a Check that is specified
     * in the provided configuration.
     * @see com.puppycrawl.tools.checkstyle.api.AutomaticBean
     */
    public void setupChild(Configuration aChildConf)
        throws CheckstyleException
    {
        // TODO: improve the error handing
        final String name = aChildConf.getName();
        final Object module = mModuleFactory.createModule(name);
        if (!(module instanceof AbstractCheckVisitor)) {
            throw new CheckstyleException(
                "ClassFileSet is not allowed as a parent of " + name);
        }
        final AbstractCheckVisitor c = (AbstractCheckVisitor) module;
        c.contextualize(mChildContext);
        c.configure(aChildConf);
        c.init();

        registerCheck(c);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.Configurable */
    public void finishLocalSetup()
    {
        DefaultContext checkContext = new DefaultContext();
        checkContext.add("classLoader", mClassLoader);
        checkContext.add("messageMap", mMessageMap);
        checkContext.add("severity", getSeverity());

        mChildContext = checkContext;
    }

    /**
     * Register a check.
     * @param aCheck the check to register
     */
    private void registerCheck(AbstractCheckVisitor aCheck)
    {
        mAllChecks.add(aCheck);
    }

    /**
     * @see com.puppycrawl.tools.checkstyle.api.FileSetCheck
     */
    public void process(File[] aFiles)
    {
        registerVisitors();

        // get all the JavaClasses in the files
        final Set javaClasses = extractJavaClasses(aFiles);

        visitSet(javaClasses);

        // walk each Java class parse tree
        final JavaClassWalker walker = new JavaClassWalker();
        walker.setVisitor(getTreeVisitor());
        final Iterator it = javaClasses.iterator();
        while (it.hasNext()) {
            final JavaClass clazz = (JavaClass) it.next();
            visitObject(clazz);
            walker.walk(clazz);
            leaveObject(clazz);
        }

        leaveSet(javaClasses);
        fireErrors();
    }

    /**
     * Gets the visitor for a parse tree walk.
     * @return the visitor for a parse tree walk.
     */
    private Visitor getTreeVisitor()
    {
        return new VisitorSet(mTreeVisitors);
    }

    /**
     * Registers all the visitors for IObjectSetVisitor visits, and for
     * tree walk visits.
     */
    private void registerVisitors()
    {
        mObjectSetVisitors.addAll(mAllChecks);
        final Iterator it = mAllChecks.iterator();
        while (it.hasNext()) {
            final AbstractCheckVisitor check = (AbstractCheckVisitor) it.next();
            final IDeepVisitor visitor = check.getVisitor();
            mObjectSetVisitors.add(visitor);
            mTreeVisitors.add(visitor);
        }
    }

    /**
     * Gets the set of all visitors for all the checks.
     * @return the set of all visitors for all the checks.
     */
    private Set getObjectSetVisitors()
    {
        return mObjectSetVisitors;
    }

    /**
     * Gets the set of all JavaClasses within a set of Files.
     * @param aFiles the set of files to extract from.
     * @return the set of all JavaClasses within aFiles.
     */
    private Set extractJavaClasses(File[] aFiles)
    {
        final Set result = new HashSet();
        final File[] classFiles = filter(aFiles);
        // get Java classes from each filtered file
        for (int i = 0; i < classFiles.length; i++) {
            try {
                final Set extracted = extractJavaClasses(classFiles[i]);
                result.addAll(extracted);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return result;
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void visitSet(Set aSet)
    {
        // register the JavaClasses in the Repository
        Repository.clearCache();
        Iterator it = aSet.iterator();
        while (it.hasNext()) {
            final JavaClass javaClass = (JavaClass) it.next();
            Repository.addClass(javaClass);
        }

        // visit the visitors
        it = getObjectSetVisitors().iterator();
        while (it.hasNext()) {
            final IObjectSetVisitor visitor = (IObjectSetVisitor) it.next();
            visitor.visitSet(aSet);
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void visitObject(Object aObject)
    {
        final Iterator it = getObjectSetVisitors().iterator();
        while (it.hasNext()) {
            final IObjectSetVisitor visitor = (IObjectSetVisitor) it.next();
            visitor.visitObject(aObject);
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void leaveObject(Object aObject)
    {
        final Iterator it = getObjectSetVisitors().iterator();
        while (it.hasNext()) {
            final IObjectSetVisitor visitor = (IObjectSetVisitor) it.next();
            visitor.leaveObject(aObject);
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.bcel.IObjectSetVisitor */
    public void leaveSet(Set aSet)
    {
        final Iterator it = getObjectSetVisitors().iterator();
        while (it.hasNext()) {
            final IObjectSetVisitor visitor = (IObjectSetVisitor) it.next();
            visitor.leaveSet(aSet);
        }
    }

    /**
     * Extracts the JavaClasses from .class, .zip, and .jar files.
     * @param aFile the file to extract from.
     * @return the set of JavaClasses from aFile.
     * @throws IOException if there is an error.
     */
    private Set extractJavaClasses(File aFile)
        throws IOException
    {
        final Set result = new HashSet();
        final String fileName = aFile.getPath();
        if (fileName.endsWith(".jar") || fileName.endsWith(".zip")) {
            final ZipFile zipFile = new ZipFile(fileName);
            final Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                final ZipEntry entry = (ZipEntry) entries.nextElement();
                final String entryName = entry.getName();
                if (entryName.endsWith(".class")) {
                    final InputStream in = zipFile.getInputStream(entry);
                    final JavaClass javaClass =
                        new ClassParser(in, entryName).parse();
                    result.add(javaClass);
                }
            }
        }
        else {
            final JavaClass javaClass = new ClassParser(fileName).parse();
            result.add(javaClass);
        }
        return result;
    }

    /**
     * Notify all listeners about the errors in a file.
     * Calls <code>MessageDispatcher.fireErrors()</code> with
     * all logged errors and than clears errors' list.
     */
    private void fireErrors()
    {
        Set keys = mMessageMap.keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            getMessageDispatcher().fireFileStarted(key);
            LocalizedMessages localizedMessages = (LocalizedMessages) mMessageMap.get(key);
            final LocalizedMessage[] errors = localizedMessages.getMessages();
            localizedMessages.reset();
            getMessageDispatcher().fireErrors(key, errors);
            getMessageDispatcher().fireFileFinished(key);
        }
    }

}
