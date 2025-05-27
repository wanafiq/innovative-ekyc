package com.wan.ekyc.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wan.ekyc.User;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class MockUtil {
    private static final String FilePath = "src/main/resources/static/user.json";

    private static final ObjectMapper mapper = new ObjectMapper();

    public static User createUser() {
        User user = User.builder()
                .id("01JA54YPBCDEZ18RHK4JYBKK58")
                .email("user@gmail.com")
                .password("$2a$10$vFgV5.ymn0Ye0q8u44ohHu3LNXMcgcRa/WmNiQkjqlShtMu4Xd9Sm")
                .fullName("user")
                .idNo("1234567890")
                .address1("address 1")
                .address2("address 2")
                .address3("address 3")
                .zipCode("zip code")
                .country("country")
                .status(UserStatus.PENDING_EKYC.name())
                .build();

        writeToFile(user);

        return user;
    }

    public static User getUser() {
        if (!FileUtil.exists(FilePath)) {
            log.error("File not found: {}", FilePath);
            throw new RuntimeException();
        }

        try {
            File file = new File(FilePath);
            return mapper.readValue(file, User.class);
        } catch (IOException e) {
            log.error("Failed to get user: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void updateUser(User user) {
        writeToFile(user);
    }

    private static void writeToFile(User user) {
        FileUtil.delete(FilePath);
        File file = FileUtil.create(FilePath);;

        try {
            mapper.writeValue(file, user);
        } catch (IOException e) {
            log.error("Failed to write user to file {}", FilePath, e);
            throw new RuntimeException();
        }
    }

}
