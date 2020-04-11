import semantics.Symbol;
import semantics.Symbol.Type;

import java.util.ArrayList;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. ASTImport.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTImport extends SimpleNode {

  public ASTImport(int id) {
    super(id);
  }

  public ASTImport(Parser p, int id) {
    super(p, id);
  }

  public void eval() throws SemanticsException {

    Type returnValue = null;
    List<String> identifiers = new ArrayList<>();
    List<Type> params = new ArrayList<>();
    for(int i = 0; i < this.jjtGetNumChildren(); i++){
      SimpleNode currNode = (SimpleNode) this.jjtGetChild(i);
      currNode.setTable(table);
      currNode.eval();

      if(currNode.id == ParserTreeConstants.JJTIMPORTPARAMS) {
        params = ((ASTImportParams) currNode).paramTypes;
      }
      else if (currNode.id == ParserTreeConstants.JJTRETURN) {
        returnValue = ((ASTReturn) currNode).returnType;
      }
      else if (currNode.id == ParserTreeConstants.JJTIDENTIFIER) {
        identifiers.add(((ASTIdentifier) currNode).identifierName);
      }
    }

    String fullImportName = String.join(".", identifiers);
    table.putSymbol(fullImportName, new Symbol(returnValue, "", false, params));

  }

}
/* JavaCC - OriginalChecksum=102b8623e78d8554f34992788a15d8e1 (do not edit this line) */
