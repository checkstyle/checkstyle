
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

import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import com.puppycrawl.tools.checkstyle.api.TokenTypes;




/**
 * this is the class that does the work of the "walking" step --
 * going through the SymTabAST and constructing the definitions and
 * references.  The SymTabAST is constructed in a DOMish fashion, i.e.
 * each node knows about its first child and its next sibling.  The
 * DFS search is carried out by two functions -- walkTree, which
 * deals with a single SymTabAST node, and walkSibilngs, which deals with
 * all the siblings of an SymTabAST node.
 */

public class TableMaker {
  private SymbolTable symbolTable;
  private SymTabAST _tree;
  private File currentFile;
  private Vector imports = new Vector();

  /**
   * Constructor for the TableMaker class
   *
   * @param tree is the<code>SymTabAST</code> from which to
   * build the <code>TableMaker</code>. It is the tree which wil be walked
   * in order to build definitions and references.
   */
  public TableMaker(SymTabAST tree)
  {
    _tree = tree;
  }

  /**
   * returns the <code> SymTabAST </code> which is the
   * SymTabAST generated from the parsed Java Source files.
   *
   * @return <code>SymTabAST</code>
   */
  public SymTabAST getTree() {
    return _tree;
  }

  /**
   * returns the <code>SymbolTable</code> that has been constructed by
   * this <code>TableMaker</code>
   *
   * @return <code>SymbolTable</code>
   * @throws <code>SymbolTableException</code>
   */
  public SymbolTable getTable() throws SymbolTableException {
    if (symbolTable == null) {
      symbolTable = new SymbolTable( _tree );

      createDefinitions();

      resolveReferences();
    }

    return symbolTable;
  }

  /**
   * walks the tree and finishes creating definitions.
   *
   * @return <code>void</code>
   * @throws <code>SymbolTableException</code>
   * @see #walkTree()
   * @see #finishCreatingDefinitions()
   */
  private void createDefinitions() throws SymbolTableException {
    walkTree();
    finishCreatingDefinitions();
  }

  /**
   * finishes up creating definitions process
   * starts at the base of the Table
   *
   * @return <code>void</code>
   * @throws <code>SymbolTableException</code>
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   * @see #finishCreatingDefinition(Definition)
   */
  private void finishCreatingDefinitions() throws SymbolTableException {
    finishCreatingDefinition(symbolTable.getBaseScope());
  }

  /**
   * begins at the base of the Table and starts finishing definition creation.
   *
   * @param def <code>Definition</code> needs to be completed
   * @return <code>void</code>
   * @throws <code>SymbolTableException</code>
   * @see ClassFinisher
   * @see VariableFinisher
   * @see MethodFinisher
   * @see CatchFinisher
   */
  private void finishCreatingDefinition(Definition def) throws SymbolTableException {

    if (def instanceof AnonymousInnerClass) {
      AnonymousInnerClass innerClass = (AnonymousInnerClass)def;
      innerClass.finishMakingDefinition();
    }
    else if (def instanceof ClassDef) {
      new ClassFinisher(def).finish();
    }
    else if ( def instanceof VariableDef ) {
      new VariableFinisher( def ).finish();
    }
    else if (def instanceof DefaultConstructor) {}
    else if ( def instanceof MethodDef ) {
      new MethodFinisher( def ).finish();
    }
    else if (def instanceof BlockDef) {
      SymTabAST firstChild = (SymTabAST)def.getTreeNode().getFirstChild();
      //handle Checkstyle grammar
      if (firstChild.getType() == TokenTypes.LPAREN) {
          firstChild = (SymTabAST) firstChild.getNextSibling();
      }
      if (firstChild.getType() == TokenTypes.PARAMETER_DEF) {
        // this is a catch block
        new CatchFinisher((BlockDef)def).finish();
      }
    }

    if ( def instanceof Scope ) {
      finishCreatingChildren((Scope)def);
    }
  }


  /**
   * iterates through all the definitions in the <code> Scope </code>
   * finishes creating each <code>Scope</code>.
   *
   * @param scope <code> Scope </code> where defininitions will be finished.
   * @return <code>void</code>
   * @throws <code>SymbolTableException</code>
   * @see net.sourceforge.transmogrify.symtab.Scope
   * @see #finishCreatingDefinition(Definition)
   */
  private void finishCreatingChildren( Scope scope ) throws SymbolTableException {
    Enumeration definitions = scope.getDefinitions();
    while ( definitions.hasMoreElements() ) {
      Definition child = (Definition)(definitions.nextElement());
      finishCreatingDefinition(child);
    }
  }

  /**
   * resolves <code>SymbolTable</code> using <code>Resolver</code>
   *
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.Resolver
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   */
  private void resolveReferences() {
    new Resolver( symbolTable ).resolve();
  }

  /**
   * starts walking the <code> SymTabAST </code>
   *
   * @return <code>void</code>
   * @see #walkSiblings(SymTabAST,boolean)
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   */
  public void walkTree() {
    walkSiblings((SymTabAST)_tree.getFirstChild(), false);
  }

