package com.wan.ekyc.common;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class FileUtil {

    public static boolean exists(String path) {
        File file = new File(path);
        return file.isFile();
    }

    public static File create(String path) {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                log.debug("File created {}", file.getAbsolutePath());
            } else {
                log.debug("File already exists");
            }
            return file;
        } catch (IOException e) {
            log.error("Failed to create file: {}", e.getMessage());
            throw new RuntimeException();
        }
    }

    public static void delete(String path) {
        File file = new File(path);
        file.delete();
    }
}
