package com.swerve.backend.subject.service;

import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.CourseMaterialDTO;
import com.swerve.backend.subject.mapper.CourseMapper;
import com.swerve.backend.subject.mapper.CourseMaterialMapper;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.CourseMaterial;
import com.swerve.backend.subject.repository.CourseMaterialRepository;
import com.swerve.backend.subject.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.swerve.backend.shared.security.SecurityUtils.*;

@Service
public class CourseMaterialService extends BaseService<CourseMaterial, CourseMaterialDTO,Long> {

    private final CourseMaterialRepository courseMaterialRepository;
    private final CourseMaterialMapper courseMaterialMapper;
//    private final FacultyFeignClient feignClient;

    public CourseMaterialService(CourseMaterialRepository courseMaterialRepository,CourseMaterialMapper courseMaterialMapper){
        super(courseMaterialRepository,courseMaterialMapper);
        this.courseMaterialRepository=courseMaterialRepository;
        this.courseMaterialMapper=courseMaterialMapper;
    }

    public void createCourseMaterial(String name, String description, String resourceUrl, LocalDateTime publicationDate,
                                     Long teacher, Long offeredCourse){
        courseMaterialRepository.insertCourseMaterial(name,description,
                resourceUrl,publicationDate,teacher,offeredCourse);
    }

//    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
//        String filePath=FOLDER_PATH+file.getOriginalFilename();
//
//        CourseMaterialDTO courseMaterialDTO=courseMaterialRepository.save(CourseMaterialDTO.builder()
//                .name(file.getOriginalFilename())
//                .type(file.getContentType())
//                .filePath(filePath).build());
//
//        file.transferTo(new File(filePath));
//
//        if (CourseMaterial != null) {
//            return "file uploaded successfully : " + filePath;
//        }
//        return null;
//    }

    public byte[] downloadFileFromFileSystem(Long id) throws IOException {
        Optional<CourseMaterial> courseMaterial = courseMaterialRepository.findById(id);
        String filePath=courseMaterial.get().getResourceUrl();
        byte[] file = Files.readAllBytes(new File(filePath).toPath());
        return file;
    }

    public CourseMaterial getCourseMaterialByName(Long id) throws IOException {
        Optional<CourseMaterial> courseMaterial = courseMaterialRepository.findById(id);
        return courseMaterial.get();
    }

    public List<CourseMaterial> getCourseMaterialByTeacherIDAndOfferedCourse(Long teacherId, Long offeredCourseId) throws IOException {
        List<CourseMaterial> courseMaterials = courseMaterialRepository.findByTeacherIdAndOfferedCourseId(teacherId, offeredCourseId);
        return courseMaterials;
    }

}
