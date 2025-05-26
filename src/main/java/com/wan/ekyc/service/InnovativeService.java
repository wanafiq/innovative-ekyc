package com.wan.ekyc.service;

import com.wan.ekyc.config.ApplicationConfig;
import com.wan.ekyc.dto.innovative.*;
import com.wan.ekyc.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
        OkDocRequest request = OkDocRequest.buildPassportRequest(
                config.getInnovative().getOkIdApiKey(),
                journeyId,
                frontImage);

        if (idID) {
            request = OkDocRequest.buildMyKadRequest(
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

    public OkFaceResponse initOkFace(String journeyId, String frontImage, String selfieImage) {
        OkFaceRequest request = OkFaceRequest.builder()
                .apiKey(config.getInnovative().getOkFaceApiKey())
                .journeyId(journeyId)
                .imageIdCardBase64(frontImage)
                .imageBestBase64(selfieImage)
                .livenessDetection(true)
                .build();

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("apiKey", request.getApiKey());
        formData.add("journeyId", request.getJourneyId());
        formData.add("imageIdCardBase64", request.getImageIdCardBase64());
        formData.add("imageBestBase64", request.getImageBestBase64());
        formData.add("livenessDetection", true);

        try {
            log.debug("Calling OkFace API. Request: {}", request);

            var response = client.post()
                    .uri("/api/ekyc/okayface")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(formData)
                    .retrieve()
                    .body(OkFaceResponse.class);

            log.debug("Received OkFace API response. Response: {}", response);

            return response;
        } catch (RestClientResponseException e) { // handle http error responses (4xx, 5xx)
            log.error("OkFace API call failed with HTTP status: {} for journeyId: {}.",
                    e.getStatusCode(), journeyId);
        } catch (RestClientException e) { // handle client exceptions (network issues, timeouts, etc...)
            log.error("OkFace API call failed due to client exception for journeyId: {}. Error: {}",
                    journeyId, e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during OkFace API call for journeyId: {}. Error: {}",
                    journeyId, e.getMessage());
        }

        return null;
    }

    public OkLiveResponse initOkLive(String journeyId, String selfieImage) {
        var bytes = ImageUtil.toByteArrayResource(selfieImage);

        OkLiveRequest request = OkLiveRequest.builder()
                .apiKey(config.getInnovative().getOkFaceApiKey())
                .journeyId(journeyId)
                .imageBest(bytes)
                .build();

        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("apiKey", config.getInnovative().getOkFaceApiKey());
        formData.add("journeyId", journeyId);
        formData.add("imageBest", bytes);

        try {
            log.debug("Calling OkLive API. Request: {}", request);

            var response = client.post()
                    .uri("/api/ekyc/okaylive")
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(formData)
                    .retrieve()
                    .body(OkLiveResponse.class);

            log.debug("Received OkLive API response. Response: {}", response);

            return response;
        } catch (RestClientResponseException e) { // handle http error responses (4xx, 5xx)
            log.error("OkLive API call failed with HTTP status: {} for journeyId: {}.",
                    e.getStatusCode(), journeyId);
        } catch (RestClientException e) { // handle client exceptions (network issues, timeouts, etc...)
            log.error("OkLive API call failed due to client exception for journeyId: {}. Error: {}",
                    journeyId, e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error during OkLive API call for journeyId: {}. Error: {}",
                    journeyId, e.getMessage());
        }

        return null;
    }
}
