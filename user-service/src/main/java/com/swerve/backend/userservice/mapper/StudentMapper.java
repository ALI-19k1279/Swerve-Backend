package com.swerve.backend.userservice.mapper;

import com.swerve.backend.userservice.client.SubjectFeignClient;
import com.swerve.backend.userservice.dto.StudentDTO;
import com.swerve.backend.userservice.model.Student;
import com.swerve.backend.shared.dto.UserDTO;
import com.swerve.backend.shared.mapper.BaseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class StudentMapper implements BaseMapper<Student, StudentDTO, Long> {
    private SubjectFeignClient subjectFeignClient;

    @Autowired
    public void setSubjectFeignClient(SubjectFeignClient subjectFeignClient) {
        this.subjectFeignClient = subjectFeignClient;
    }

    @Override
    @Named("default")
    @Mapping(source = "userId", target = "user")
    public abstract StudentDTO toDTO(Student student);

    @Override
    @Named("default")
    @Mapping(source = "user.id", target = "userId")
    public abstract Student toModel(StudentDTO studentDTO);

    public List<StudentDTO> toDTO(List<Student> students) {
        List<StudentDTO> list = students.stream().map(this::toDTO).toList();
        if (list.size() > 0) {
            List<Long> studentIds = list.stream().map(StudentDTO::getId).toList();
//            List<Double> averageGrades = subjectFeignClient.getAverageGradesByStudentId(studentIds);
//            List<Integer> totalECTS = subjectFeignClient.getTotalECTSByStudentId(studentIds);

//            for (int i = 0; i < list.size(); i++) {
//                list.get(i).setAverageGrade(averageGrades.get(i));
//                list.get(i).setTotalECTS(totalECTS.get(i));
//            }
        }

        return list;
    }

    public abstract UserDTO userDTOFromId(Long id);

//    @Named("getAverageGrade")
//    public Double getAverageGrade(Long studentId) {
//        return subjectFeignClient.getAverageGradesByStudentId(List.of(studentId)).get(0);
//    }
//
//    @Named("getTotalECTS")
//    public Integer getTotalECTS(Long studentId) {
//        return subjectFeignClient.getTotalECTSByStudentId(List.of(studentId)).get(0);
//    }
}
