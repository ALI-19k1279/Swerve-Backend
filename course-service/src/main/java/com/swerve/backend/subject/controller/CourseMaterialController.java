package com.swerve.backend.subject.controller;

import com.swerve.backend.shared.controller.BaseController;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.CourseMaterialDTO;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.CourseMaterial;
import com.swerve.backend.subject.service.CourseMaterialService;
import com.swerve.backend.subject.service.CourseService;
import com.swerve.backend.subject.util.Utility;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
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


    @PreAuthorize("hasAnyAuthority('viewCourse')")
    @GetMapping("/fileSystem/{id}")
    public ResponseEntity<?> downloadFileFromFileSystem(@PathVariable Long id) throws IOException {
        byte[] fileData = courseMaterialService.downloadFileFromFileSystem(id);
        CourseMaterial courseMaterialByName = courseMaterialService.getCourseMaterialByName(id);
        String name = courseMaterialByName.getName();
        String extension= Utility.getFileExtension(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(Utility.getMediaTypeForFileName(name))
                .body(fileData);
    }

    @PreAuthorize("hasAnyAuthority('viewCourse')")
    @GetMapping("/fileSystem/{teacherId}/{offeredCourseId}")
    public ResponseEntity<?> getCourseMaterialsByTeacherIdAndOfferedCourseId(@PathVariable Long teacherId,@PathVariable Long offeredCourseId) throws IOException {
        List<CourseMaterial> courseMaterials = courseMaterialService.getCourseMaterialByTeacherIDAndOfferedCourse(teacherId, offeredCourseId);
        return new ResponseEntity<>(courseMaterials, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('updateCourse')")
    @PostMapping("/filesystem")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file,
                                    @RequestParam("name") String name,
                                    @RequestParam("description") String description,
                                    @RequestParam("publicationDate") @DateTimeFormat(pattern = "yyyy-M-d") Date publicationDate,
                                    @RequestParam("teacher") String teacher,
                                    @RequestParam("offeredCourse") String offeredCourse
    ) throws IOException{
        // Handle file and other parameters here

        courseMaterialService.createCourseMaterial(file,name,description,publicationDate,Long.parseLong(teacher),Long.parseLong(offeredCourse));
        return new ResponseEntity<>( HttpStatus.CREATED);
    }

//    @PostMapping("/fileSystem")
//    public ResponseEntity<?> create(@RequestBody Map<String, Object> requestBody) {
//        String name = (String) requestBody.get("name");
//        String description = (String) requestBody.get("description");
//        String resourceUrl = (String) requestBody.get("resourceUrl");
//        LocalDateTime publicationDate = LocalDateTime.parse((String) requestBody.get("publicationDate"));
//        Long teacher = Long.parseLong((String) requestBody.get("teacher"));
//        Long offeredCourse = Long.parseLong((String) requestBody.get("offeredCourse"));
//
//        System.out.println(name);
//        System.out.println(description);
//        System.out.println(resourceUrl);
//        System.out.println(publicationDate);
//        System.out.println(teacher);
//        System.out.println(offeredCourse);
////        CourseMaterialDTO courseMaterialDTO= new CourseMaterialDTO(name,description,
////                resourceUrl,publicationDate,teacher,offeredCourse);
//
////        CourseDTO course = courseService.save(courseDTO);
//        courseMaterialService.createCourseMaterial(name,description,
//                resourceUrl,publicationDate,teacher,offeredCourse);
////        if (course == null) {
////            throw new ServerException;
////        } else {
////            return new ResponseEntity<>(course, HttpStatus.CREATED);
////        }
//        return new ResponseEntity<>( HttpStatus.CREATED);
//    }
}
