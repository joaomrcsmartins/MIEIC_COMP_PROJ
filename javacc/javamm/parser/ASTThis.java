/* Generated By:JJTree: Do not edit this line. ASTThis.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package javamm.parser;

public
class ASTThis extends SimpleNode {
    public ASTThis(int id) {
        super(id);
    }

    public ASTThis(Javamm p, int id) {
        super(p, id);
    }

    @Override
    public void write(PrintWriter writer) {
        writer.println("aload_0");
    }

}
/* JavaCC - OriginalChecksum=d1b4bf035afae7b16b90bef37d06c462 (do not edit this line) */
