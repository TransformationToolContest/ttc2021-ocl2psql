package eu.ttc2020.ocl.reference;

import java.io.File;
import java.io.IOException;

import org.vgu.ocl2psql.main.OCL2PSQL_2;
import org.vgu.se.sql.parser.SQLParser;
import org.vgu.ttc2020.model.TTCReturnModel;

import sql.Statement;

/**
 * Shows an example of how to use the OCL2PSQL transformation tool
 */
public class SampleLauncher {

	private static final String FILEPATH = "resources/";
	private static final String OCL_FILENAME = "SampleOCLExp.xmi";
	private static final String SQL_XMI_FILENAME = "SampleSQLStm.xmi";

	public static void main(String[] args) throws IOException {

		OCL2PSQL_2 ocl2psql = new OCL2PSQL_2();
		final String oclFilePath = String.format("%s%s", FILEPATH, OCL_FILENAME);
		File oclExpFile = new File(oclFilePath);

//		 This code snippet shows how OCL2PSQL transforms a OCL expression in XMI format
//		 to a SQL statement in XMI format.
		TTCReturnModel returnModel = ocl2psql.fromOCLXMIFileToSQLXMIStatement(oclExpFile);
		Statement sqlStm = returnModel.getStatement();

//		 This code snippet shows how we store the SQL statement model into a XMI file.
		final String sqlAsXMIFilePath = String.format("%s%s", FILEPATH, SQL_XMI_FILENAME);
		File sqlStmFile = new File(sqlAsXMIFilePath);
		SQLParser.saveEStatement(sqlStmFile, sqlStm);

//		 This code snippet show how we transform a SQL statement from XMI to plain text,
//		 either from java hierarchy objects.
		String sqlPlain = SQLParser.outputEStatementAsString(sqlStm);
		System.out.println(sqlPlain);
//		 or from xmi file.
		String sqlPlain2 = SQLParser.outputEStatementAsString(sqlStmFile);
		System.out.println(sqlPlain2);
	}

}
