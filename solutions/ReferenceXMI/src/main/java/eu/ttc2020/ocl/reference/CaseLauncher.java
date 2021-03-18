package eu.ttc2020.ocl.reference;

import java.io.File;
import java.util.Map;

/**
 * Reads the various environment variables provided by the Python script, sets
 * up the case solver and prints out the results according to the CSV header
 * file.
 */
public class CaseLauncher {

	private static final String ENV_TOOL = "TOOL";
	private static final String ENV_PHASE_INDEX = "STAGEINDEX";
	private static final String ENV_CHALLENGE_INDEX = "CHALLENGEINDEX";
	private static final String ENV_RUN_INDEX = "RUNINDEX";
	private static final String ENV_OCL_XMI = "PATHTOOCLXMI";
	private static final String ENV_OCL_TEXT = "OCLQUERY";
	private static final String ENV_SCHEMA_XMI = "PATHTOSCHEMAXMI";

	private static Configuration createConfiguration() {
		Configuration c = new Configuration();
		final Map<String, String> env = System.getenv();
		
//		for(String key : env.keySet()) {
//		    System.out.println(key + " " + env.get(key));
//		}
		
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
		
		return c;
	}

	public static void main(String[] args) {
		Configuration c = createConfiguration();
		new Solution().run(c);
	}
}
