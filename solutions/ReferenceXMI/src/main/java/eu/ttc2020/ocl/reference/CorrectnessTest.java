/**************************************************************************
Copyright 2020 Vietnamese-German-University

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

@author: ngpbh
***************************************************************************/

package eu.ttc2020.ocl.reference;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import eu.ttc2020.ocl.reference.resultset.ResultRow;
import eu.ttc2020.ocl.reference.resultset.ResultSet;

public class CorrectnessTest {

//  Configuration for database connection
	private static final String driver = "com.mysql.cj.jdbc.Driver";
	private static final String connectionDB = "jdbc:mysql://%s:%d/cardb%s?autoReconnect=true&useSSL=false";
//  
	private static final String host = "localhost";

	private static Connection con;

	private static boolean compare(List<String> expectedResult, List<String> actualResult) {
		if (expectedResult.size() != actualResult.size())
			return false;
		else {
			Collections.sort(expectedResult);
			Collections.sort(actualResult);
			for (int i = 0; i < expectedResult.size(); i++) {
				if (!expectedResult.get(i).equals(actualResult.get(i)))
					return false;
			}
		}
		return true;
	}

	private static ResultSet executeStatement(String inputStatement) throws SQLException {
		ResultSet actualResult = new ResultSet();
		List<ResultRow> rows = new ArrayList<ResultRow>();
		PreparedStatement st;
		st = con.prepareStatement(inputStatement);
		java.sql.ResultSet rs = st.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();

		List<String> columnNames = new ArrayList<>();
		for (int i = 1; i <= rsmd.getColumnCount(); i++) {
			columnNames.add(rsmd.getColumnLabel(i));
		}

		while (rs.next()) {
			List<Object> rowData = new ArrayList<>();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				rowData.add(rs.getObject(i));
			}
			ResultRow row = new ResultRow();
			HashMap<String, String> cols = new HashMap<String, String>();
			for (int colIndex = 0; colIndex < rsmd.getColumnCount(); colIndex++) {
				String objString = "";
				Object columnObject = rowData.get(colIndex);
				if (columnObject != null) {
					objString = columnObject.toString();
				}
				cols.put(columnNames.get(colIndex), objString);
			}
			row.setCols(cols);
			rows.add(row);
		}
		actualResult.setRows(rows);
		return actualResult;
	}

	public static List<String> getExpectedResult(int scenario, int stage, int challenge) throws IOException {
		final String scenarioFolder = String.format("scenario%d", scenario);
		final String expectedResultFile = String.format("Stage%dChallenge%d.res", stage, challenge);
		final List<String> expectedResultRows = Files
				.readAllLines(Paths.get("resources", scenarioFolder, expectedResultFile));
		return expectedResultRows;
	}

	private static List<String> getActualResult(ResultSet actualResultSet) throws Exception {
		List<String> actualResult = new ArrayList<String>();
		if (actualResultSet.getRows() == null)
			return actualResult;
		for (ResultRow row : actualResultSet.getRows()) {
			if (row.getCols().containsKey("res")) {
				actualResult.add(row.getCols().get("res"));
			} else
				throw new Exception("No res column defined in the given SQL statement");
		}
		return actualResult;
	}

	public static boolean check(Configuration c, int iScenario, String sqlStmString) throws Exception {
		final int port = c.getMySQLport();
		final String username = c.getMySQLUsername();
		final String password = c.getMySQLPassword();
		final int iStage = c.getStageIndex();
		final int iChallenge = c.getChallengeIndex();
		boolean isCorrect = false;
		Class.forName(driver);
		String connection = String.format(connectionDB, host, port, String.valueOf(iScenario));
		con = DriverManager.getConnection(connection, username, password);
		List<String> expectedResult = getExpectedResult(iScenario, iStage, iChallenge);
		ResultSet actualResultSet = executeStatement(sqlStmString);
		List<String> actualResult = getActualResult(actualResultSet);
		isCorrect = compare(expectedResult, actualResult);
		con.close();
		return isCorrect;
	}
}
