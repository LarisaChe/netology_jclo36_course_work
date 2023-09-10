package ru.netology;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.transfer.Amount;
import ru.netology.transfer.Code;
import ru.netology.transfer.Currency;
import ru.netology.transfer.TransferMoney;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class NetologyJclo36CourseWorkApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Container
    private static final GenericContainer<?> tmApp = new GenericContainer<>("transfermoney:latest")
            .withExposedPorts(5500);

    private  HttpHeaders headers = new HttpHeaders();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String transferUrl = "http://localhost:5500/transfer";
    private static final String confirmUrl = "http://localhost:5500/confirmOperation";

    private TransferMoney transfer = new TransferMoney(
            "1234432156788765",
            "02/28",
            "555",
            "9876678954322345",
            new Amount(1300, Currency.RUB));

    private Code code = new Code("9876");

    @Test
    public void test01() throws JsonProcessingException, JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<String>(objectMapper.writeValueAsString(transfer), headers);

        ResponseEntity<String> responseEntityStr = testRestTemplate.
                postForEntity(transferUrl, request, String.class);
        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());

        Assertions.assertEquals("1", root.toString());
    }

    @Test
    public void test02() throws JsonProcessingException, JSONException {
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<String>(objectMapper.writeValueAsString(code), headers);

        ResponseEntity<String> responseEntityStr = testRestTemplate.
                postForEntity(confirmUrl, request, String.class);
        JsonNode root = objectMapper.readTree(responseEntityStr.getBody());

        Assertions.assertEquals("1", root.toString());
    }

}
