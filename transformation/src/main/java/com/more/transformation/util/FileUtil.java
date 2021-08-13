package com.more.transformation.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author lzy
 * @date 2021/7/8 15:37
 */
@Slf4j
public class FileUtil extends FileUtils {


    /**
     * MultipartFile 图片转base64
     * @param file
     * @return
     * @throws Exception
     */
    public static String imgToBase64(MultipartFile file) throws Exception{
        String contentType = file.getContentType();
        BASE64Encoder base64Encoder =new BASE64Encoder();
        String base64EncoderImg = base64Encoder.encode(file.getBytes());
        base64EncoderImg = base64EncoderImg.replaceAll("[\\s*\t\n\r]", "");
        return base64EncoderImg;
    }

    /**
     * MultipartFile 转 File
     * @param multipartFile
     * @return
     */
    public static File transferToFile(MultipartFile multipartFile) {
        //选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String fileName = multipartFile.getOriginalFilename();
            assert fileName != null;
            String prefix = UUID.randomUUID().toString();
            String sufix = fileName.substring(fileName.lastIndexOf("."));
            file=File.createTempFile(prefix, sufix);
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("MultipartFile转File失败");
            e.printStackTrace();
        }
        return file;
    }
}
