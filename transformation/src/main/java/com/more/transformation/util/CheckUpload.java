package com.more.transformation.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
/**
 * obs oss 文件上传校验类
 * @author zhangjb
 */
public class CheckUpload {

    private static CheckUpload checkUpload = null;

    public static CheckUpload getInstance() {
        if (checkUpload == null) {
            checkUpload = new CheckUpload();
        }
        return checkUpload;
    }

    /**
     *
     * @param file 文件
     * @return ResultBean对象 装文件的临时路径 文件的名称
     */
    public ResultBean beforeCheck(MultipartFile file) {
        ResultBean rb = new ResultBean(false);
        //判断文件是否为空
        if (file.isEmpty()) {
            rb.setMsg("文件为空");
            return rb;
        }

        try {
            File receivedFile = File.createTempFile("http_received_", null);
            file.transferTo(receivedFile);
            log.debug("===> 保存到临时文件: {}", receivedFile.getAbsolutePath());
            Map<String, String> mapResult = new ConcurrentHashMap<>();
            mapResult.put("filePath", receivedFile.getAbsolutePath());
            mapResult.put("originalFilename", file.getOriginalFilename());
            rb.setResultFlag(true);
            rb.setResultMap(mapResult);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            rb.setMsg(e.getMessage());
        }
        return rb;
    }
}
