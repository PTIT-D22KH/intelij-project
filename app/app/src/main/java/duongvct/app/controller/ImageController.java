package duongvct.app.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/images")
public class ImageController {

    @GetMapping("/avatar/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Resource resource = new ClassPathResource("static/images/avatar/" + filename);
            if (resource.exists() && resource.isReadable()) {
                Path filePath = resource.getFile().toPath();
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, Files.probeContentType(filePath));
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    @PostMapping("/avatar")
//    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            // Define the path where the file will be saved
//            String uploadDir = new ClassPathResource("static/images/avatar/").getFile().getAbsolutePath();
//            File destinationFile = new File(uploadDir, file.getOriginalFilename());
//
//            // Save the file
//            file.transferTo(destinationFile);
//
//            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

}