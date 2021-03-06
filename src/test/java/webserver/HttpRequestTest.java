package webserver;

import org.junit.jupiter.api.Test;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.request.RequestMethod;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

class HttpRequestTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    void getParameter_userInfo_true() throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_param_test.txt"));
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(request.getParam("userId")).isEqualTo("id");
        assertThat(request.getParam("password")).isEqualTo("password");
        assertThat(request.getParam("name")).isEqualTo("gyu");
    }

    @Test
    void getMethod_post_true() throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_post_test.txt"));
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(request.getMethod()).isEqualTo(RequestMethod.POST);
    }

    @Test
    void getBody_userId_true() throws IOException, URISyntaxException {
        InputStream inputStream = new FileInputStream(new File(testDirectory + "request_form_post_test.txt"));
        HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(inputStream)));
        assertThat(request.getBody("userId")).isEqualTo("javajigi");
        assertThat(request.getBody("password")).isEqualTo("password");
    }
}