  /**
   * processes a single SymTabAST node based on its type and passes of its
   * children for further processing where appropriate.
   *
   * @param tree the <code>SymTabAST</code> node to process
   * @param makeAnonymousScopes sets to <code>false</code> after walking Class,
   *                            Methods and Try otherwise sets to <code>true</code>
   * @return <code>void</code>
   * @see #walkSiblings(SymTabAST, boolean)
   * @see #processAnonymousInnerClass(SymTabAST, SymTabAST)
   * @see #processBlock(SymTabAST, boolean)
   * @see #processClass(SymTabAST)
   * @see #processConstructorDef(SymTabAST)
   * @see #processElse(SymTabAST)
   * @see #processFile(SymTabAST)
   * @see #processFinally(SymTabAST)
   * @see #processFor(SymTabAST)
   * @see #processImplicitPackage(SymTabAST)
   * @see #processImport(SymTabAST)
   * @see #processLabel(SymTabAST)
   * @see #processMethodDef(SymTabAST)
   * @see #processPackage(SymTabAST)
   * @see #processTry(SymTabAST)
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   * @see net.sourceforge.transmogrify.symtab.antlr.JavaTokenTypes
   */
  public void walkTree(SymTabAST tree, boolean makeAnonymousScopes) {

    if (tree != null) {

      tree.setScope( symbolTable.getCurrentScope() );

      switch(tree.getType()) {
      case 0:
        processFile(tree);
        if ( tree.getFirstChild().getType() != TokenTypes.PACKAGE_DEF ) {
          processImplicitPackage( tree.getFile() );
        }

        walkSiblings((SymTabAST)tree.getFirstChild(), false);

        // pop package scope
        symbolTable.popScope();
        clearImports();
        break;

      case TokenTypes.LITERAL_NEW:
        SymTabAST symtabTree = tree;
        //walk parameters, in case of anonymous inner class
        walkTree(symtabTree.findFirstToken(TokenTypes.ELIST),
          makeAnonymousScopes);
        SymTabAST objblock
          = symtabTree.findFirstToken(TokenTypes.OBJBLOCK);
        if (objblock != null) {
          SymTabAST classExtended
            = symtabTree.findFirstToken(TokenTypes.IDENT);

          processAnonymousInnerClass(objblock, classExtended);
        }
        break;

      case TokenTypes.SLIST:
        if (makeAnonymousScopes) {
          processBlock(tree, true);
        }
        else {
          walkSiblings((SymTabAST)tree.getFirstChild(), true);
        }
        break;

      case TokenTypes.CTOR_DEF:
        processConstructorDef(tree);
        break;

      case TokenTypes.METHOD_DEF:
        processMethodDef(tree);
        break;

      case TokenTypes.LITERAL_FINALLY:
        processFinally(tree);
        break;

      case TokenTypes.LITERAL_TRY:
        processTry(tree);
        break;

      case TokenTypes.VARIABLE_DEF:
        processVariableDef(tree);
        break;

      case TokenTypes.PACKAGE_DEF:
        processPackage(tree);
        break;

      case TokenTypes.LABELED_STAT:
        processLabel(tree);
        break;

      case TokenTypes.IMPORT:
        processImport(tree);
        break;

      case TokenTypes.CLASS_DEF:
      case TokenTypes.INTERFACE_DEF:
        processClass(tree);
        break;

      case TokenTypes.LITERAL_FOR:
        processFor(tree);
        break;

      case TokenTypes.LITERAL_IF:
        processIf(tree);
        break;
      
      case TokenTypes.LITERAL_ASSERT:
        processAssert(tree);
        break;

      case TokenTypes.LITERAL_CATCH:
      case TokenTypes.LITERAL_WHILE:
      case TokenTypes.LITERAL_SWITCH:
      case TokenTypes.LITERAL_DO:
      case TokenTypes.LITERAL_SYNCHRONIZED:
      case TokenTypes.STATIC_INIT:
      case TokenTypes.INSTANCE_INIT:
        processBlock(tree, false);
        break;

      default:
        walkSiblings((SymTabAST)tree.getFirstChild(), false);
      }
    }
  }

  /**
 * @param tree
 */
public void processAssert(SymTabAST tree) {
    BlockDef block = makeBlock(tree);

    SymTabAST expr = tree.findFirstToken(TokenTypes.EXPR);
    SymTabAST message = (SymTabAST)expr.getNextSibling();
    while ((message != null) && (message.getType() != TokenTypes.EXPR)) {
        message = (SymTabAST) message.getNextSibling();
    }


    symbolTable.pushScope( block );
    walkTree(expr, false);
    if (message != null) {  
        walkTree(message, false);
    }
    symbolTable.popScope();   
}

/**
   * processes the given <code>SymTabAST</code> and each of its siblings
   *
   * @param tree <code>SymTabAST</code> node to process
   * @param makeAnonymousScopes
   *
   * @return <code>void</code>
   * @see #walkTree(SymTabAST, boolean)
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   */
  public void walkSiblings(SymTabAST tree, boolean makeAnonymousScopes) {
    while(tree != null) {
      walkTree(tree, makeAnonymousScopes);
      tree = (SymTabAST)tree.getNextSibling();
    }
  }

