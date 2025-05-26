package com.wan.ekyc.service;

import com.wan.ekyc.config.ApplicationConfig;
import com.wan.ekyc.dto.innovative.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;

@Slf4j
@Service
public class InnovativeService {

    private final ApplicationConfig config;
    private final RestClient client;

    public InnovativeService(ApplicationConfig config) {
        this.config = config;
        this.client = RestClient.builder()
                .baseUrl(config.getInnovative().getUrl())
                .build();
    }

    public CreateJourneyIdResponse CreateJourneyId() {
        CreateJourneyIdRequest request = CreateJourneyIdRequest.builder()
                .username(config.getInnovative().getUsername())
                .password(config.getInnovative().getPassword())
                .build();

        try {
            log.debug("Calling CreateJourneyId API. Request: {}", request);

            var response = client.post()
                    .uri("/api/ekyc/journeyid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(CreateJourneyIdResponse.class);

            log.debug("Received CreateJourneyId API response. Response: {}", response);

            return response;
        } catch (RestClientResponseException e) { // handle http error responses (4xx, 5xx)
            log.error("CreateJourneyId API call failed with HTTP status: {}.", e.getStatusCode());
        } catch (RestClientException e) { // handle client exceptions (network issues, timeouts, etc...)
            log.error("CreateJourneyId API call failed due to client exception. Error: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during CreateJourneyId API call. Error: {}", e.getMessage());
        }

        return null;
    }

    public OkIdResponse initOkId(String journeyId, String frontIdImage, String backIdImage, boolean idID) {
        OkIdRequest request = OkIdRequest.buildPassportRequest(
                config.getInnovative().getOkIdApiKey(),
                journeyId,
                frontIdImage);

        if (idID) {
            request = OkIdRequest.buildMyKadRequest(
                    config.getInnovative().getOkIdApiKey(),
                    journeyId,
                    frontIdImage,
                    backIdImage);
        }

        try {
            log.debug("Calling OkId API. Request: {}", request);

            var response = client.post()
                    .uri("/api/ekyc/okayid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(OkIdResponse.class);

            log.debug("Received OkId API response. Response: {}", response);

            return response;
        } catch (RestClientResponseException e) { // handle http error responses (4xx, 5xx)
            log.error("OkId API call failed with HTTP status: {} for journeyId: {}.",
                    e.getStatusCode(), journeyId);
        } catch (RestClientException e) { // handle client exceptions (network issues, timeouts, etc...)
            log.error("OkId API call failed due to client exception for journeyId: {}. Error: {}",
                    journeyId, e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during OkId API call for journeyId: {}. Error: {}",
                    journeyId, e.getMessage());
        }

        return null;
    }

    public OkDocResponse initOkDoc(String journeyId, String frontImage, boolean idID) {
        OkayDocRequest request = OkayDocRequest.buildPassportRequest(
                config.getInnovative().getOkIdApiKey(),
                journeyId,
                frontImage);

        if (idID) {
            request = OkayDocRequest.buildMyKadRequest(
                    config.getInnovative().getOkIdApiKey(),
                    journeyId,
                    frontImage);
        }

        try {
            log.debug("Calling OkDoc API. Request: {}", request);

            var response = client.post()
                    .uri("/api/ekyc/okaydoc")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(OkDocResponse.class);

            log.debug("Received OkDoc API response. Response: {}", response);

            return response;
        } catch (RestClientResponseException e) { // handle http error responses (4xx, 5xx)
            log.error("OkDoc API call failed with HTTP status: {} for journeyId: {}.",
                    e.getStatusCode(), journeyId);
        } catch (RestClientException e) { // handle client exceptions (network issues, timeouts, etc...)
            log.error("OkDoc API call failed due to client exception for journeyId: {}. Error: {}",
                    journeyId, e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during OkDoc API call for journeyId: {}. Error: {}",
                    journeyId, e.getMessage());
        }

        return null;
    }
}
