/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.impl;

import io.proleap.cobol.Cobol85Parser.BooleanLiteralContext;
import io.proleap.cobol.asg.metamodel.BooleanLiteral;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.type.CobolBaseType;
import io.proleap.cobol.asg.metamodel.type.Type;
import io.proleap.cobol.asg.metamodel.valuestmt.BooleanLiteralValueStmt;

public class BooleanLiteralValueStmtImpl extends ValueStmtImpl implements BooleanLiteralValueStmt {

	protected BooleanLiteral booleanLiteral;

	public BooleanLiteralValueStmtImpl(final ProgramUnit programUnit, final BooleanLiteralContext ctx) {
		super(programUnit, ctx);
	}

	@Override
	public BooleanLiteral getLiteral() {
		return booleanLiteral;
	}

	@Override
	public Type getType() {
		return CobolBaseType.BOOLEAN;
	}

	@Override
	public Boolean getValue() {
		final Boolean result = booleanLiteral != null ? booleanLiteral.getValue() : null;
		return result;
	}

	@Override
	public void setBooleanLiteral(final BooleanLiteral booleanLiteral) {
		this.booleanLiteral = booleanLiteral;
	}

	@Override
	public String toString() {
		return booleanLiteral != null ? booleanLiteral.toString() : null;
	}
}
