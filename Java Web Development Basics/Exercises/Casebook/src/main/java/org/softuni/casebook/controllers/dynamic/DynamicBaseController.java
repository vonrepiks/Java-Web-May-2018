package org.softuni.casebook.controllers.dynamic;

import org.softuni.casebook.constants.CasebookConstants;
import org.softuni.casebook.constants.ErrorMessages;
import org.softuni.casebook.controllers.BaseController;
import org.softuni.casebook.utility.LimeLeaf;
import org.softuni.database.entities.User;
import org.softuni.database.repositories.UserRepository;
import org.softuni.javache.http.HttpCookie;
import org.softuni.javache.http.HttpRequest;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpSessionStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class DynamicBaseController extends BaseController {
    private static final String HTML_EXTENSION_AND_SEPARATOR = ".html";
    private LimeLeaf limeLeaf;

    protected DynamicBaseController(HttpSessionStorage sessionStorage) {
        super(sessionStorage);
        this.limeLeaf = LimeLeaf.getInstance();
    }

    private String getHeaderContent(String headerPath) {
        try {
            List<String> userHeader = Files.readAllLines(Paths.get(headerPath));
            return String.join("", userHeader);
        } catch (IOException e) {
            return null;
        }
    }

    private String getHeader(HttpRequest httpRequest) {
        if (super.isLoggedIn(httpRequest)) {
            return this.getHeaderContent(CasebookConstants.USER_HEADER_HTML);
        } else {
            return this.getHeaderContent(CasebookConstants.GUEST_HEADER_HTML);
        }
    }

    private String getMessage(String type) {
        try {
            if (!this.limeLeaf.getViewData().get(type).equals("")) {
                List<String> view = Files.readAllLines(Paths.get(CasebookConstants.TEMPLATES_PATH + type + HTML_EXTENSION_AND_SEPARATOR));
                return String.join("", view);
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private String getView(String template) {
        try {
            List<String> view = Files.readAllLines(Paths.get(CasebookConstants.TEMPLATES_PATH + template + HTML_EXTENSION_AND_SEPARATOR));
            return String.join("", view);
        } catch (IOException e) {
            return null;
        }
    }

    private String getFooter() {
        try {
            List<String> userHeader = Files.readAllLines(Paths.get(CasebookConstants.FOOTER_PATH + "footer" + HTML_EXTENSION_AND_SEPARATOR));
            return String.join("", userHeader);
        } catch (IOException e) {
            return null;
        }
    }

    private String getBaseView() {
        try {
            List<String> view = Files.readAllLines(Paths.get(CasebookConstants.BASE_VIEW_HTML));
            return String.join("", view);
        } catch (IOException e) {
            return null;
        }
    }

    protected User getCurrentUser(HttpRequest request, UserRepository userRepository) {
        HttpCookie cookie = request.getCookies().get(CasebookConstants.SERVER_SESSION_TOKEN);
        String userId = super.getSessionStorage()
                .getById(cookie.getValue())
                .getAttributes()
                .get("user-id")
                .toString();

        User user = userRepository.findById(userId);
        return user;
    }

    protected byte[] view(String template, HttpRequest httpRequest, HttpResponse httpResponse) {
        String baseViewContent = this.getBaseView();

        String headerContent = this.getHeader(httpRequest);
        String errorContent = this.getMessage(CasebookConstants.ERROR_MESSAGE_TYPE);
        String successContent = this.getMessage(CasebookConstants.SUCCESS_MESSAGE_TYPE);
        String viewContent = this.getView(template);
        String footerContent = this.getFooter();

        if (viewContent == null) {
            return super.notFound(ErrorMessages.NOT_FOUND_ERROR_MESSAGE.getBytes(), httpResponse);
        }

        String renderedErrorContent = this.limeLeaf.renderHtml(errorContent == null ? "" : errorContent);
        String renderedSuccessContent = this.limeLeaf.renderHtml(successContent == null ? "" : successContent);
        String renderedContent = this.limeLeaf.renderHtml(viewContent);

        this.limeLeaf.addAttributeToViewData(CasebookConstants.ERROR_MESSAGE_TYPE, "");

        baseViewContent = baseViewContent.replace("{{header}}", headerContent == null ? "" : headerContent);
        baseViewContent = baseViewContent.replace("{{error}}", renderedErrorContent == null ? "" : renderedErrorContent);
        baseViewContent = baseViewContent.replace("{{success}}", renderedSuccessContent == null ? "" : renderedSuccessContent);
        baseViewContent = baseViewContent.replace("{{view}}", renderedContent);
        baseViewContent = baseViewContent.replace("{{footer}}", footerContent == null ? "" : footerContent);

        httpResponse.addHeader("Content-Type", "text/html");

        return baseViewContent.getBytes();
    }

    public LimeLeaf getLimeLeaf() {
        return this.limeLeaf;
    }

    protected byte[] redirect(String location, HttpRequest httpRequest, HttpResponse httpResponse) {
        byte[] loginView = this.view(location, httpRequest, httpResponse);
        return redirect(loginView, "/" + location, httpResponse);
    }
}
