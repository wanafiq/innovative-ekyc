package com.wan.ekyc.service;

import com.wan.ekyc.User;
import com.wan.ekyc.common.MockUtil;
import com.wan.ekyc.common.UserStatus;
import com.wan.ekyc.dto.ekyc.InitEkycRequest;
import com.wan.ekyc.dto.innovative.CreateJourneyIdResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class EkycService {
    private final InnovativeService innovativeService;

    public EkycService(InnovativeService innovativeService) {
        this.innovativeService = innovativeService;
    }

    public User initEkyc(InitEkycRequest req) {
        // In a real situation, this user will come from database. User already finished onboarding process
        // the current status should be PENDING_EKYC
        User user = MockUtil.createUser();

        if (!user.getStatus().equals(UserStatus.PENDING_EKYC.name())) {
            log.error("User {} has not finished onboarding process", user.getId());
            throw new RuntimeException();
        }

        // Each user will get journey id only once, at this point journey id should be null
        if (user.getJourneyId() != null) {
            log.error("User {} has already got ekyc journey id", user.getId());
            throw new RuntimeException();
        }

        CreateJourneyIdResponse response = innovativeService.createJourneyId();
        if (!response.getStatus().equals("success")) {
            log.error("Failed to create journey id: {}", response.getMessage());
            throw new RuntimeException();
        }

        user.setStatus(UserStatus.PENDING_EKYC_REVIEW.name());
        user.setJourneyId(response.getJourneyId());
        MockUtil.updateUser(user);

        return MockUtil.getUser();
    }

}
