package com.wan.ekyc.service;

import com.wan.ekyc.config.ApplicationConfig;
import com.wan.ekyc.dto.CreateJourneyIdRequest;
import com.wan.ekyc.dto.innovative.CreateJourneyIdResponse;
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
            return client.post()
                    .uri("/api/ekyc/journeyidid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(CreateJourneyIdResponse.class);
        } catch (RestClientResponseException e) { // handle http error responses (4xx, 5xx)
            log.error("CreateJourneyId API call failed with HTTP status: {}.", e.getStatusCode());
        } catch (RestClientException e) { // handle client exceptions (network issues, timeouts, etc...)
            log.error("CreateJourneyId API call failed due to client exception. Error: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during CreateJourneyId API call. Error: {}", e.getMessage());
        }

        return null;
    }
}
