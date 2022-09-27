package org.xdl.reggie.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xdl.reggie.common.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * 文件上传下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        log.info(file.toString());
        // 原始文件名称
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        String fileName = UUID.randomUUID() + suffix;
        if (!new File(basePath).exists()) {
            // 如果目录不存在则创建
            new File(basePath).mkdir();
        }
        try {
            // 将临时文件转存到指定文件
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return R.success(fileName);
    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String name) {
        log.info("name:{}", name);
        try {
            // 输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            //输出流，通过输出流将文件写回浏览器，在浏览器中显示图片
            ServletOutputStream outputStream = response.getOutputStream();
            // 设置响应头
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[516];
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
