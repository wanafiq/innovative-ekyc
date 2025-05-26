package com.wan.ekyc.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Slf4j
public class ImageUtil {
    private static final String IMAGE_PATH = "static/";

    public static String getFrontIdBase64() {
        String imgPath = String.format("%s%s", IMAGE_PATH, "front_id.jpg");

        byte[] bytes = loadFileResource(imgPath);

        return toBase64(bytes);
    }

    public static String getBackIdBase64() {
        String imgPath = String.format("%s%s", IMAGE_PATH, "back_id.jpg");

        byte[] bytes = loadFileResource(imgPath);

        return toBase64(bytes);
    }

    public static String getPassportBase64() {
        String imgPath = String.format("%s%s", IMAGE_PATH, "passport.jpg");

        byte[] bytes = loadFileResource(imgPath);

        return toBase64(bytes);
    }

    public static String getSelfieBase64() {
        String imgPath = String.format("%s%s", IMAGE_PATH, "selfie.jpg");

        byte[] bytes = loadFileResource(imgPath);

        return toBase64(bytes);
    }

    private static String toBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] loadFileResource(String filename) {
        try (InputStream is = ImageUtil.class.getClassLoader().getResourceAsStream(filename)) {
            if (is == null) {
                String err = String.format("Resource not found: %s", filename);
                log.error(err);
                throw new RuntimeException(err);
            }

            return IOUtils.toByteArray(is);
        } catch (IOException e) {
            String err = String.format("Failed to load resource %s from classpath. Error: %s", filename, e);
            log.error(err);
            throw new RuntimeException(err);
        }
    }
}
