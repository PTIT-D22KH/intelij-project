package duongvct.app.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/file")
@CrossOrigin(origins = "http://localhost:5173")
public class FileController {
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("fileImg") MultipartFile file,
            @RequestHeader("upload-type") String targetFolder) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }

        try {
            // Define the path where the file will be saved
            String uploadDir = new File("src/main/resources/static/images/" + targetFolder).getAbsolutePath();
            File destinationFile = new File(uploadDir, file.getOriginalFilename());

            // Save the file
            file.transferTo(destinationFile);

            return new ResponseEntity<>(file.getOriginalFilename(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
