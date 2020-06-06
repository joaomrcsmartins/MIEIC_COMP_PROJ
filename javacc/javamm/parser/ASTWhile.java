package javamm.parser;

import javamm.SemanticsException;
import javamm.cfg.CFGNode;
import javamm.semantics.StackUsage;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

/* Generated By:JJTree: Do not edit this line. ASTWhile.java Version 6.1 */
/* JavaCCOptions:MULTI=true,NODE_USES_Javamm=false,VISITOR=false,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTWhile extends ConditionalNode {

    public static int labelCounter = 0;
    private HashMap<Integer, Integer> requiredPops = new HashMap<>();

    public ASTWhile(int id) {
        super(id);
    }

    public ASTWhile(Javamm p, int id) {
        super(p, id);
    }

    @Override
    public void eval(Javamm parser) {
        super.eval(parser);
        for (int i = 1; i < this.jjtGetNumChildren(); i++) {
            SimpleNode child = (SimpleNode) this.jjtGetChild(i);

            if (!child.validStatement) {
                parser.semanticErrors.add(new SemanticsException("Not a statement: " + child.toString(), child));
                return;
            }

            child.setTables(table, methodTable);
            child.eval(parser);
        }
    }

    @Override
    public void write(PrintWriter writer) {

        BooleanBinaryOperatorNode b = null;
        int localCounter = labelCounter;

        if (!ASTProgram.optimize)
            writer.println("while_" + localCounter + ":");
        SimpleNode expression = (SimpleNode) this.jjtGetChild(0);
        switch (expression.id) {
            case JavammTreeConstants.JJTAND:
            case JavammTreeConstants.JJTLESSTHAN:
                BooleanBinaryOperatorNode node = (BooleanBinaryOperatorNode) expression;
                if (ASTProgram.optimize) {
                    node.write(writer, "endwhile_" + localCounter);
                    writer.println("while_" + localCounter + ":");
                    b = node;
                } else {
                    node.write(writer, "endwhile_" + localCounter);
                }
                break;
            case JavammTreeConstants.JJTBOOLEANVALUE:
            case JavammTreeConstants.JJTNEGATION:
            case JavammTreeConstants.JJTIDENTIFIER:
            case JavammTreeConstants.JJTMETHODCALL:
                expression.write(writer);
                if (ASTProgram.optimize) {
                    writer.println("  ifeq endwhile_" + localCounter);
                    writer.println("while_" + localCounter + ":");
                } else
                    writer.println("  ifeq endwhile_" + localCounter);
                break;
            default:
                return;
        }

        for (int i = 1; i < this.jjtGetNumChildren(); i++) {
            SimpleNode exp = (SimpleNode) this.jjtGetChild(i);
            if (exp.id == JavammTreeConstants.JJTWHILE)
                labelCounter++;
            exp.write(writer);
            StackUsage.popStack(writer, this.requiredPops.get(i));
        }


        if (!ASTProgram.optimize)
            writer.println("  goto while_" + localCounter);
        else if (b != null)
            b.writeConditionOpt(writer, "while_" + localCounter);
        else {
            expression.write(writer);
            writer.println("  ifne while_" + localCounter);
        }


        writer.println("endwhile_" + localCounter + ":");
        labelCounter++;
    }

    public void calculateStackCondition(StackUsage stackUsage, SimpleNode expression) {
        switch (expression.id) {
            case JavammTreeConstants.JJTIDENTIFIER:
            case JavammTreeConstants.JJTBOOLEANVALUE:
            case JavammTreeConstants.JJTNEGATION:
            case JavammTreeConstants.JJTMETHODCALL:
                expression.calculateStackUsage(stackUsage);
                stackUsage.dec(1); // ifeq
                break;
            case JavammTreeConstants.JJTAND:
            case JavammTreeConstants.JJTLESSTHAN:
                BooleanBinaryOperatorNode node = (BooleanBinaryOperatorNode) expression;
                node.calculateParamsStackUsage(stackUsage);
                break;
            default:
                return;
        }
    }

    @Override
    protected void calculateStackUsage(StackUsage stackUsage) {
        SimpleNode expression = (SimpleNode) this.jjtGetChild(0);
        calculateStackCondition(stackUsage, expression);

        int stackUsageBefore = stackUsage.getStackUsage();
        for (int i = 1; i < this.jjtGetNumChildren(); i++) {
            SimpleNode exp = (SimpleNode) this.jjtGetChild(i);
            exp.calculateStackUsage(stackUsage);
            this.requiredPops.put(i, stackUsage.getStackUsage() - stackUsageBefore);
            stackUsage.set(stackUsageBefore);
        }

        if (ASTProgram.optimize)
            calculateStackCondition(stackUsage, expression);
    }

    @Override
    public List<CFGNode> getNodes() {
        CFGNode conditionNode = new CFGNode(((SimpleNode) this.jjtGetChild(0)).getSymbols());
        CFGNode endNode = new CFGNode(new ArrayList<>());

        conditionNode.addEdge(endNode); // connect condition to end

        List<CFGNode> whileNodes = new ArrayList<>(); // all nodes inside while after condition
        for (int i = 1; i < this.jjtGetNumChildren(); i++) {
            List<CFGNode> nodes = ((SimpleNode) this.jjtGetChild(i)).getNodes();

            if (whileNodes.size() != 0)
                whileNodes.get(whileNodes.size() - 1).addEdge(nodes.get(0));

            whileNodes.addAll(nodes);
        }

        if (whileNodes.size() != 0) {
            conditionNode.addEdge(whileNodes.get(0)); // connect condition to while's inner nodes
            for(CFGNode node: whileNodes){
                node.addEdge(conditionNode);
            }
        }

        List<CFGNode> nodes = new ArrayList<>();
        nodes.add(conditionNode);
        nodes.addAll(whileNodes);
        nodes.add(endNode);

        return nodes;
    }
}
/* JavaCC - OriginalChecksum=5f4455af0142b2fe9a424f1a313045fd (do not edit this line) */
