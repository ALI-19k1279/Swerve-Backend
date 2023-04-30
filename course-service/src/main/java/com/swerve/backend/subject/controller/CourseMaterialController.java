package com.swerve.backend.subject.controller;

import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.CourseMaterialDTO;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.CourseMaterial;
import com.swerve.backend.subject.service.CourseMaterialService;
import com.swerve.backend.subject.service.CourseService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/coursematerial")
public class CourseMaterialController extends BaseController<CourseMaterial, CourseMaterialDTO,Long> {

    private final CourseMaterialService courseMaterialService;

    public CourseMaterialController(CourseMaterialService courseMaterialService){
        super(courseMaterialService);
        this.courseMaterialService=courseMaterialService;
    }

//    @PostMapping("/fileSystem")
//    public ResponseEntity<?> uploadImageToFIleSystem(@RequestParam("image") MultipartFile file) throws IOException {
//        String uploadImage = courseMaterialService.uploadImageToFileSystem(file);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadImage);
//    }

    public String getFileExtension(String filename) {
        if(filename == null || filename.isEmpty()) {
            return "";
        }
        int dotIndex = filename.lastIndexOf(".");
        if(dotIndex == -1 || dotIndex == 0 || dotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(dotIndex + 1);
    }

    public MediaType getMediaTypeForFileName(String fileName) {
        String extension = getFileExtension(fileName);
        switch (extension.toLowerCase()) {
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "jpeg":
            case "jpg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "txt":
                return MediaType.TEXT_PLAIN;
            case "html":
                return MediaType.TEXT_HTML;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    @GetMapping("/fileSystem/{fileName}")
    public ResponseEntity<?> downloadFileFromFileSystem(@PathVariable String fileName) throws IOException {
        byte[] fileData = courseMaterialService.downloadFileFromFileSystem(fileName);
        CourseMaterial courseMaterialByName = courseMaterialService.getCourseMaterialByName(fileName);
        String name = courseMaterialByName.getName();
        String extension= getFileExtension(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(getMediaTypeForFileName(name))
                .body(fileData);
    }

    @GetMapping("/fileSystem/{teacherId}/{offeredCourseId}")
    public ResponseEntity<?> getCourseMaterialsByTeacherIdAndOfferedCourseId(@PathVariable Long teacherId,@PathVariable Long offeredCourseId) throws IOException {
        List<CourseMaterial> courseMaterials = courseMaterialService.getCourseMaterialByTeacherIDAndOfferedCourse(teacherId, offeredCourseId);
        return new ResponseEntity<>(courseMaterials, HttpStatus.OK);
    }

    @PostMapping("/fileSystem")
    public ResponseEntity<?> create(@RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        String description = (String) requestBody.get("description");
        String resourceUrl = (String) requestBody.get("resourceUrl");
        LocalDateTime publicationDate = LocalDateTime.parse((String) requestBody.get("publicationDate"));
        Long teacher = Long.parseLong((String) requestBody.get("teacher"));
        Long offeredCourse = Long.parseLong((String) requestBody.get("offeredCourse"));

        System.out.println(name);
        System.out.println(description);
        System.out.println(resourceUrl);
        System.out.println(publicationDate);
        System.out.println(teacher);
        System.out.println(offeredCourse);
//        CourseMaterialDTO courseMaterialDTO= new CourseMaterialDTO(name,description,
//                resourceUrl,publicationDate,teacher,offeredCourse);

//        CourseDTO course = courseService.save(courseDTO);
        courseMaterialService.createCourseMaterial(name,description,
                resourceUrl,publicationDate,teacher,offeredCourse);
//        if (course == null) {
//            throw new ServerException;
//        } else {
//            return new ResponseEntity<>(course, HttpStatus.CREATED);
//        }
        return new ResponseEntity<>( HttpStatus.CREATED);
    }
}
