package com.example.test;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.openapitools.api.RouteFpApi;
import ru.digitalapifiles.server.model.FileDownloadStateFPResponse;
import ru.digitalapifiles.server.model.FileUploadFPRequest;
import ru.digitalapifiles.server.model.UploadStateFpResponse;
import ru.digitalapifiles.server.model.UploadUrlFpResponse;

@Controller
public class MyClass implements RouteFpApi {

    private final NativeWebRequest nativeWebRequest;

    public MyClass(NativeWebRequest nativeWebRequest) {
        this.nativeWebRequest = nativeWebRequest;
    }

    @Override
    public ResponseEntity<Object> getDownloadFile(UUID fileId, String printPdf, String range, String ifRange) {
        return RouteFpApi.super.getDownloadFile(fileId, printPdf, range, ifRange);
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(nativeWebRequest);
    }

    @Override
    public ResponseEntity<UploadStateFpResponse> getUploadState(UUID fileId) {
        UploadStateFpResponse uploadStateFpResponse = new UploadStateFpResponse();
        uploadStateFpResponse.setBankComment("Vse putem");
        return new ResponseEntity<>(uploadStateFpResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> postDownload(Set<UUID> fileIds) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<FileDownloadStateFPResponse>> postDownloadState( Set<UUID> fileIds) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UploadUrlFpResponse> postUpload(FileUploadFPRequest fileUploadFPRequest) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> postUploadFile(UUID fileId) {
        Optional<NativeWebRequest> webRequestOpt = getRequest();
        if (webRequestOpt.isPresent()) {
            NativeWebRequest nativeWebRequest = webRequestOpt.get();
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            int i = -5;
            try {
                i = request.getInputStream().available();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ServletFileUpload upload = new ServletFileUpload();
            try {
                FileItemIterator iterStream = upload.getItemIterator(request);
                System.out.println(i);
                while (iterStream.hasNext()) {
                    FileItemStream item = iterStream.next();
                    String name = item.getFieldName();
                    if (!item.isFormField()) {
                        File tmpFile = File.createTempFile("download", "tmp");
                        try (InputStream uploadedStream = item.openStream();
                             FileOutputStream out = new FileOutputStream(tmpFile, true)) {
                            IOUtils.copy(uploadedStream, out);
                        }
                    }
                    else {
                        try (InputStream uploadedStream = item.openStream()) {
                            String formFieldValue = Streams.asString(uploadedStream);
                            System.out.println("Form field " + formFieldValue);
                        }
                    }
                }
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
            }

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

