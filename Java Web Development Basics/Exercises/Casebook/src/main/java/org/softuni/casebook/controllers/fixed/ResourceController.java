package org.softuni.casebook.controllers.fixed;

import org.softuni.casebook.constants.CasebookConstants;
import org.softuni.casebook.constants.ErrorMessages;
import org.softuni.casebook.controllers.BaseController;
import org.softuni.casebook.utility.MimeTypeManager;
import org.softuni.javache.http.HttpRequest;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpSessionStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceController extends BaseController {

    public ResourceController(HttpSessionStorage sessionStorage) {
        super(sessionStorage);
    }

    public byte[] processResourceRequest(HttpRequest httpRequest, HttpResponse httpResponse) {
        String assetPath = CasebookConstants.PUBLIC_RESOURCES_PATH +
                httpRequest.getRequestUrl();

        File file = new File(assetPath);

        if (!file.exists() || file.isDirectory()) {
            return super.notFound(ErrorMessages.NOT_FOUND_ERROR_MESSAGE.getBytes(), httpResponse);
        }

        byte[] result;

        try {
            result = Files.readAllBytes(Paths.get(assetPath));
        } catch (IOException e) {
            return super.internalServerError(ErrorMessages.INTERNAL_SERVER_ERROR_MESSAGE.getBytes(), httpResponse);
        }

        httpResponse.addHeader("Content-Type", MimeTypeManager.getMimeType(file.getName()));
        httpResponse.addHeader("Content-Length", result.length + "");
        httpResponse.addHeader("Content-Disposition", "inline");

        return this.ok(result, httpResponse);
    }
}
