package br.com.file.service.util;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;

public class Base64Util {

    public static String multipartFileToBase64String(MultipartFile file) {
        String base64Image = "";
        try {
            base64Image = Base64.encodeBase64String(IOUtils.toByteArray(file.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }
    
    /*public static byte[] base64ToByteArray(String data) {
        return java.util.Base64.getMimeDecoder().decode(data);
    }*/
    
    public static byte[] decodeToImage(String imageString) {

    	return Base64.decodeBase64(imageString);
    }

}
