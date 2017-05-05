package gov.nist.asg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Test;

import io.proleap.cobol.CobolTestSupport;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.DataDivision;
import io.proleap.cobol.asg.metamodel.data.communication.CommunicationDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.communication.CommunicationSection;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntryGroup;
import io.proleap.cobol.asg.metamodel.data.workingstorage.WorkingStorageSection;
import io.proleap.cobol.asg.metamodel.environment.EnvironmentDivision;
import io.proleap.cobol.asg.metamodel.environment.configuration.ConfigurationSection;
import io.proleap.cobol.asg.metamodel.environment.configuration.object.ObjectComputerParagraph;
import io.proleap.cobol.asg.metamodel.environment.configuration.source.SourceComputerParagraph;
import io.proleap.cobol.asg.metamodel.procedure.Paragraph;
import io.proleap.cobol.asg.metamodel.procedure.ProcedureDivision;
import io.proleap.cobol.asg.metamodel.procedure.Statement;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.runner.impl.CobolParserRunnerImpl;
import io.proleap.cobol.preprocessor.CobolPreprocessor.CobolSourceFormatEnum;

public class CM210MTest extends CobolTestSupport {

	@Test
	public void test() throws Exception {
		final File inputFile = new File("src/test/resources/gov/nist/CM201M.CBL");
		final Program program = new CobolParserRunnerImpl().analyzeFile(inputFile, CobolSourceFormatEnum.FIXED);
		final CompilationUnit compilationUnit = program.getCompilationUnit("CM201M");
		final ProgramUnit programUnit = compilationUnit.getProgramUnit();

		{
			final EnvironmentDivision environmentDivision = programUnit.getEnvironmentDivision();

			{
				final ConfigurationSection configurationSection = environmentDivision.getConfigurationSection();
				final SourceComputerParagraph sourceComputerParagraph = configurationSection
						.getSourceComputerParagraph();
				assertEquals("XXXXX082", sourceComputerParagraph.getName());

				final ObjectComputerParagraph objectComputerParagraph = configurationSection
						.getObjectComputerParagraph();
				assertEquals("XXXXX083", objectComputerParagraph.getName());
			}
		}

		{
			final DataDivision dataDivision = programUnit.getDataDivision();
			assertNotNull(dataDivision.getWorkingStorageSection());

			{
				final WorkingStorageSection workingStorageSection = dataDivision.getWorkingStorageSection();
				assertEquals(4, workingStorageSection.getRootDataDescriptionEntries().size());

				{
					final DataDescriptionEntry dataDescriptionEntry = workingStorageSection
							.findDataDescriptionEntry("MSG-72");
					assertEquals(DataDescriptionEntry.Type.SCALAR, dataDescriptionEntry.getType());
				}

				{
					final DataDescriptionEntry dataDescriptionEntry = workingStorageSection
							.findDataDescriptionEntry("RECOGNITION-MSG-1");
					assertEquals(DataDescriptionEntry.Type.GROUP, dataDescriptionEntry.getType());

					{
						final DataDescriptionEntryGroup dataDescriptionEntryGroup = (DataDescriptionEntryGroup) dataDescriptionEntry;
						assertEquals(3, dataDescriptionEntryGroup.getDataDescriptionEntries().size());
					}
				}

				{
					final DataDescriptionEntry dataDescriptionEntry = workingStorageSection
							.findDataDescriptionEntry("RECOGNITION-MSG-2");
					assertEquals(DataDescriptionEntry.Type.GROUP, dataDescriptionEntry.getType());

					{
						final DataDescriptionEntryGroup dataDescriptionEntryGroup = (DataDescriptionEntryGroup) dataDescriptionEntry;
						assertEquals(3, dataDescriptionEntryGroup.getDataDescriptionEntries().size());
					}
				}

				{
					final DataDescriptionEntry dataDescriptionEntry = workingStorageSection
							.findDataDescriptionEntry("RECOGNITION-MSG-3");
					assertEquals(DataDescriptionEntry.Type.GROUP, dataDescriptionEntry.getType());

					{
						final DataDescriptionEntryGroup dataDescriptionEntryGroup = (DataDescriptionEntryGroup) dataDescriptionEntry;
						assertEquals(0, dataDescriptionEntryGroup.getDataDescriptionEntries().size());
					}
				}
			}

			assertNull(dataDivision.getFileSection());
			assertNotNull(dataDivision.getCommunicationSection());

			{
				final CommunicationSection communicationSection = dataDivision.getCommunicationSection();
				assertNotNull(communicationSection);
				assertEquals(2, communicationSection.getRootDataDescriptionEntries().size());

				{
					final CommunicationDescriptionEntry communicationDescriptionEntry = communicationSection
							.getCommunicationDescriptionEntry("CM-INQUE-1");
					assertNotNull(communicationDescriptionEntry);
					assertEquals(CommunicationDescriptionEntry.Type.INPUT, communicationDescriptionEntry.getType());
				}

				{
					final CommunicationDescriptionEntry communicationDescriptionEntry = communicationSection
							.getCommunicationDescriptionEntry("CM-OUTQUE-1");
					assertNotNull(communicationDescriptionEntry);
					assertEquals(CommunicationDescriptionEntry.Type.OUTPUT, communicationDescriptionEntry.getType());
				}

				{
					final DataDescriptionEntry dataDescriptionEntry = communicationSection
							.findDataDescriptionEntry("INQUE-1-RECORD");
					assertEquals(DataDescriptionEntry.Type.GROUP, dataDescriptionEntry.getType());

					{
						final DataDescriptionEntryGroup dataDescriptionEntryGroup = (DataDescriptionEntryGroup) dataDescriptionEntry;
						assertEquals(6, dataDescriptionEntryGroup.getDataDescriptionEntries().size());
					}
				}

				{
					final DataDescriptionEntry dataDescriptionEntry = communicationSection
							.findDataDescriptionEntry("OUTQUE-1-RECORD");
					assertEquals(DataDescriptionEntry.Type.GROUP, dataDescriptionEntry.getType());

					{
						final DataDescriptionEntryGroup dataDescriptionEntryGroup = (DataDescriptionEntryGroup) dataDescriptionEntry;
						assertEquals(4, dataDescriptionEntryGroup.getDataDescriptionEntries().size());
					}
				}
			}
		}

		{
			final ProcedureDivision procedureDivision = programUnit.getProcedureDivision();
			assertEquals(3, procedureDivision.getParagraphs().size());
			assertEquals(0, procedureDivision.getStatements().size());

			{
				final Paragraph paragraph = procedureDivision.getParagraphs().get(0);
				assertEquals("CM201M-INIT", paragraph.getParagraphName().getName());
				assertEquals(1, paragraph.getStatements().size());

				{
					final Statement statement = paragraph.getStatements().get(0);
					assertEquals(StatementTypeEnum.ENABLE, statement.getStatementType());
				}
			}

			{
				final Paragraph paragraph = procedureDivision.getParagraphs().get(1);
				assertEquals("TAKE-NEXT-MSG", paragraph.getParagraphName().getName());
				assertEquals(11, paragraph.getStatements().size());

				{
					final Statement statement = paragraph.getStatements().get(0);
					assertEquals(StatementTypeEnum.RECEIVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(1);
					assertEquals(StatementTypeEnum.MOVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(2);
					assertEquals(StatementTypeEnum.MOVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(3);
					assertEquals(StatementTypeEnum.MOVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(4);
					assertEquals(StatementTypeEnum.SEND, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(5);
					assertEquals(StatementTypeEnum.MOVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(6);
					assertEquals(StatementTypeEnum.SEND, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(7);
					assertEquals(StatementTypeEnum.MOVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(8);
					assertEquals(StatementTypeEnum.SEND, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(9);
					assertEquals(StatementTypeEnum.ACCEPT, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(10);
					assertEquals(StatementTypeEnum.IF, statement.getStatementType());
				}
			}

			{
				final Paragraph paragraph = procedureDivision.getParagraphs().get(2);
				assertEquals("SOMETHING-IS-WRONG-HERE", paragraph.getParagraphName().getName());
				assertEquals(6, paragraph.getStatements().size());

				{
					final Statement statement = paragraph.getStatements().get(0);
					assertEquals(StatementTypeEnum.MOVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(1);
					assertEquals(StatementTypeEnum.SEND, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(2);
					assertEquals(StatementTypeEnum.MOVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(3);
					assertEquals(StatementTypeEnum.MOVE, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(4);
					assertEquals(StatementTypeEnum.SEND, statement.getStatementType());
				}

				{
					final Statement statement = paragraph.getStatements().get(5);
					assertEquals(StatementTypeEnum.STOP, statement.getStatementType());
				}
			}
		}
	}
}
