package com.swerve.backend.subject.service;

import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.client.FacultyFeignClient;
import com.swerve.backend.subject.dto.CourseDTO;
import com.swerve.backend.subject.dto.CycleDTO;
import com.swerve.backend.subject.dto.OfferedCourseDTO;
import com.swerve.backend.subject.mapper.CourseMapper;
import com.swerve.backend.subject.mapper.CycleMapper;
import com.swerve.backend.subject.model.Course;
import com.swerve.backend.subject.model.Cycle;
import com.swerve.backend.subject.repository.CourseRepository;
import com.swerve.backend.subject.repository.CycleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CycleService extends BaseService<Cycle, CycleDTO,Long>{
    private final CycleRepository cycleRepository;
    private final CycleMapper cycleMapper;

    public CycleService(CycleMapper cycleMapper,CycleRepository cycleRepository){
        super(cycleRepository,cycleMapper);
        this.cycleMapper=cycleMapper;
        this.cycleRepository=cycleRepository;
    }
    public List<CycleDTO> GetCycleById(Long id){
        System.out.println("Service:: "+id);
        List<CycleDTO> cycleDTOS=cycleMapper.toDTO(
                cycleRepository.findByIdAndDeletedFalse(id)
        );
        System.out.println("cycle::"+cycleDTOS.get(0).getId());
        return cycleDTOS.isEmpty()?cycleDTOS:null;
    }
}