  /**
   * processes the given <code>SymTabAST</code> as a package defintion
   *
   * @param tree the <code>SymTabAST</code> to process
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   * @see net.sourceforge.transmogrify.symtab.PackageDef
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   */
  public void processPackage(SymTabAST tree) {
    SymTabAST firstChild = (SymTabAST)tree.getFirstChild();

    String name = ASTUtil.constructDottedName(firstChild);
    firstChild.ignoreChildren();

    PackageDef pkg = symbolTable.getPackage(name);

    if (pkg == null) {
      pkg = createPackage( (SymTabAST)(tree.getFirstChild()) );
    }

    symbolTable.pushScope(pkg);
  }

  /**
   * processes a java class that use default no package
   *
   * @param file <code>File</code> object of the java class
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.PackageDef
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   */
  public void processImplicitPackage( File file ) {
    String name = file.getParent();
    if (name == null) {
        name = "";
    }
    PackageDef pkg = symbolTable.getPackage( name );

    if ( pkg == null ) {
      pkg = new PackageDef( name, symbolTable.getBaseScope(), null );
      symbolTable.definePackage( pkg, symbolTable.getBaseScope() );
    }

    symbolTable.pushScope( pkg );
  }

  /**
   * gets the package represented by the tree.  The method
   * analyzes the tree, constructs an appropriate package name
   * and fetches it from the internal package list. If the package does not
   * exist it is created.
   *
   * @param tree <code>SymTabAST</code> to consider
   *
   * @return <code>PackageDef</code> the resulting package definition
   * @see #getPackage(Scope, SymTabAST)
   */
  private PackageDef createPackage( SymTabAST tree ) {
    PackageDef result = null;

    if (tree.getType() == TokenTypes.DOT) {
      // find the package result of left child
      SymTabAST leftChild = (SymTabAST)tree.getFirstChild();
      SymTabAST rightChild = (SymTabAST)leftChild.getNextSibling();

      PackageDef context = createPackage(leftChild);
      result = getPackage( context, rightChild );
    }
    else {
      result = getPackage(symbolTable.getBaseScope(), tree);
    }

    return result;
  }

  /**
   * gets the package determined by the tree and parent package def.
   * The method analyzes the tree and parent scope and retrieves the
   * appropriate package definition from the internal package list.
   * If the package does not exist it is created.
   *
   * @param tree <code>SymTabAST</code> to consider
   * @param parent the parent package definition
   *
   * @return PackageDef the resulting package definition
   * @see net.sourceforge.transmogrify.symtab.PackageDef
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   */
  private PackageDef getPackage(Scope parent, SymTabAST tree ) {
    String name = tree.getText();
    PackageDef result = null;
    if (!(parent instanceof BaseScope)) {
      result = symbolTable.getPackage(parent.getQualifiedName() + "." + name);
    }
    else {
      result = symbolTable.getPackage(name);
    }

    if (result == null) {
      result = new PackageDef(tree.getText(), parent, tree);
      symbolTable.definePackage(result, parent);
    }

    return result;
  }

  /**
   * process the given <code>SymTabAST</code> as a file definition
   *
   * @param tree the <code>SymTabAST</code> to process
   * @return <code>void</code>
   * @see #setCurrentFile(String)
   */
  public void processFile(SymTabAST tree) {
    setCurrentFile(tree.getText());
  }

  /**
   * adds the given <code>SymTabAST</code> to <code>imports</code> member
   *
   * @param tree the <code>SymTabAST</code> to process
   * @return <code>void</code>
   */
  public void processImport(SymTabAST tree) {
    imports.add( tree );
  }

  /**
   * clears the <code>imports</code> member
   * @return <code>void</code>
   */
  private void clearImports() {
    imports.clear();
  }

  /**
   * process the given SymTabAST as a label definition
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see com.trwx.symtab.antlr.SymTabAST
   * @see net.sourceforge.transmogrify.symtab.LabelDef
   * @see #walkTree(SymTabAST, boolean)
   */
  public void processLabel(SymTabAST tree) {
    String name = tree.findFirstToken(TokenTypes.IDENT).getText();
    LabelDef label = new LabelDef( name, symbolTable.getCurrentScope(),
           tree );
    symbolTable.defineLabel( label );

    walkTree((SymTabAST)tree.getFirstChild().getNextSibling(), false);
  }

  /**
   * process the given <code>SymTabAST</code> as a class definition
   *
   * @param tree the <code>SymTabAST</code> to process
   * @return <code>void</code>
   * @see #makeClass(String, SymTabAST)
   * @see #walkSiblings(SymTabAST, boolean)
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   */
  public void processClass(SymTabAST tree) {
    String name = tree.findFirstToken(TokenTypes.IDENT).getText();

    makeClass(name, tree);
    final SymTabAST objblock =
        tree.findFirstToken(TokenTypes.OBJBLOCK);
    SymTabAST start = (SymTabAST)objblock.getFirstChild();
    if (start != null) {
        //skip LPAREN
        if (start.getType() == TokenTypes.LPAREN) {
            start = (SymTabAST)start.getNextSibling();
        }
        walkSiblings(start, false);
    }
    
    symbolTable.popScope();
  }

