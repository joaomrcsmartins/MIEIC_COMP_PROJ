package base;

import base.semantics.Symbol;

import java.util.ArrayList;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. ASTImportParams.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTImportParams extends SimpleNode {
    public List<Symbol.Type> paramTypes = new ArrayList<>();

    public ASTImportParams(int id) {
        super(id);
    }

    public ASTImportParams(Parser p, int id) {
        super(p, id);
    }

    public void eval() throws SemanticsException {

        for(int i = 0; i < this.jjtGetNumChildren(); i++){
            SimpleNode currNode = (SimpleNode) this.jjtGetChild(i);
            currNode.setTable(table);
            currNode.eval();
            paramTypes.add(Symbol.getNodeSymbolType(currNode));
        }

    }

}
/* JavaCC - OriginalChecksum=d6c0b562b7dca48aa604c88f8adfac00 (do not edit this line) */