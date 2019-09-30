package webserver.servlet;

import org.junit.jupiter.api.Test;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.view.RedirectView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateServletTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void doPost() throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_post_test.txt"));
        HttpRequest httpRequest = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        HttpResponse httpResponse = new HttpResponse(null);
        UserCreateServlet userCreateServlet = new UserCreateServlet();
        assertThat(userCreateServlet.doPost(httpRequest, httpResponse)).isEqualTo(new RedirectView("/index.html"));
    }
}