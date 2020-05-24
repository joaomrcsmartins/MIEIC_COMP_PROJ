package javamm.parser;

import javamm.semantics.StackUsage;
import javamm.semantics.Symbol.Type;

import java.io.PrintWriter;

/* Generated By:JJTree: Do not edit this line. ASTNumeric.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTNumeric extends TypeNode {
  public int number;
  public ASTNumeric(int id) {
    super(id);
    type = Type.INT;
  }

  public ASTNumeric(Javamm p, int id) {
    super(p, id);
    type = Type.INT;
  }

  public String toString() {
    return JavammTreeConstants.jjtNodeName[id] + "[" + number + "]";
  }

  @Override
  public void write(PrintWriter writer) {
    if(number > 32767) {
      writer.println("  ldc " + number);
    } else if (number > 127) {
      writer.println("  sipush " + number);
    } else {
      writer.println(number > 5 ? "  bipush " + number : "  iconst_" + number);
    }
  }

  @Override
  protected void calculateStackUsage(StackUsage stackUsage) {
    stackUsage.inc(1);
  }

}
/* JavaCC - OriginalChecksum=d1df27becc141526f727d9a5e4174f25 (do not edit this line) */
