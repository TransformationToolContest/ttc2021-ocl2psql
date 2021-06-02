package eu.ttc2020.ocl.epsilon.tx;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.regex.Pattern;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.epsilon.egl.formatter.language.LanguageFormatter;
import org.eclipse.epsilon.egl.launch.EglRunConfiguration;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;

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

	private final Resource sqlResource;

	public SQL2Text(Resource sqlResource) {
		this.sqlResource = sqlResource;
	}

	public String run() throws Exception {
		EglRunConfiguration runConfig = EglRunConfiguration.Builder()
			.withScript(Paths.get(getClass().getResource(EGL_RESOURCE_PATH).toURI()))
			.withModel(new InMemoryEmfModel("SQL", sqlResource))
			.withParallelism(-1) // parallel EGL not supported yet
			.build();

		runConfig.getModule().setDefaultFormatters(Collections.singleton(new SQLFormatter()));
		runConfig.run();
		String result = (String) runConfig.getResult();
		runConfig.dispose();
		
		return result;
	}
	
}
