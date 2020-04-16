/* Generated By:JJTree: Do not edit this line. ASTMethodReturn.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package base;

import base.semantics.Symbol;

public
class ASTMethodReturn extends SimpleNode {
  Symbol.Type type = null;
  public ASTMethodReturn(int id) {
    super(id);
  }

  public ASTMethodReturn(Parser p, int id) {
    super(p, id);
  }

  @Override
  public void eval() throws SemanticsException {
    if(!(this.jjtGetChild(0) instanceof  TypeNode))
      throw new SemanticsException("Unknown return type!");

    final TypeNode node = (TypeNode) this.jjtGetChild(0);
    node.setTable(table);
    node.eval();
    type = node.type;
  }
}
/* JavaCC - OriginalChecksum=f35d6e18a29162cb6c0e550634450721 (do not edit this line) */
