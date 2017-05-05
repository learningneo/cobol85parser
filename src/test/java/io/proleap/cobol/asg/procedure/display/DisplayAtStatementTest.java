package io.proleap.cobol.asg.procedure.display;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import io.proleap.cobol.CobolTestSupport;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.procedure.ProcedureDivision;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.display.DisplayStatement;
import io.proleap.cobol.asg.metamodel.procedure.display.Operand;
import io.proleap.cobol.asg.metamodel.valuestmt.CallValueStmt;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;
import io.proleap.cobol.asg.runner.impl.CobolParserRunnerImpl;
import io.proleap.cobol.preprocessor.CobolPreprocessor.CobolSourceFormatEnum;

public class DisplayAtStatementTest extends CobolTestSupport {

	@Test
	public void test() throws Exception {
		final File inputFile = new File(
				"src/test/resources/io/proleap/cobol/asg/procedure/display/DisplayAtStatement.cbl");
		final Program program = new CobolParserRunnerImpl().analyzeFile(inputFile, CobolSourceFormatEnum.TANDEM);

		final CompilationUnit compilationUnit = program.getCompilationUnit("DisplayAtStatement");
		final ProgramUnit programUnit = compilationUnit.getProgramUnit();
		final ProcedureDivision procedureDivision = programUnit.getProcedureDivision();
		assertEquals(0, procedureDivision.getParagraphs().size());
		assertEquals(1, procedureDivision.getStatements().size());

		{
			final DisplayStatement displayStatement = (DisplayStatement) procedureDivision.getStatements().get(0);
			assertNotNull(displayStatement);
			assertEquals(StatementTypeEnum.DISPLAY, displayStatement.getStatementType());
			assertEquals(2, displayStatement.getOperands().size());

			{
				final Operand operand = displayStatement.getOperands().get(0);
				assertNotNull(operand.getOperandValueStmt());

				final CallValueStmt operandCallValueStmt = (CallValueStmt) operand.getOperandValueStmt();
				assertEquals(Call.CallType.UNDEFINED_CALL, operandCallValueStmt.getCall().getCallType());
			}

			{
				final Operand operand = displayStatement.getOperands().get(1);
				assertNotNull(operand.getOperandValueStmt());
				assertEquals("2", operand.getOperandValueStmt().getValue());
			}

			{
				assertNotNull(displayStatement.getAt());

				final ValueStmt atValueStmt = displayStatement.getAt().getAtValueStmt();
				assertNotNull(atValueStmt);

				final CallValueStmt atCallValueStmt = (CallValueStmt) atValueStmt;
				assertEquals(Call.CallType.UNDEFINED_CALL, atCallValueStmt.getCall().getCallType());
			}
		}
	}
}