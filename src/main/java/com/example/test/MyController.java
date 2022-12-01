package com.example.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class MyController {

    private final NativeWebRequest nativeWebRequest;

    public MyController(NativeWebRequest nativeWebRequest) {
        this.nativeWebRequest = nativeWebRequest;
    }

    @PostMapping("/v2/files/upload-file/8c2da9b5-29b1-4393-8eda-982fe68c63ec")
    public String myMethod() {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        int i = -5;
        try {
            i = request.getInputStream().available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "End";
    }
}
