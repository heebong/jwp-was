package utils;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestUtilsTest {
    @Test
    void parseParamToMap() {
        String rawParams = "userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";

        Map<String, String> params = HttpRequestUtils.parseBodyParamToMap(rawParams);

        assertThat(params.get("userId")).isEqualTo("javajigi");
        assertThat(params.get("password")).isEqualTo("password");
        assertThat(params.get("name")).isEqualTo("%EB%B0%95%EC%9E%AC%EC%84%B1");
        assertThat(params.get("email")).isEqualTo("javajigi%40slipp.net");
    }

    @Test
    void parseQueryString_noQueryString_empty() {
        Map<String, String> params = HttpRequestUtils.parseQueryString(null);
        assertThat(params.size()).isEqualTo(0);

    }

    @Test
    void parseAbsPathAndQuery() {
        String rawUri = "/user/create?userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net";
        assertThat(HttpRequestUtils.parseAbsPathAndQuery(rawUri).length).isEqualTo(2);
        assertThat(HttpRequestUtils.parseAbsPathAndQuery(rawUri)[0]).isEqualTo("/user/create");
        assertThat(HttpRequestUtils.parseAbsPathAndQuery(rawUri)[1]).isEqualTo("userId=javajigi&password=password&name=%EB%B0%95%EC%9E%AC%EC%84%B1&email=javajigi%40slipp.net");
    }
}
