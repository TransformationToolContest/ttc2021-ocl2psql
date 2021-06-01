package eu.ttc2020.ocl.epsilon;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.launch.EtlRunConfiguration;

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
		sourceProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, resolveResource(OCL_MM_RESOURCE_PATH));
		sourceProperties.setProperty(EmfModel.PROPERTY_MODEL_URI, getAbsoluteURIString(sourceModelFile));
		sourceProperties.setProperty(EmfModel.PROPERTY_REUSE_UNMODIFIED_FILE_BASED_METAMODELS, true + "");
		sourceProperties.setProperty(EmfModel.PROPERTY_READONLOAD, true + "");
		sourceProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, false + "");

		StringProperties targetProperties = new StringProperties();
		targetProperties.setProperty(EmfModel.PROPERTY_NAME, "SQL");
		targetProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, resolveResource(SQL_MM_RESOURCE_PATH));
		targetProperties.setProperty(EmfModel.PROPERTY_MODEL_URI, getAbsoluteURIString(targetModelFile));
		targetProperties.setProperty(EmfModel.PROPERTY_REUSE_UNMODIFIED_FILE_BASED_METAMODELS, true + "");
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

		for (File oclXMIFile : modelsFolder.listFiles(f -> f.getName().startsWith("Stage") && f.getName().endsWith(".xmi"))) {
			File sqlFile = new File("ocl-" + oclXMIFile.getName());
			new Transformation(oclXMIFile, sqlFile).run();
		}
	}

}
