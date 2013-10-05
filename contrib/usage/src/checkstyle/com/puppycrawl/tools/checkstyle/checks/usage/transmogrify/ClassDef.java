
// Transmogrify License
// 
// Copyright (c) 2001, ThoughtWorks, Inc.
// All rights reserved.
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// - Redistributions of source code must retain the above copyright notice,
//   this list of conditions and the following disclaimer.
// - Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
// Neither the name of the ThoughtWorks, Inc. nor the names of its
// contributors may be used to endorse or promote products derived from this
// software without specific prior written permission.
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
// TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
// PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
// CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
// PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
// OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
// WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
// OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
// ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.puppycrawl.tools.checkstyle.checks.usage.transmogrify;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;



/**
 * <code>ClassDef</code> contains all the information needed to
 * represent a java class or interface.  This includes the superclass,
 * whether it's a class or an interface, the interfaces it implements,
 * a list of its (direct?) subclasses, and the classes that implement
 * it if it is an interface
 *
 * @see Scope
 */
public class ClassDef extends DefaultScope implements IClass {
    private long id = 0;

    private IClass superclass = null;
    private List interfaces = new Vector();

    private List subclasses = new Vector();
    private List implementors = new Vector();

    private Set importedPackages = new HashSet();

    // variable definitions will use elements from parent
    private Set methods = new HashSet();

    private Hashtable imports = new Hashtable();
    private Vector unprocessedImports = null;

    protected MethodDef _defaultConstructor;

    public ClassDef(String name, Scope parentScope, SymTabAST node) {
        super(name, parentScope, node);
        _defaultConstructor = new DefaultConstructor(this);
        addDefinition(_defaultConstructor);
    }

    public long getNextAnonymousId() {
        return ++id;
    }

    public void setSuperclass(IClass newSuperclass) {
        this.superclass = newSuperclass;
    }

    public IClass getSuperclass() {
        return superclass;
    }

    public void addUnprocessedImports(Vector aImports) {
        unprocessedImports = (Vector) (aImports.clone());
    }

    public Vector getUnprocessedImports() {
        return unprocessedImports;
    }

    public void importPackage(IPackage pkg) {
        importedPackages.add(pkg);
    }

    public void importClass(IClass imported) {
        imports.put(imported.getName(), imported);
    }

    // begin definitions interface

    public void addDefinition(MethodDef method) {
        if (method.getName().equals(getName())) {
            methods.remove(_defaultConstructor);
        }
        methods.add(method);
    }

    protected Enumeration getDefinitions() {
        Vector allElements = new Vector();

        allElements.addAll(elements.values());
        allElements.addAll(methods);
        allElements.addAll(labels.values());
        allElements.addAll(classes.values());

        return allElements.elements();
    }

    public IClass getClassDefinition(String name) {
        IClass result = null;

        result = (ClassDef) (classes.get(name));

        if (result == null) {
            result = (IClass) (imports.get(name));
        }

        if (result == null) {
            Iterator it = importedPackages.iterator();
            while (it.hasNext() && result == null) {
                IPackage pkg = (IPackage) it.next();
                result = pkg.getClass(name);
            }
        }

        if (result == null) {
            result = getParentScope().getClassDefinition(name);
        }
        
        //TODO: check for a class in the same package?
        if (result == null) {
            final String packageName = getParentScope().getQualifiedName();
            final String fullName = packageName + "." + name;
            Class theClass = null;
            try {
                theClass = ClassManager.getClassLoader().loadClass(fullName);
                result = new ExternalClass(theClass);
            }
            catch (ClassNotFoundException e) {
                // no-op
            }
        }
         
        return result;
    }

