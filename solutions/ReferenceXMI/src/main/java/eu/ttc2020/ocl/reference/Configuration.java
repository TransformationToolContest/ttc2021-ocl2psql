package eu.ttc2020.ocl.reference;

import java.io.File;

/**
 * Stores the relevant configuration options read from the environment
 * variables.
 */
public class Configuration {

	private int stageIndex, challengeIndex, runIndex;
	private File fOCLQueryXMI, fSchemaXMI;
	private String sOCLQuery;
	private String sTool;
	private String mySQLUsername, mySQLPassword;
	private int mySQLport;

	/**
	 * Returns the name of the tool (solution).
	 */
	public String getTool() {
		return sTool;
	}

	/**
	 * Sets the name of the tool (solution).
	 */
	public void setTool(String tool) {
		this.sTool = tool;
	}

	/**
	 * Returns the 0-based identifier for this stage.
	 */
	public int getStageIndex() {
		return stageIndex;
	}

	/**
	 * Sets the 0-based identifier for this stage.
	 */
	public void setStageIndex(int stage) {
		this.stageIndex = stage;
	}

	/**
	 * Returns the 0-based identifier for this challenge within the stage.
	 */
	public int getChallengeIndex() {
		return challengeIndex;
	}

	/**
	 * Sets the 0-based identifier for this challenge within the stage.
	 */
	public void setChallengeIndex(int challenge) {
		this.challengeIndex = challenge;
	}

	/**
	 * Returns the 0-based run index (each experiment is repeated a number of
	 * times).
	 */
	public int getRunIndex() {
		return runIndex;
	}

	/**
	 * Sets the 0-based run index (each experiment is repeated a number of times).
	 */
	public void setRunIndex(int runIndex) {
		this.runIndex = runIndex;
	}

	/**
	 * Returns the {@link File} with the XMI representation of the OCL query to be
	 * transformed.
	 */
	public File getOCLQueryXMIFile() {
		return fOCLQueryXMI;
	}

	/**
	 * Sets the {@link File} with the XMI representation of the OCL query to be
	 * transformed.
	 */
	public void setOCLQueryXMIFile(File fChallengeOCL) {
		this.fOCLQueryXMI = fChallengeOCL;
	}

	/**
	 * Returns a string with the OCL query to be transformed.
	 */
	public String getOCLQuery() {
		return sOCLQuery;
	}

	/**
	 * Sets the string with the OCL query to be transformed.
	 */
	public void setOCLQuery(String sChallengeOCL) {
		this.sOCLQuery = sChallengeOCL;
	}

	/**
	 * Returns the {@link File} with the XMI representation of the database schema.
	 */
	public File getDatabaseSchemaXMIFile() {
		return fSchemaXMI;
	}

	/**
	 * Sets the {@link File} with the XMI representation of the database schema.
	 */
	public void setDatabaseSchemaXMIFile(File schemaXMI) {
		this.fSchemaXMI = schemaXMI;
	}

    @Override
    public String toString() {
        return "Configuration [stageIndex=" + stageIndex + ", challengeIndex="
            + challengeIndex + ", runIndex=" + runIndex + ", fOCLQueryXMI="
            + fOCLQueryXMI + ", fSchemaXMI=" + fSchemaXMI + ", sOCLQuery="
            + sOCLQuery + ", sTool=" + sTool + "]";
    }

	public String getMySQLUsername() {
		return mySQLUsername;
	}

	public void setMySQLUsername(String mySQLUsername) {
		this.mySQLUsername = mySQLUsername;
	}

	public String getMySQLPassword() {
		return mySQLPassword;
	}

	public void setMySQLPassword(String mySQLPassword) {
		this.mySQLPassword = mySQLPassword;
	}

	public int getMySQLport() {
		return mySQLport;
	}

	public void setMySQLport(int mySQLport) {
		this.mySQLport = mySQLport;
	}

}
