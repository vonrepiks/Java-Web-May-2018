package org.softuni.summer.utility;

import org.softuni.summer.api.Model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class TemplateEngine {
    private static final String TEMPLATE_FILE_EXTENSION = ".html";

    private final String applicationTemplateFolder;

    public TemplateEngine(String applicationTemplateFolder) {
        this.applicationTemplateFolder = applicationTemplateFolder;
    }

    private String renderTemplate(String templateContent, Model model) {
        String renderedContent = templateContent;

        for (Map.Entry<String,Object> viewDataObject : model.getAttributes().entrySet()) {
            renderedContent = renderedContent.replace("${" + viewDataObject.getKey() + "}", viewDataObject.getValue().toString());
        }
        return renderedContent;
    }

    private Model getNewModel() {
        return new Model();
    }

    public String loadTemplate(String templateName) throws IOException {
        return loadTemplate(templateName, this.getNewModel());
    }

    public String loadTemplate(String templateName, Model model) throws IOException {
        String templateContent = String.join("",
                Files.readAllLines(
                        Paths.get(this.applicationTemplateFolder + templateName + TEMPLATE_FILE_EXTENSION)));
        System.out.println(templateContent);
        System.out.println(model);
        if (model != null) {
            templateContent =  this.renderTemplate(templateContent, model);
        }

        System.out.println(templateContent);

        return templateContent;
    }
}
