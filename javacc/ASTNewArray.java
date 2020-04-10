import semantics.Symbol;
import semantics.Symbol.Type;

/* Generated By:JJTree: Do not edit this line. ASTNewArray.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTNewArray extends TypeNode {
    public ASTNewArray(int id) {
        super(id);
        type = Type.INT_ARRAY;
    }

    public ASTNewArray(Parser p, int id) {
        super(p, id);
        type = Type.INT_ARRAY;
    }

    @Override
    public void eval() throws SemanticsException {
        if (this.jjtGetNumChildren() != 1) throw new SemanticsException("New array operation is unary");

        SimpleNode child = (SimpleNode) this.jjtGetChild(0);
        this.evaluateChild(child, Type.INT);
    }
}
/* JavaCC - OriginalChecksum=3a631d25cbdc4e13263745a21c4cb259 (do not edit this line) */