  /**
   * creates <code>ClassDef</code> for the current class node and add it to the
   * symbol table
   * @param name name of the class
   * @param tree current node to be processed
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.ClassDef
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   */
  public void makeClass(String name, SymTabAST tree) {
    ClassDef def = new ClassDef(name, symbolTable.getCurrentScope(), tree);
    def.addUnprocessedImports(imports);
    symbolTable.defineClass(def);
    symbolTable.pushScope(def);
  }

  /**
   * processes anonymous inner class encountered in the tree
   * @param objblock the anonymous block
   * @param classExtended
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.AnonymousInnerClass
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   */
  public void processAnonymousInnerClass(SymTabAST objblock,
                                         SymTabAST classExtended) {
    ClassDef def = new AnonymousInnerClass(objblock,
                                           classExtended,
                                           symbolTable.getCurrentScope());
    symbolTable.defineClass(def);
    symbolTable.pushScope(def);
    walkSiblings((SymTabAST)objblock.getFirstChild(), false);
    symbolTable.popScope();
  }

  /**
   * process the given SymTabAST as a variable definition
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.VariableDef
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   * @see #makeVariableDef(SymTabAST, Scope)
   * @see #walkTree(SymTabAST, boolean)
   */
  private void processVariableDef(SymTabAST tree) {
    VariableDef def = makeVariableDef( tree, symbolTable.getCurrentScope() );
    symbolTable.defineVariable(def);
    SymTabAST assignmentNode
      = tree.findFirstToken(TokenTypes.ASSIGN);
    if (assignmentNode != null) {
      walkTree((SymTabAST)assignmentNode.getFirstChild(), false);
    }
  }

  /**
   * creates <code>VariableDef</code> based on the current tree node and scope
   * @param tree the current tree node
   * @param scope the current scope
   * @return <code>VariableDef</code>
   * @see net.sourceforge.transmogrify.symtab.VariableDef
   */
  public VariableDef makeVariableDef(SymTabAST tree, Scope scope) {
    String name = tree.findFirstToken(TokenTypes.IDENT).getText();
    VariableDef result = new VariableDef(name, scope, tree);

    return result;
  }

  /**
   * process the given SymTabAST as a try block
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see #makeBlock(SymTabAST)
   * @see net.sourceforge.transmogrify.symtab.antlr.SymTabAST
   * @see #walkTree(SymTabAST, boolean)
   * @see #walkSiblings(SymTabAST, boolean)
   */
  public void processTry(SymTabAST tree){
    BlockDef block = makeBlock(tree);

    SymTabAST slist = tree.findFirstToken(TokenTypes.SLIST);
    SymTabAST everythingElse = (SymTabAST)slist.getNextSibling();

    symbolTable.pushScope( block );
    walkTree( slist, false );
    symbolTable.popScope();

    walkSiblings( everythingElse, false );
  }

  /**
   * process the given SymTabAST as a finally block
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see #makeBlock(SymTabAST)
   * @see #walkTree(SymTabAST, boolean)
   */
  public void processFinally(SymTabAST tree){
    BlockDef block = makeBlock(tree);

    SymTabAST slist = tree.findFirstToken(TokenTypes.SLIST);
    SymTabAST tryBlock = tree.findFirstToken(TokenTypes.LITERAL_TRY);

    symbolTable.pushScope( block );
    walkTree( slist, false );
    symbolTable.popScope();

    walkTree( tryBlock, false );
  }


  /**
   * process the given SymTabAST as a method definition
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.MethodDef
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   * @see #walkTree(SymTabAST, boolean)
   */
  public void processMethodDef(SymTabAST tree) {
    String name = tree.findFirstToken(TokenTypes.IDENT).getText();
    MethodDef method = new MethodDef(name, symbolTable.getCurrentScope(),
                                     tree);
    symbolTable.defineMethod( method );

    symbolTable.pushScope( method );
    walkTree(tree.findFirstToken(TokenTypes.SLIST), false);
    symbolTable.popScope();
  }

  /**
   * process the given SymTabAST as a constructor definition
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see net.sourceforge.transmogrify.symtab.MethodDef
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   * @see #walkTree(SymTabAST, boolean)
   */
  public void processConstructorDef(SymTabAST tree) {
    processMethodDef(tree);
  }

  /**
   * process the given SymTabAST as a for block
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see #makeBlock(SymTabAST)
   * @see #walkTree(SymTabAST, boolean)
   */
  public void processFor(SymTabAST tree) {
    BlockDef block = makeBlock(tree);

    symbolTable.pushScope( block );
    SymTabAST body;
    SymTabAST forEach = tree.findFirstToken(TokenTypes.FOR_EACH_CLAUSE);
    if (forEach != null) {
        walkTree(forEach, false);
        body = (SymTabAST)forEach.getNextSibling();
    }
    else {
        walkTree(tree.findFirstToken(TokenTypes.FOR_INIT), false);
        walkTree(tree.findFirstToken(TokenTypes.FOR_CONDITION), false);

        SymTabAST forIter = tree.findFirstToken(TokenTypes.FOR_ITERATOR);
        walkTree(forIter, false);
        body = (SymTabAST)forIter.getNextSibling();
    }

    //handle Checkstyle grammar
    if (body.getType() == TokenTypes.RPAREN) {
        body = (SymTabAST) body.getNextSibling();
    }
    walkTree(body, false);
    symbolTable.popScope();
  }

