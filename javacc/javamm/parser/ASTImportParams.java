package javamm.parser;

import javamm.SemanticsException;
import javamm.semantics.ClassSymbol;
import javamm.semantics.Symbol;

import java.util.ArrayList;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. ASTImportParams.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTImportParams extends SimpleNode {
    public List<Symbol> paramTypes = new ArrayList<>(); // collect the Symbol types of each parameter

    public ASTImportParams(int id) {
        super(id);
    }

    public ASTImportParams(Javamm p, int id) {
        super(p, id);
    }

    public void eval(Javamm parser) {

        for (int i = 0; i < this.jjtGetNumChildren(); i++) {
            SimpleNode currNode = (SimpleNode) this.jjtGetChild(i);
            if (currNode.id == JavammTreeConstants.JJTIDENTIFIER) { // parameter type is identifier
                ASTIdentifier identifier = (ASTIdentifier) currNode;
                if (!table.checkSymbol(identifier.identifierName)) { // check if type exists
                    parser.semanticErrors.add(new SemanticsException("Unknown identifier type: " + identifier.identifierName, this));
                }

                ClassSymbol symbol = (ClassSymbol) table.getSymbol(identifier.identifierName);
                paramTypes.add(symbol);
            } else { // parameter type is not identifier
                currNode.setTables(table, methodTable);
                currNode.eval(parser);

                Symbol.Type type = Symbol.getNodeSymbolType(currNode);
                if (type != Symbol.Type.VOID)
                    paramTypes.add(new Symbol(type));
            }
        }
    }


    /**
     * Check if all identifier parameters' types exist
     * @param parser Javamm object for Warnings and errors
     */
    public void evalIdentifiers(Javamm parser) {
        for(int i = 0; i < this.jjtGetNumChildren(); i++){
            SimpleNode currNode = (SimpleNode) this.jjtGetChild(i);
            if (!(currNode instanceof ASTIdentifier)) continue;

            String identifierName = ((ASTIdentifier) currNode).identifierName;
            if(!this.table.checkSymbol(identifierName)) {
                parser.semanticErrors.add(new SemanticsException("Unknown identifier type: " + identifierName, this));
                return;
            }
        }
    }

}
/* JavaCC - OriginalChecksum=d6c0b562b7dca48aa604c88f8adfac00 (do not edit this line) */
