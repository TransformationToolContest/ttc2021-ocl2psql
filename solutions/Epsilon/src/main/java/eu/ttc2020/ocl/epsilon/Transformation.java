package eu.ttc2020.ocl.epsilon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.launch.EtlRunConfiguration;
import org.vgu.se.sql.parser.SQLParser;

import ocl.dm.DmPackage;
import ocl.exp.ExpPackage;
import sql.SqlPackage;

/**
 * Java wrapper for M2M transformation from OCL queries serialised in XMI using the
 * case's simplified OCL metamodel, to SQL queries also in XMI using the case's
 * simplified SQL metamodel.
 *
 * This also includes a main method for quickly testing out the transformation across
 * all challenges.
 */
public class Transformation {

	private static final String ETL_RESOURCE_PATH = "/etl/ocl2psql.etl";
	private static final String OCL_MM_RESOURCE_PATH = "/metamodels/ocl.ecore";
	private static final String SQL_MM_RESOURCE_PATH = "/metamodels/sql.ecore";

	private final File sourceModelFile;
	private final File targetModelFile;

	public Transformation(File source, File target) {
		this.sourceModelFile = source;
		this.targetModelFile = target;
	}

	public void run() throws Exception {
		StringProperties sourceProperties = new StringProperties();
		sourceProperties.setProperty(EmfModel.PROPERTY_NAME, "OCL");
		sourceProperties.setProperty(EmfModel.PROPERTY_METAMODEL_URI, DmPackage.eNS_URI + "," + ExpPackage.eNS_URI);
		sourceProperties.setProperty(EmfModel.PROPERTY_MODEL_URI, getAbsoluteURIString(sourceModelFile));
		sourceProperties.setProperty(EmfModel.PROPERTY_READONLOAD, true + "");
		sourceProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, false + "");

		StringProperties targetProperties = new StringProperties();
		targetProperties.setProperty(EmfModel.PROPERTY_NAME, "SQL");
		targetProperties.setProperty(EmfModel.PROPERTY_METAMODEL_URI, SqlPackage.eNS_URI);
		targetProperties.setProperty(EmfModel.PROPERTY_MODEL_URI, getAbsoluteURIString(targetModelFile));
		targetProperties.setProperty(EmfModel.PROPERTY_READONLOAD, false + "");
		targetProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, true + "");

		EtlModule module = new EtlModule();
		URL etlScriptResource = getClass().getResource(ETL_RESOURCE_PATH);
		module.parse(etlScriptResource);
		EtlRunConfiguration runConfig = EtlRunConfiguration.Builder()
			.withScript(Paths.get(getClass().getResource(ETL_RESOURCE_PATH).toURI()))
			.withModel(new EmfModel(), sourceProperties)
			.withModel(new EmfModel(), targetProperties)
			.build();

		runConfig.run();
		runConfig.dispose();
	}

	private String resolveResource(final String resourcePath) throws URISyntaxException {
		return getClass().getResource(resourcePath).toURI().toString();
	}

	private String getAbsoluteURIString(File f) throws IOException {
		return f.getCanonicalFile().toURI().toString();
	}

	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			System.err.println("Usage: java -jar [...] pathToModelsFolder");
			System.exit(1);
		}

		final String modelsFolderPath = args[0];
		final File modelsFolder = new File(modelsFolderPath);
		if (!modelsFolder.isDirectory()) {
			System.err.println(String.format("'%s' is not a directory", modelsFolderPath));
			System.exit(2);
		}

		// Ensure all static metamodels are registered
		DmPackage.eINSTANCE.getName();
		ExpPackage.eINSTANCE.getName();
		SqlPackage.eINSTANCE.getName();

		File outputFolder = new File("outputs");
		if (outputFolder.isDirectory()) {
			for (File f : outputFolder.listFiles()) {
				f.delete();
			}
		} else {
			outputFolder.mkdirs();
		}

		File[] challengeFiles = modelsFolder.listFiles(f -> f.getName().startsWith("Stage") && f.getName().endsWith(".xmi"));
		Arrays.sort(challengeFiles, (a, b) -> a.getName().compareTo(b.getName()));
		for (File oclXMIFile : challengeFiles) {
			final File sqlXMIFile = new File(outputFolder, "ocl-" + oclXMIFile.getName());
			System.out.println(String.format("Transforming %s into %s", oclXMIFile.getPath(), sqlXMIFile.getPath()));
			new Transformation(oclXMIFile, sqlXMIFile).run();

			final String sqlStatement = SQLParser.outputEStatementAsString(sqlXMIFile);
			final File sqlTextFile = new File(outputFolder, "ocl-" + oclXMIFile.getName().replaceAll("[.]xmi$", ".sql")); 
			System.out.println(String.format("Transforming %s into %s", sqlXMIFile.getPath(), sqlTextFile.getPath()));
			try (FileWriter fw = new FileWriter(sqlTextFile); PrintWriter pw = new PrintWriter(fw)) {
				pw.println(sqlStatement);
			}
		}
	}

}
