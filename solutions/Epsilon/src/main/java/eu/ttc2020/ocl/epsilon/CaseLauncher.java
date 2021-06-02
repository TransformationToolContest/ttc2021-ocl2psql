package eu.ttc2020.ocl.epsilon;

import java.io.File;
import java.util.Map;

import eu.ttc2020.ocl.epsilon.tx.Solution;
import ocl.dm.DmPackage;
import ocl.exp.ExpPackage;
import sql.SqlPackage;

/**
 * Reads the various environment variables provided by the Python script, sets
 * up the case solver and prints out the results according to the CSV header
 * file.
 */
public class CaseLauncher {

	private static final String ENV_TOOL = "Tool";
	private static final String ENV_PHASE_INDEX = "StageIndex";
	private static final String ENV_CHALLENGE_INDEX = "ChallengeIndex";
	private static final String ENV_RUN_INDEX = "RunIndex";
	private static final String ENV_OCL_XMI = "PathToOCLXMI";
	private static final String ENV_OCL_TEXT = "OCLQuery";
	private static final String ENV_SCHEMA_XMI = "PathToSchemaXMI";
	private static final String ENV_MYSQL_USERNAME = "MySQLUsername";
	private static final String ENV_MYSQL_PASSWORD = "MySQLPassword";
	private static final String ENV_MYSQL_PORT = "MySQLPort";

	private static Configuration createConfiguration() {
		Configuration c = new Configuration();
		final Map<String, String> env = System.getenv();

		final String sTool = env.get(ENV_TOOL);
		if (sTool != null) {
			c.setTool(sTool);
		}

		final String sStageIndex = env.get(ENV_PHASE_INDEX);
		if (sStageIndex != null) {
			c.setStageIndex(Integer.parseInt(sStageIndex));
		}
		
		final String sChallengeIndex = env.get(ENV_CHALLENGE_INDEX);
		if (sChallengeIndex != null) {
			c.setChallengeIndex(Integer.parseInt(sChallengeIndex));
		}

		final String sRunIndex = env.get(ENV_RUN_INDEX);
		if (sRunIndex != null) {
			c.setRunIndex(Integer.parseInt(sRunIndex));
		}

		final String sPathToXMI = env.get(ENV_OCL_XMI);
		if (sPathToXMI != null) {
			final File fXMI = new File(sPathToXMI);
			if (fXMI.canRead()) {
				c.setOCLQueryXMIFile(fXMI);
			} else {
				throw new IllegalArgumentException("Cannot read XMI file " + fXMI);
			}
		}

		final String sOCLQuery = env.get(ENV_OCL_TEXT);
		if (sOCLQuery != null) {
			c.setOCLQuery(sOCLQuery);
		}

		final String sPathToSchemaXMI = env.get(ENV_SCHEMA_XMI);
		if (sPathToSchemaXMI != null) {
			final File fXMI = new File(sPathToSchemaXMI);
			if (fXMI.canRead()) {
				c.setDatabaseSchemaXMIFile(fXMI);
			} else {
				throw new IllegalArgumentException("Cannot read XMI file " + fXMI);
			}
		}
		
		final String sMySQLUsername = env.get(ENV_MYSQL_USERNAME);
		if (sMySQLUsername != null) {
			c.setMySQLUsername(sMySQLUsername);
		}
		
		final String sMySQLPassword = env.get(ENV_MYSQL_PASSWORD);
		if (sMySQLPassword != null) {
			c.setMySQLPassword(sMySQLPassword);
		}
		
		final String sMySQLPort = env.get(ENV_MYSQL_PORT);
		if (sMySQLPort != null) {
			c.setMySQLport(Integer.parseInt(sMySQLPort));
		}
		
		return c;
	}

	public static void main(String[] args) {
		DmPackage.eINSTANCE.getName();
		ExpPackage.eINSTANCE.getName();
		SqlPackage.eINSTANCE.getName();
		
		Configuration c = createConfiguration();
		new Solution().run(c);
	}
}
