package eu.ttc2020.ocl.epsilon;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.regex.Pattern;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.egl.formatter.language.LanguageFormatter;
import org.eclipse.epsilon.egl.launch.EglRunConfiguration;
import org.eclipse.epsilon.emc.emf.EmfModel;

import sql.SqlPackage;

/**
 * Java wrapper for M2T transformation from XMI-serialised SQL queries to their
 * textual representation, using indentation.
 */
public class SQL2Text {

	private static class SQLFormatter extends LanguageFormatter {
		private static final String increasePattern = "\\(\\s*$";
		private static final String decreasePattern = "^\\)";
			
		public SQLFormatter() {
			super(Pattern.compile(increasePattern, Pattern.MULTILINE),
			      Pattern.compile(decreasePattern, Pattern.MULTILINE));
		}
	}
	
	private static final String EGL_RESOURCE_PATH = "/egl/sqlAST2Text.egl";

	private final File sourceModelFile;

	public SQL2Text(File source, File target) {
		this.sourceModelFile = source;
	}

	public String run() throws Exception {
		StringProperties sourceProperties = new StringProperties();
		sourceProperties.setProperty(EmfModel.PROPERTY_NAME, "SQL");
		sourceProperties.setProperty(EmfModel.PROPERTY_METAMODEL_URI, SqlPackage.eNS_URI);
		sourceProperties.setProperty(EmfModel.PROPERTY_MODEL_URI, getAbsoluteURIString(sourceModelFile));
		sourceProperties.setProperty(EmfModel.PROPERTY_READONLOAD, true + "");
		sourceProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, false + "");

		EglRunConfiguration runConfig = EglRunConfiguration.Builder()
			.withScript(Paths.get(getClass().getResource(EGL_RESOURCE_PATH).toURI()))
			.withModel(new EmfModel(), sourceProperties)
			.withParallelism(-1) // parallel EGL not supported yet
			.build();

		runConfig.getModule().setDefaultFormatters(Collections.singleton(new SQLFormatter()));
		runConfig.run();
		String result = (String) runConfig.getResult();
		runConfig.dispose();
		
		return result;
	}

	private String getAbsoluteURIString(File f) throws IOException {
		return f.getCanonicalFile().toURI().toString();
	}
	
}
