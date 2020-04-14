package base;

import base.semantics.MethodSymbol;
import base.semantics.SymbolTable;
import base.semantics.Symbol;

import java.util.ArrayList;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. ASTMethod.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTMethod extends TypeNode {
  public ASTMethod(int id) {
    super(id);
  }

  public ASTMethod(Parser p, int id) {
    super(p, id);
  }

  @Override
  public void eval() throws SemanticsException {
    if (this.jjtGetNumChildren() != 2 && this.jjtGetNumChildren() != 3) throw new SemanticsException("Lacks the number of required children!");
    SimpleNode methodType = (SimpleNode) this.jjtGetChild(0);
    SimpleNode parameters;

    if(this.jjtGetNumChildren() == 2) {
      parameters = null;
    } else {
      parameters = (SimpleNode) this.jjtGetChild(1);
    }
    //TODO: class must set the table of the method instead of using this

    String method_name = null;
    Symbol.Type type = null;
    List<Symbol.Type> parameters_types =  new ArrayList<>();

    if (methodType.id == ParserTreeConstants.JJTMETHODNAME){
      ASTIdentifier namenode = (ASTIdentifier) methodType.jjtGetChild(1);
      SimpleNode typenode = (SimpleNode) methodType.jjtGetChild(0);
      method_name = namenode.identifierName;
      type = VarNode.getType(typenode.id);

      if(parameters != null) {
        parameters.setTable(this.table);
        if(this.jjtGetNumChildren() % 2 != 0) throw new SemanticsException("Invalid set of parameters. Must be in pairs: <TYPE> <NAME>");
        for(int i = 0; i < this.jjtGetNumChildren(); i+=2) {
          VarNode parameter = new VarNode(i,this.jjtGetChild(i),this.jjtGetChild(i+1),table);
          parameter.eval();
          parameters_types.add(parameter.getType(this.jjtGetChild(i).getId()));
        }
      }
      this.table.getParent().putSymbol(method_name,new MethodSymbol(type,parameters_types));
    } else if(methodType.id == ParserTreeConstants.JJTMAIN) {
      if(this.table.getParent().getSymbol("main") != null)
        throw new SemanticsException("Main is already defined in this class!");

      SimpleNode child = (SimpleNode) methodType.jjtGetChild(0);
      if(child.id == ParserTreeConstants.JJTIDENTIFIER)
      {
        ASTIdentifier identifier = (ASTIdentifier) child;
        this.table.putSymbol(identifier.identifierName, new Symbol(Symbol.Type.OBJ));
      }
      else
        throw new SemanticsException("Invalid child to body!");

      parameters_types.add(Symbol.Type.OBJ);
      this.table.getParent().putSymbol("main",new MethodSymbol(Symbol.Type.MAIN,parameters_types));
    } else {
      throw new SemanticsException("Wrong method type was found");
    }


  }

  public void processBody() throws SemanticsException {
    ASTMethodBody methodBody;

    if(this.jjtGetNumChildren() == 2) {
      methodBody = (ASTMethodBody) this.jjtGetChild(1);
    } else {
      methodBody = (ASTMethodBody) this.jjtGetChild(2);
    }

    if(methodBody.id != ParserTreeConstants.JJTMETHODBODY)
    {
      throw new SemanticsException("No method body found!");
    }
    else {
      methodBody.setTable(new SymbolTable());
      methodBody.eval();
    }

  }
}
/* JavaCC - OriginalChecksum=e01bdf01dd9e8aa606ef225a59a26df3 (do not edit this line) */
