package com.github.mybridge.sql.parser;

import java.util.List;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;

public class IdItemsListVisitor implements ItemsListVisitor {

    private int                           index;
    private long                          idValue;
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(DefaultParser.class);

    public IdItemsListVisitor(int index) {
        this.index = index;
    }

    public long getValue() {
        return this.idValue;
    }

    @Override
    public void visit(SubSelect subSelect) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ExpressionList expressionList) {
        List list = expressionList.getExpressions();
        this.idValue = ((net.sf.jsqlparser.expression.LongValue) list.get(index)).getValue();
    }
}
