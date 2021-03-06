/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.impl;

import io.proleap.cobol.Cobol85Parser.LiteralContext;
import io.proleap.cobol.asg.metamodel.Literal;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.type.Type;
import io.proleap.cobol.asg.metamodel.valuestmt.LiteralValueStmt;

public class LiteralValueStmtImpl extends ValueStmtImpl implements LiteralValueStmt {

	protected Literal literal;

	public LiteralValueStmtImpl(final ProgramUnit programUnit, final LiteralContext ctx) {
		super(programUnit, ctx);
	}

	@Override
	public Literal getLiteral() {
		return literal;
	}

	@Override
	public Type getType() {
		return literal.getType();
	}

	@Override
	public Object getValue() {
		final Object result;

		if (literal == null) {
			result = null;
		} else {
			result = literal.getValue();
		}

		return result;
	}

	@Override
	public void setLiteral(final Literal literal) {
		this.literal = literal;
	}

	@Override
	public String toString() {
		return literal != null ? literal.toString() : null;
	}
}
