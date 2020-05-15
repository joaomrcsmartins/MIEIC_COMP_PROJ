/* Generated By:JJTree: Do not edit this line. ASTParameters.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.Symbol;

public
class ASTParameters extends SimpleNode {
    public int nParams = 0;

    public ASTParameters(int id) {
        super(id);
    }

    public ASTParameters(Javamm p, int id) {
        super(p, id);
    }

    @Override
    public void eval(Javamm parser) {
        this.nParams = this.jjtGetNumChildren()/2;
        for (int i = 0; i < this.jjtGetNumChildren(); i+=2) {
            SimpleNode paramType = (SimpleNode) this.jjtGetChild(i);
            if(!(paramType instanceof ASTIdentifier)) continue;

            ASTIdentifier paramIdentifier = (ASTIdentifier) paramType;
            Symbol paramSymbol = this.table.getSymbol(paramIdentifier.identifierName);
            if(paramSymbol == null || paramSymbol.getType() != Symbol.Type.CLASS) {
                parser.semanticErrors.add(new SemanticsException("Unknown parameter type was found", paramIdentifier));
                return;
            }
        }
    }

}
/* JavaCC - OriginalChecksum=60e53f5703d6eb46cf89fbdef85aea83 (do not edit this line) */
