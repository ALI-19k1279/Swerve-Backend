package com.swerve.backend.subject.service;


import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.client.FacultyFeignClient;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.LearningTrackDTO;
import com.swerve.backend.subject.dto.OfferedCourseDTO;
import com.swerve.backend.subject.mapper.CourseMapper;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.LearningTrack;
import com.swerve.backend.subject.model.OfferedCourse;
import com.swerve.backend.subject.model.PreRequisite;
import com.swerve.backend.subject.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    public CourseDTO GetCourseById(Long id){
        System.out.println("Service : "+id);
        List<CourseDTO> courseDTOS=courseMapper.toDTO(
                courseRepository.findByIdAndDeletedFalse(id)
        );
        List<Course> courses=courseRepository.findByIdAndDeletedFalse(id);
        System.out.println(courses.get(0).getTitle());
        System.out.println("DTO::"+courseDTOS.get(0).getTitle());
        return courseDTOS.get(0);
    }
    public LearningTrack getLearningTrackbyCourseID(Long id){
        System.out.println("Service : "+id);
//        List<CourseDTO> courseDTOS=courseMapper.toDTO(
//               (list<Course>) courseRepository.findByIdAndDeletedFalse(id)
//        );
        List<Course> courses=courseRepository.findByIdAndDeletedFalse(id);
        System.out.println(courses.get(0).getTitle());
        System.out.println("DTO::"+courses.get(0).getLearningTrack());
//        List<CourseDTO> courseDTOS=courseMapper.toDTO(courses);
//        System.out.println(courseDTOS.get(0).getLearning_track_id());
        return courses.get(0).getLearningTrack();
    }
    public List<CourseDTO> GetAllCourses(){
        List<CourseDTO> courseDTOS=courseMapper.toDTO(
                courseRepository.findAll()
        );
        return courseDTOS.isEmpty()?null:courseDTOS;
    }
    public List<PreRequisite> getPreRequisitebyCourseID(long id){
        List<PreRequisite> preRequisites=courseRepository.findPreRequisiteByCourseID(id);
        System.out.println(preRequisites.get(0).getPreReqIs());
        return preRequisites.isEmpty()?null: preRequisites;

    }
    public List<OfferedCourse> GetAllOfferedCourses(){
        List<OfferedCourse> offeredCourses=courseRepository.GetAllOfferedCourses();
        return offeredCourses.isEmpty()?null:offeredCourses;
    }
}
