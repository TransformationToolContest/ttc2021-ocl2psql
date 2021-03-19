package eu.ttc2020.ocl.reference.ocl2psql;

import java.io.File;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.vgu.ocl2psql.main.OCL2PSQL_2;
import org.vgu.ocl2psql.ocl.roberts.exception.OclParseException;
import org.vgu.se.ocl.parser.OCLParser;
import org.vgu.se.sql.parser.SQLParser;

import com.vgu.se.jocl.expressions.Expression;

import net.sf.jsqlparser.statement.Statement;

/**
 * Shows an example of how to use the OCL2PSQL transformation tool in cooperate with the metamodels.
 */
public class SampleLauncher {

	private static final String FILEPATH = "resources/";
	private static final String OCL_FILENAME = "SampleOCLExp.xmi";
	private static final String SQL_XMI_FILENAME = "SampleSQLStm.xmi";

	public static void main(String[] args) throws IOException, OclParseException, ParseException {

		final String oclFilePath = String.format("%s%s", FILEPATH, OCL_FILENAME);
		File oclExpFile = new File(oclFilePath);
		final String sqlAsXMIFilePath = String.format("%s%s", FILEPATH, SQL_XMI_FILENAME);
		File sqlStmFile = new File(sqlAsXMIFilePath);

//		This code snippet shows how OCL2PSQL transforms a OCL expression in XMI format
//		to a SQL statement in XMI format.
//		OCL(XMI) --> OCL (Java) --> SQL (Java) --> SQL (XMI)
		OCL2PSQL_2 ocl2psql = new OCL2PSQL_2();
		Expression oclExp = OCLParser.convertToExp(oclExpFile.getAbsolutePath());
		Statement sqlStm = ocl2psql.mapOCLExpressionToSQLStatement(oclExp);
		sql.Statement sqlStmXMI = SQLParser.transform(sqlStm);

//		 This code snippet shows how we store the SQL statement model into a XMI file.
		SQLParser.saveEStatement(sqlStmFile, sqlStmXMI);

//		 This code snippet show how we transform a SQL statement from XMI to plain text,
//		 either from java hierarchy objects.
		String sqlPlain = sqlStm.toString();
		System.out.println(sqlPlain);
//		 or from xmi file.
		String sqlPlain2 = SQLParser.outputEStatementAsString(sqlStmFile);
		System.out.println(sqlPlain2);
	}

}
