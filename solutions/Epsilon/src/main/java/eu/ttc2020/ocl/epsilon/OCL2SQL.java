package eu.ttc2020.ocl.epsilon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.etl.launch.EtlRunConfiguration;

import ocl.dm.DmPackage;
import ocl.exp.ExpPackage;
import sql.SqlPackage;

/**
 * Java wrapper for M2M transformation from OCL queries serialised in XMI using the
 * case's simplified OCL metamodel, to SQL queries also in XMI using the case's
 * simplified SQL metamodel.
 */
public class OCL2SQL {

	private static final String ETL_RESOURCE_PATH = "/etl/ocl2psql.etl";

	private final File sourceModelFile;
	private final File targetModelFile;

	public OCL2SQL(File source, File target) {
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

		EtlRunConfiguration runConfig = EtlRunConfiguration.Builder()
			.withScript(Paths.get(getClass().getResource(ETL_RESOURCE_PATH).toURI()))
			.withModel(new EmfModel(), sourceProperties)
			.withModel(new EmfModel(), targetProperties)
			.build();

		runConfig.run();
		runConfig.dispose();
	}

	private String getAbsoluteURIString(File f) throws IOException {
		return f.getCanonicalFile().toURI().toString();
	}
	
}
