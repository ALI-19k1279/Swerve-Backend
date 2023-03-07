package com.swerve.backend.subject.service;


import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.client.FacultyFeignClient;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.OfferedCourseDTO;
import com.swerve.backend.subject.mapper.CourseMapper;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService extends BaseService<Course,CourseDTO,Long> {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
//    private final FacultyFeignClient feignClient;

    public CourseService(CourseRepository courseRepository,CourseMapper courseMapper){
        super(courseRepository,courseMapper);
        this.courseRepository=courseRepository;
        this.courseMapper=courseMapper;
    }
    public List<CourseDTO> GetCourseById(Long id){
        System.out.println("Service"+id);
        List<CourseDTO> courseDTOS=courseMapper.toDTO(
                courseRepository.findByIdAndDeletedFalse(id)
        );
        return courseDTOS.isEmpty()?courseDTOS:null;
    }
    public List<CourseDTO> GetAllCourses(){
        List<CourseDTO> courseDTOS=courseMapper.toDTO(
                courseRepository.findAll()
        );
        return courseDTOS.isEmpty()?courseDTOS:null;
    }
}
