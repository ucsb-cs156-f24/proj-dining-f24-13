package edu.ucsb.cs156.dining.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class DiningMenuAPIService {
  @Value("${app.startDate:2024-01-01T00:00:00-08:00}")
  private OffsetDateTime startDate;

  @Value("${app.endDate:2024-12-31T23:59:59-08:00}")
  private OffsetDateTime endDate;

  @Autowired private ObjectMapper objectMapper;

  @Value("${app.ucsb.api.key}")
  private String apiKey;

  private RestTemplate restTemplate;

  public DiningMenuAPIService(RestTemplateBuilder restTemplateBuilder) throws Exception {
    this.restTemplate = restTemplateBuilder.build();
  }

  public static final String GET_DAYS =
      "https://api.ucsb.edu/dining/menu/v1/";

  public static final String GET_COMMONS =
      "https://api.ucsb.edu/dining/menu/v1/{date-time}";

  public static final String GET_MEALS =
      "https://api.ucsb.edu/dining/menu/v1/{date-time}/{dining-common-code}";

  public String getDays() throws Exception {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ucsb-api-version", "1.0");
    headers.set("ucsb-api-key", this.apiKey);

    HttpEntity<String> entity = new HttpEntity<>("body", headers);

    String url = GET_DAYS;
    log.info("url=" + url);

    String retVal = "";
    MediaType contentType = null;
    HttpStatus statusCode = null;

    ResponseEntity<String> re = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    contentType = re.getHeaders().getContentType();
    statusCode = (HttpStatus) re.getStatusCode();
    retVal = re.getBody();

    log.info("json: {} contentType: {} statusCode: {}", retVal, contentType, statusCode);
    return retVal;
  }

  public String getCommons(OffsetDateTime dateTime) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ucsb-api-version", "1.0");
    headers.set("ucsb-api-key", this.apiKey);

    HttpEntity<String> entity = new HttpEntity<>("body", headers);

    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    String formattedDateTime = dateTime.format(formatter);

    String url = GET_COMMONS
                .replace("{date-time}", formattedDateTime);

    log.info("url=" + url);

    String retVal = "";
    MediaType contentType = null;
    HttpStatus statusCode = null;

    ResponseEntity<String> re =
        restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    contentType = re.getHeaders().getContentType();
    statusCode = (HttpStatus) re.getStatusCode();
    retVal = re.getBody();

    if (retVal.equals("null"))
    {
      retVal = "{\"error\": \"Commons doesn't serve meals on given day.\"}";
    }

    log.info("json: {} contentType: {} statusCode: {}", retVal, contentType, statusCode);
    return retVal;
  } 

  public String getMeals(OffsetDateTime dateTime, String diningCommonCode) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("ucsb-api-version", "1.0");
    headers.set("ucsb-api-key", this.apiKey);

    HttpEntity<String> entity = new HttpEntity<>("body", headers);

    DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    String formattedDateTime = dateTime.format(formatter);

    String url = GET_MEALS
                .replace("{date-time}", formattedDateTime)
                .replace("{dining-common-code}", diningCommonCode);

    log.info("url=" + url);

    String retVal = "";
    MediaType contentType = null;
    HttpStatus statusCode = null;

    ResponseEntity<String> re =
        restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    contentType = re.getHeaders().getContentType();
    statusCode = (HttpStatus) re.getStatusCode();
    retVal = re.getBody();

    if (retVal.equals("null"))
    {
      retVal = "{\"error\": \"Meals are not served at given commons on given day.\"}";
    }

      log.info("json: {} contentType: {} statusCode: {}", retVal, contentType, statusCode);
      return retVal;
  } 
}