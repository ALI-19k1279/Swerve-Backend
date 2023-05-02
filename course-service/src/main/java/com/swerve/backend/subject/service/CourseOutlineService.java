package com.swerve.backend.subject.service;

import com.swerve.backend.shared.exception.NotFoundException;
import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.CourseOutlineDTO;
import com.swerve.backend.subject.mapper.CourseOutlineMapper;
import com.swerve.backend.subject.model.OfferedCourse;
import com.swerve.backend.subject.model.OfferedCourseOutline;
import com.swerve.backend.subject.repository.OfferedCourseOutlineRepository;
import com.swerve.backend.subject.repository.OfferedCourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseOutlineService extends BaseService<OfferedCourseOutline, CourseOutlineDTO,Long> {
    private final OfferedCourseOutlineRepository offeredCourseOutlineRepository;
    private final CourseOutlineMapper courseOutlineMapper;
    private OfferedCourseRepository offeredCourseRepository;

    public CourseOutlineService(OfferedCourseOutlineRepository offeredCourseOutlineRepository,OfferedCourseRepository offeredCourseRepository, CourseOutlineMapper courseOutlineMapper) {
        super(offeredCourseOutlineRepository,courseOutlineMapper);
        this.offeredCourseOutlineRepository = offeredCourseOutlineRepository;
        this.courseOutlineMapper = courseOutlineMapper;
        this.offeredCourseRepository=offeredCourseRepository;
    }

    public CourseOutlineDTO getCourseOutlineByOfferedCourseId(Long offeredCourseId) {
        List<CourseOutlineDTO.WeeklyCourseOutline> weeklyCourseOutlines = new ArrayList<>();

        List<OfferedCourseOutline> offeredCourseOutlines = offeredCourseOutlineRepository.findByOfferedCourseId(offeredCourseId);
        for (OfferedCourseOutline offeredCourseOutline : offeredCourseOutlines) {
            List<String> topics = offeredCourseOutline.getTopics();
            topics.removeIf(Objects::isNull); // remove null values from the topics list
            int week = offeredCourseOutline.getWeek();
            CourseOutlineDTO.WeeklyCourseOutline weeklyCourseOutline = new CourseOutlineDTO.WeeklyCourseOutline(week, topics);
            weeklyCourseOutlines.add(weeklyCourseOutline);
        }

        CourseOutlineDTO courseOutlineDTO = new CourseOutlineDTO();
        courseOutlineDTO.setOfferedCourseId(offeredCourseId);
        courseOutlineDTO.setCourseOutline(weeklyCourseOutlines);

        return courseOutlineDTO;
    }

    public CourseOutlineDTO saveOrUpdateCourseOutline(Long offeredCourseId, Long teacherId, CourseOutlineDTO courseOutlineDTO) {
        OfferedCourse offeredCourse = offeredCourseRepository.findById(offeredCourseId)
                .orElseThrow(NotFoundException::new);

        List<CourseOutlineDTO.WeeklyCourseOutline> weeklyCourseOutlines = courseOutlineDTO.getCourseOutline();
        if (weeklyCourseOutlines != null) {
            for (CourseOutlineDTO.WeeklyCourseOutline weeklyOutline : weeklyCourseOutlines) {
                OfferedCourseOutline offeredCourseOutline = new OfferedCourseOutline();
                offeredCourseOutline.setTeacherId(teacherId);
                offeredCourseOutline.setWeek(weeklyOutline.getWeek());
                offeredCourseOutline.setOfferedCourse(offeredCourse);
                offeredCourseOutline.setTopics(weeklyOutline.getTopics());

                Optional<OfferedCourseOutline> existingOfferedCourseOutline = offeredCourseOutlineRepository.findByOfferedCourseIdAndWeek(offeredCourseId, offeredCourseOutline.getWeek());

                if (existingOfferedCourseOutline.isPresent()) {
                    OfferedCourseOutline existingOutline = existingOfferedCourseOutline.get();
                    existingOutline.setTopics(offeredCourseOutline.getTopics());
                    offeredCourseOutlineRepository.save(existingOutline);
                } else {
                    offeredCourseOutlineRepository.save(offeredCourseOutline);
                }
            }
        }

        return courseOutlineDTO;
    }







}
