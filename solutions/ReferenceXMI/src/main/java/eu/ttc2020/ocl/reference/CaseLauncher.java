package eu.ttc2020.ocl.reference;

import java.io.File;
import java.util.Map;

/**
 * Reads the various environment variables provided by the Python script, sets
 * up the case solver and prints out the results according to the CSV header
 * file.
 */
public class CaseLauncher {

	private static final String ENV_TOOL = "Tool";
	private static final String ENV_PHASE_INDEX = "PhaseIndex";
	private static final String ENV_PHASE_NAME = "PhaseName";
	private static final String ENV_CHALLENGE_INDEX = "ChallengeIndex";
	private static final String ENV_RUN_INDEX = "RunIndex";
	private static final String ENV_OCL_XMI = "PathToOCLXMI";
	private static final String ENV_OCL_TEXT = "OCLQuery";
	private static final String ENV_SCHEMA_XMI = "PathToSchemaXMI";

	private static Configuration createConfiguration() {
		Configuration c = new Configuration();
		final Map<String, String> env = System.getenv();

		final String sTool = env.get(ENV_TOOL);
		if (sTool != null) {
			c.setTool(sTool);
		}

		final String sPhaseIndex = env.get(ENV_PHASE_INDEX);
		if (sPhaseIndex != null) {
			c.setPhaseIndex(Integer.parseInt(sPhaseIndex));
		}

		final String sPhaseName = env.get(ENV_PHASE_NAME);
		if (sPhaseName != null) {
			c.setPhaseName(sPhaseName);
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
