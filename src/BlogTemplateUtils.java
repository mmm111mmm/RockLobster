import java.io.IOException;
import java.util.List;

import entities.FileTemplate;


public class BlogTemplateUtils {

	public static void generatedContentFromTemplates(List<FileTemplate> templates) throws IOException {
		for (FileTemplate template: templates) {
			template.generateContent();
		}
	}

}