  /**
   * process the given SymTabAST as an if block
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see #makeBlock(SymTabAST)
   * @see #walkTree(SymTabAST, boolean)
   * @see #processElse(SymTabAST)
   */
  public void processIf(SymTabAST tree) {
    BlockDef block = makeBlock(tree);

    SymTabAST expr = tree.findFirstToken(TokenTypes.EXPR);
    SymTabAST ifBranch = (SymTabAST)expr.getNextSibling();
    // handle Checkstyle grammar
    if (ifBranch.getType() == TokenTypes.RPAREN) {
        ifBranch = (SymTabAST) ifBranch.getNextSibling();
    }
    SymTabAST elseBranch = (SymTabAST)ifBranch.getNextSibling();
    // handle Checkstyle grammar
    if ((elseBranch != null) && (elseBranch.getType() == TokenTypes.SEMI)) {
            elseBranch = (SymTabAST) elseBranch.getNextSibling();
    }
    if ((elseBranch != null) && (elseBranch.getType() == TokenTypes.LITERAL_ELSE)) {
        elseBranch = (SymTabAST) elseBranch.getFirstChild();
    }

    symbolTable.pushScope( block );
    walkTree(expr, false);
    walkTree(ifBranch, false);
    symbolTable.popScope();

    processElse(elseBranch);
  }

  /**
   * process the given SymTabAST as an else block
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see #processIf(SymTabAST)
   * @see #makeElseBlock(SymTabAST)
   */
  public void processElse(SymTabAST tree) {
    if (tree != null) {
      if (tree.getType() == TokenTypes.LITERAL_IF) {
        processIf(tree);
      }
      else {
        makeElseBlock(tree);
      }
    }
  }

  /**
   * defines an anonymous block to enclose the scope of an else block
   *
   * @param tree the SymTabAST to process
   * @return <code>void</code>
   * @see #makeBlock(SymTabAST)
   * @see #walkTree(SymTabAST, boolean)
   */
  public void makeElseBlock(SymTabAST tree) {
    if (tree.getType() == TokenTypes.SLIST) {
      BlockDef block = makeBlock(tree);
      symbolTable.pushScope( block );
      walkTree(tree, false);
      symbolTable.popScope();
    }
    else {
      walkTree(tree, false);
    }
  }

  /**
   * processes the current tree node as BlockDef
   * @param tree current tree node
   * @param makeAnonymousScopes
   * @return <code>void</code>
   */
  public void processBlock(SymTabAST tree, boolean makeAnonymousScopes) {
    BlockDef block = makeBlock(tree);
    symbolTable.pushScope(block);
    //handle Checkstyle grammar
    SymTabAST child = (SymTabAST)tree.getFirstChild();
    if ((child != null) && (child.getType() == TokenTypes.LPAREN)) {
        child = (SymTabAST) child.getNextSibling();
    }
    walkSiblings(child, makeAnonymousScopes);
    symbolTable.popScope();
  }

  /**
   * set the current file to the named file
   *
   * @param fileName the name of the file
   * @return <code>void</code>
   */
  public void setCurrentFile(String fileName) {
    currentFile = new File(fileName);
    symbolTable.setCurrentFile(currentFile);
  }


  /**
   * creates a new <code> BlockDef </code> in the SymbolTable
   *
   * @param tree is a <code> SymTabAST </code> whose root begins the new block
   * @return <code> BlockDef </code>
   * @see net.sourceforge.transmogrify.symtab.BlockDef
   * @see net.sourceforge.transmogrify.symtab.SymbolTable
   */
  private BlockDef makeBlock( SymTabAST tree ) {
    BlockDef block = new BlockDef( symbolTable.getCurrentScope(), tree );
    symbolTable.defineBlock( block );
    return block;
  }

  /**
   * returns the <code>SymTabAST</code> that contains the parameter classDef's
   * extends nodes
   *
   * @param classDef is a <code> ClassDef </code> whose extends nodes we want
   * @return <code> SymTabAST </code>
   */
  public static SymTabAST getExtendsNode(ClassDef classDef) {
    SymTabAST result = null;
    SymTabAST extendsClause = null;

    SymTabAST classDefTreeNode = classDef.getTreeNode();
    extendsClause =
      classDefTreeNode.findFirstToken(TokenTypes.EXTENDS_CLAUSE);

    if (extendsClause != null) {
      result = (SymTabAST)(extendsClause.getFirstChild());
    }

    return result;
  }

  /**
   * Superclass for different kind of XXXFinisher subclass
   * @see CatchFinisher
   * @see ClassFinisher
   * @see DefinitionFinisher
   * @see MethodFinisher
   * @see VariableFinisher
   */
  class DefinitionFinisher {

