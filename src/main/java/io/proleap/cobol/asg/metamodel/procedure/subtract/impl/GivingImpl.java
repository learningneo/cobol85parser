/*
 * Copyright (C) 2016, Ulrich Wolffgang <u.wol@wwu.de>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the BSD 3-clause license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.subtract.impl;

import io.proleap.cobol.Cobol85Parser.SubtractGivingContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.subtract.Giving;

public class GivingImpl extends CobolDivisionElementImpl implements Giving {

	protected final SubtractGivingContext ctx;

	protected Call givingCall;

	protected boolean rounded;

	public GivingImpl(final ProgramUnit programUnit, final SubtractGivingContext ctx) {
		super(programUnit, ctx);

		this.ctx = ctx;
	}

	@Override
	public Call getGivingCall() {
		return givingCall;
	}

	@Override
	public boolean isRounded() {
		return rounded;
	}

	@Override
	public void setGivingCall(final Call givingCall) {
		this.givingCall = givingCall;
	}

	@Override
	public void setRounded(final boolean rounded) {
		this.rounded = rounded;
	}

}