    public IMethod getMethodDefinition(String name, ISignature signature) {
        IMethod result = null;

        result = getDeclaredMethod(name, signature);

        if (result == null) {
            result = getMostCompatibleMethod(name, signature);
        }

        if (result == null) {
            if (superclass != null) {
                result = superclass.getMethodDefinition(name, signature);
            }
        }

        if (result == null) {
            IClass[] myInterfaces = getInterfaces();
            for (int index = 0;
                index < myInterfaces.length && result == null;
                index++) {
                result = myInterfaces[index].getMethodDefinition(name, signature);
            }
        }

        // not sure why this is here -- inner classes, maybe?
        // regardless, write better
        if (result == null) {
            if (getParentScope() != null) {
                result = getParentScope().getMethodDefinition(name, signature);
            }
        }

        return result;
    }

    public IMethod getMostCompatibleMethod(String name, ISignature signature) {
        IMethod result = null;

        SortedSet compatibleMethods =
            new TreeSet(new MethodSpecificityComparator());

        Iterator it = methods.iterator();
        while (it.hasNext()) {
            MethodDef method = (MethodDef) it.next();
            if (name.equals(method.getName())) {
                if (method.hasCompatibleSignature(signature)) {
                    compatibleMethods.add(method);
                }
            }
        }

        if (!compatibleMethods.isEmpty()) {
            result = (IMethod) compatibleMethods.first();
        }

        return result;
    }

    public IMethod getDeclaredMethod(String name, ISignature signature) {
        // finds methods declared by this class with the given signature

        IMethod result = null;

        Iterator it = methods.iterator();
        while (it.hasNext()) {
            MethodDef method = (MethodDef) it.next();
            if (name.equals(method.getName())) {
                if (method.hasSameSignature(signature)) {
                    result = method;
                    break;
                }
            }
        }

        return result;
    }

    public IVariable getVariableDefinition(String name) {
        IVariable result = null;

        // in keeping with getField in java.lang.Class
        // 1) current class
        // 2) direct superinterfaces
        // 3) superclass
        // then we do the parent scope in case its an inner class

        result = (VariableDef) (elements.get(name));

        if (result == null) {
            IClass[] superinterfaces = getInterfaces();
            for (int i = 0;
                i < superinterfaces.length && result == null;
                i++) {
                result = superinterfaces[i].getVariableDefinition(name);
            }
        }

        if (result == null) {
            if (superclass != null) {
                result = superclass.getVariableDefinition(name);
            }
        }

        if (result == null) {
            if (getParentScope() != null) {
                result = getParentScope().getVariableDefinition(name);
            }
        }

        return result;
    }

    // end definitions interface

    public void addInterface(IClass implemented) {
        interfaces.add(implemented);
    }

    public IClass[] getInterfaces() {
        IClass[] type = new IClass[0];
        return (IClass[]) interfaces.toArray(type);
    }

    public ClassDef getEnclosingClass() {
        return this;
    }

    public void addSubclass(ClassDef subclass) {
        subclasses.add(subclass);
    }

    public List getSubclasses() {
        return subclasses;
    }

    public void addImplementor(ClassDef implementor) {
        implementors.add(implementor);
    }

    public List getImplementors() {
        return implementors;
    }

    public IClass[] getInnerClasses() {
        Iterator it = getClasses();
        List result = new ArrayList();

        while (it.hasNext()) {
            result.add(it.next());
        }

        return (IClass[]) result.toArray(new IClass[0]);
    }

    public boolean isSuperclassOf(IClass possibleChild) {
        // justify my existence
        boolean result = subclasses.contains(possibleChild);

        /*
        Iterator it = subclasses.iterator();
        while (it.hasNext() && !result) {
          IClass child = (IClass)it.next();
          result = child.isSuperclassOf(possibleChild);
        }
        */
        return result;
    }

    public boolean isCompatibleWith(IClass type) {
        boolean result = false;

        // check myself
        if (type.equals(this)) {
            result = true;
        }
        // check my superclass
        else if (superclass != null && superclass.isCompatibleWith(type)) {
            result = true;
        }
        // check my interfaces
        else if (!interfaces.isEmpty()) {
            Iterator it = interfaces.iterator();

            while (it.hasNext() && !result) {
                IClass current = (IClass) it.next();

                if (current.isCompatibleWith(type)) {
                    result = true;
                }
            }
        }

        return result;
    }

    public boolean isPrimitive() {
        return false;
    }
}