    protected SymTabAST _node = null;

    /**
    * Constructor. It finishes the definition passed to it
    *
    */
    public DefinitionFinisher( Definition def ) {
      _node = def.getTreeNode();
    }

    public void finish() throws SymbolTableException {}

    /**
     * gets the classDef that represents the type of the given definition
     *
     *
     * @param def the definition whose type to find
     * @param typeNode the TokenTypes.TYPE node associated with the def
     *
     * @return the resulting class definition
     */
    protected IClass getType( Definition def, SymTabAST typeNode ) {
      IClass result = null;

      SymTabAST typeClassNode = null;
      boolean isArray = false;

      if ( typeNode.getFirstChild().getType()
           == TokenTypes.ARRAY_DECLARATOR ) {
        isArray = true;
        typeClassNode = (SymTabAST)typeNode.getFirstChild().getFirstChild();
      }
      else {
        typeClassNode = (SymTabAST)typeNode.getFirstChild();
      }

      Scope lookupScope = null;

      if (def instanceof Scope) {
         lookupScope = (Scope)def;
      }
      else {
         lookupScope = def.getParentScope();
      }

      Resolver resolver = new Resolver(symbolTable);
      IClass typeClass = resolver.resolveClass(typeClassNode, lookupScope, null, false);

      if ( isArray ) {
        result = new ArrayDef( typeClass );
      }
      else {
        result = typeClass;
      }

      return result;
    }

  }

  class ClassFinisher extends DefinitionFinisher {

    private ClassDef _def = null;

    /**
    * Constructor. Creates a ClassFinisher from a <code> Definition </code>
    *
    * @param def is a <code> Definition </code>
    */
    public ClassFinisher( Definition def ) {
      super( def );
      _def = (ClassDef)def;
    }

    /**
    * Completes all tasks required for finishing a ClassDef
    * Including adding imports, setting super classes and adding
    * interfaces.
    * @return <code>void</code>
    * @throws <code>SymbolTableException</code>
    * @see #addImports()
    * @see #setSuperclass()
    * @see #addInterfaces()
    */
    public void finish() throws SymbolTableException {
      if ( _node != null ) {
        addImports();
        setSuperclass();
        addInterfaces();
      }
    }

    /**
    * Adds all packages and classes that are imported by this class
    * to the class for later reference
    */
    private void addImports() throws ClassImportException {
      IPackage java = new ExternalPackage("java", null);
      IPackage lang = new ExternalPackage("lang", java);
      java.addDefinition(lang);
      _def.importPackage(lang);

      Vector unprocessedImports = _def.getUnprocessedImports();
      for ( int i = 0; i < unprocessedImports.size(); i++ ) {
        SymTabAST importNode = (SymTabAST)unprocessedImports.get(i);
        SymTabAST imported = (SymTabAST)importNode.getFirstChild();
        SymTabAST lastPart = (SymTabAST)imported.getFirstChild().getNextSibling();

        DotIterator it = new DotIterator(imported);
        SymTabAST current = null;
        String className = null;
        IClass importedClass = null;

        // attempt at each token to find the class
        //   first in source
        //   then on classpath
        //
        // if there are more tokens left
        //   continue until you hit the last token
        //   if it's a star
        //     import all inner classes
        //   else
        //     import the explicitly named inner class
        // else import the class
        //
        // if no classes were found, import the package

        while(it.hasNext()) {
          current = it.nextNode();
          if (className == null) {
            className = current.getText();
          }
          else {
            if (!current.getText().equals("*")) {
              className += "." + current.getText();
            }
            else {
              break;
            }
          }
          importedClass = findOrLoadClass(className, importedClass);

          if (importedClass != null) {
            break;
          }
        }

        if (it.hasNext()) {
          boolean isImported = false;
          while(it.hasNext()) {
            current = it.nextNode();
            if (current.getText().equals("*")) {
              importInnerClasses(importedClass);
              isImported = true;
            }
            else {
              className += "$" + current.getText();
              importedClass = findOrLoadClass(className, importedClass);
            }
          }
          if (!isImported) {
            _def.importClass(importedClass);
          }
        }
        else {
          if (importedClass != null) {
            _def.importClass(importedClass);
          }
          else {
            if (current != null && current.getText().equals("*")) {
              IPackage pkg = symbolTable.getPackage(className);
              if (pkg == null) {
                pkg = getPackage(className);
              }
              _def.importPackage(pkg);
            }
            else {
              //TODO: can we safely ignore this?
              //throw new ClassImportException(className);
              ;
            }
          }
        }

        // now set definitions where appropriate
        imported.ignoreChildren();
        if ((lastPart.getType() == TokenTypes.IDENT)
            //TODO: guard added for class not loaded
            //This is OK for single file processing, but not
            //multiple files.
            && (importedClass != null)
            )
        {
          lastPart.setDefinition(importedClass, null, true);
          lastPart.setMeaningfulness(true);
        }
      }
    }

