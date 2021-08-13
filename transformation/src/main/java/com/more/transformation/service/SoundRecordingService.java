package com.more.transformation.service;

import java.io.FileNotFoundException;

public interface SoundRecordingService {
    String saveFile(String storageType,String originalFilename,String filePath) throws FileNotFoundException;
}
