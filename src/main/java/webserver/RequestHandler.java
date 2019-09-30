package webserver;

import exceptions.ErrorResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.handler.MappingHandler;
import webserver.parser.HttpRequestParser;
import webserver.request.HttpRequest;
import webserver.response.HttpResponse;
import webserver.servlet.HttpServlet;
import webserver.view.ErrorView;
import webserver.view.View;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class RequestHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        logger.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
            connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            generateResponse(in, out);
        } catch (IOException | URISyntaxException e) {
            logger.error(e.getMessage());
        }
    }

    private void generateResponse(InputStream in, OutputStream out) throws IOException, URISyntaxException {
        try {
            HttpRequest request = HttpRequestParser.parse(new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8)));
            HttpServlet httpServlet = MappingHandler.getServlets(request);
            HttpResponse response2 = new HttpResponse(new DataOutputStream(out));
            View view = httpServlet.run(request, response2);
            view.render(request, response2);
        } catch (ErrorResponseException e) {
            HttpResponse response2 = new HttpResponse(new DataOutputStream(out));
            View view = new ErrorView(e.getHttpStatus(), e.getMessage());
            view.render(null, response2);
        }
    }
}