    /**
     * creates <code>ExternalPackage</code>
     * @param packageName name of the package
     * @return <code>ExternalPackage</code>
     * @see net.sourceforge.transmogrify.symtab.ExternalPackage
     */
    private ExternalPackage getPackage(String packageName) {
      return new ExternalPackage(packageName, null);
    }

    /**
     * stores the inner classes in the approriate ClassDef
     * @param outerClass
     * @return <code>void</code>
     */
    private void importInnerClasses(IClass outerClass) {
      IClass[] innerClasses = outerClass.getInnerClasses();

      for (int i = 0; i < innerClasses.length; i++) {
        _def.importClass(innerClasses[i]);
      }
    }

    /**
     * creates <code>ExternalClass</code> based on a java class
     * @param className class to be loaded
     * @return <code>IClass</code>
     * @see net.sourceforge.transmogrify.symtab.ExternalClass
     */
    private IClass loadClass(String className) {
      IClass result = null;

      try {
        Class javaClass
          = ClassManager.getClassLoader().loadClass(className);

        result = new ExternalClass(javaClass);
      }
      catch (ClassNotFoundException ignoreMe) {}

      return result;
    }

    /**
     * find a class from <code>BaseCode</code> or its parent
     * @param className name of the class to be load or found
     * @param parentClass its parent class
     * @return <code>IClass</code>
     * @see net.sourceforge.transmogrify.symtab.SymbolTable
     * @see net.sourceforge.transmogrify.symtab.IClass
     * @see #loadClass(String)
     */
    private IClass findOrLoadClass(String className, IClass parentClass) {
      IClass result = null;

      if (parentClass == null) {
        result = symbolTable.getBaseScope().getClassDefinition(className);
      }
      else {
        int index = className.lastIndexOf("$");
        if (index < 0) {
          index = className.lastIndexOf(".");
        }

        result = parentClass.getClassDefinition(className.substring(index + 1));
      }

      if (result == null) {
        result = loadClass(className);
      }

      return result;
    }

    /**
    *
    * If the class has a super class a reference to this super class
    * is added to this class.
    * @return <code>void</code>
    * @see net.sourceforge.transmogrify.symtab.ClassDef
    */
    private void setSuperclass() {
      if (_def.getTreeNode().getType() == TokenTypes.CLASS_DEF) {
        SymTabAST extendsNode = getExtendsNode(_def);
        if ( extendsNode != null ) {
          String superclassName = ASTUtil.constructDottedName(extendsNode);
          IClass superclass = _def.getClassDefinition(superclassName);
          if ( superclass != null ) {
            _def.setSuperclass( superclass );
            superclass.addSubclass( _def );
          }
        }
        else {
          _def.setSuperclass(new ExternalClass(Object.class));
        }
      }
      else {
        _def.setSuperclass(new ExternalClass(Object.class));
      }
    }

    /**
    *
    * If the class implements an interface a reference to this interface
    * is added to this class.
    * @return <code>void</code>
    * @see net.sourceforge.transmogrify.symtab.ClassDef
    */
    private void addInterfaces() {
      SymTabAST implementsClause = null;

      if (_def.getTreeNode().getType() == TokenTypes.CLASS_DEF) {
        implementsClause
          = _node.findFirstToken(TokenTypes.IMPLEMENTS_CLAUSE);
      }
      else {
        implementsClause
          = _node.findFirstToken(TokenTypes.EXTENDS_CLAUSE);
      }

      if ( implementsClause != null ) {
        SymTabAST interfaceNode = (SymTabAST)implementsClause.getFirstChild();
        while ( interfaceNode != null ) {
          IClass implemented =
            _def.getClassDefinition(interfaceNode.getText());
          if ( implemented != null ) {
            _def.addInterface( implemented );
            implemented.addImplementor( _def );
          }
        interfaceNode = (SymTabAST)(interfaceNode.getNextSibling());
        }
      }
    }
  }

  /**
   *
   * Completes a Variable by setting all required references
   *
   */
  class VariableFinisher extends DefinitionFinisher {
    VariableDef _def = null;


    /**
    *
    * Constructor. Creates a VariableFinishes from the <code>Definition</code>
    * @param def VariableDef to be finished
    * @see net.sourceforge.transmogrify.symtab.VariableDef
    */
    public VariableFinisher( Definition def ) {
      super( def );
      _def = (VariableDef)def;
    }


    /**
    *
    * Finishes the variable by setting the Type
    * @return <code>void</code>
    * @see #getType(Definition, SymTabAST)
    * @see net.sourceforge.transmogrify.symtab.VariableDef
    */
    public void finish() {

      SymTabAST typeNode = _node.findFirstToken(TokenTypes.TYPE);

      SymTabAST typeTextNode = (SymTabAST)(typeNode.getFirstChild());
      if ( typeTextNode.getType() == TokenTypes.ARRAY_DECLARATOR ) {
        typeTextNode = (SymTabAST)(typeTextNode.getFirstChild());
      }
      typeTextNode.setLine(ASTUtil.getLine( _def.getTreeNode() ));

      IClass varType = getType(_def, typeNode);
      _def.setType( varType );

    }
  }

  /**
   *
   * Completes a Variable by setting all required references
   *
   */
  class MethodFinisher extends DefinitionFinisher {
    MethodDef _def = null;


    /**
    *
    * Creates a MethodFinisher from a <code> Definition <code>
    * @param def MethodDef to be finished
    * @see net.sourceforge.transmogrify.symtab.MethodDef
    */
    public MethodFinisher( Definition def ) {
      super( def );
      _def = (MethodDef)def;
    }

    /**
    *
    * Completes a method by setting all references to return types,
    * signatures and exceptions.
    * @return <code>void</code>
    * @see #setReturnType()
    * @see #setSignature()
    * @see #setExceptionsThrown()
    */
    public void finish() {
      setReturnType();
      setSignature();
      setExceptionsThrown();
    }

    /**
    *
    * setReturnType adds a reference to the methods return type
    * to the method definition
    * @return <code>void</code>
    * @see net.sourceforge.transmogrify.symtab.MethodDef
    * @see #getType(Definition, SymTabAST)
    * @see #getTypeNode()
    */
    private void setReturnType() {
      IClass type = null;

      if ( isConstructor() ) {
        type = _def.getEnclosingClass();
      }
      else {
        type = getType(_def, getTypeNode());
      }

      _def.setType(type);
    }

    /**
    *
    * setSignature adds a reference to the methods paramaters
    * to the method definition
    * @return <code>void</code>
    * @see #makeVariableDef(SymTabAST, Definition)
    * @see VariableFinisher
    * @see net.sourceforge.transmogrify.symtab.MethodDef
    */
    private void setSignature() {
      SymTabAST parametersNode
        = _node.findFirstToken(TokenTypes.PARAMETERS);

      SymTabAST parameterNode = (SymTabAST)(parametersNode.getFirstChild());
      while ( parameterNode != null ) {
        if (parameterNode.getType() == TokenTypes.PARAMETER_DEF) {
            VariableDef parameter = makeVariableDef( parameterNode, _def );
            new VariableFinisher( parameter ).finish();
            _def.addParameter( parameter );
        }
        parameterNode = (SymTabAST)(parameterNode.getNextSibling());
      }

    }

    /**
    *
    * setExceptionsThrown adds a reference to the methods Exceptions
    * to the method definition
    * @return <code>void</code>
    * @see net.sourceforge.transmogrify.symtab.SymbolTable
    * @see net.sourceforge.transmogrify.symtab.PackageDef
    * @see net.sourceforge.transmogrify.symtab.MethodDef
    */
    private void setExceptionsThrown() {
      IClass exception = null;
      SymTabAST throwsNode
        = _node.findFirstToken(TokenTypes.LITERAL_THROWS);

      if ( throwsNode != null ) {
        SymTabAST exceptionNode = (SymTabAST)(throwsNode.getFirstChild());
        while (exceptionNode != null ) {
          if (exceptionNode.getType() == TokenTypes.DOT) {
            PackageDef pkg = symbolTable.getPackage(ASTUtil.constructPackage(exceptionNode));
            if ( pkg != null ) {
              exception = pkg.getClassDefinition(ASTUtil.constructClass(exceptionNode));
            }
          }
          else {
            exception = _def.getClassDefinition(exceptionNode.getText());
          }
          _def.addException(exception);
          exceptionNode = (SymTabAST)(exceptionNode.getNextSibling());
        }
      }

    }

    /**
    *
    * isConstructor sets the flag in the method definition to indicate
    * whether it is a constructor or not
    * @return <code>boolean</code> <code>true</code> if a node has no return type
    *                              <code>false</code> if a node has a return type
    * @see #getTypeNode()
    */
    private boolean isConstructor() {
      boolean result = false;

      if ( getTypeNode() == null ) {
        result = true;
      }

      return result;
    }

    /**
    *
    * getTypeNode returnss the <code> SymTabAST</code> node that is the
    * return type of this method
    *
    * @return <code>SymTabAST</code>
    */
    private SymTabAST getTypeNode() {
      return _node.findFirstToken(TokenTypes.TYPE);
    }
  }

  class CatchFinisher extends DefinitionFinisher {
    BlockDef _def = null;

    /**
     * constructor that takes a <code>BlockDef</code>
     * @param def <code>BlockDef</code> for the catch block
     */
    public CatchFinisher(BlockDef def) {
      super(def);
      _def = def;
    }

    /**
     * finishes definition creation
     * @return <code>void</code>
     * @see #createExceptionVariable()
     */
    public void finish() {
      createExceptionVariable();
    }

    /**
     * creates <code>VariableDef<code> and finishes creation definition for
     * arguments in the catch clause
     * @return <code>void</code>
     * @see net.sourceforge.transmogrify.symtab.VariableDef
     * @see #makeVariableDef(SymTabAST, Definition)
     * @see VariableFinisher
     */
    private void createExceptionVariable() {
      SymTabAST exceptionNode
        = _def.getTreeNode().findFirstToken(TokenTypes.PARAMETER_DEF);

      VariableDef exception = makeVariableDef(exceptionNode, _def);
      new VariableFinisher(exception).finish();
      _def.addDefinition(exception);
    }
  }

}


