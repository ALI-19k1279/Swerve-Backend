package com.swerve.backend.subject.service;


import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.AttendanceDTO;
import com.swerve.backend.subject.mapper.AttendanceMapper;
import com.swerve.backend.subject.mapper.StudentsPerGroup_OfferedCourseMapper;
import com.swerve.backend.subject.model.OfferedCourseAttendance;
import com.swerve.backend.subject.repository.AttendanceRepository;
import com.swerve.backend.subject.repository.StudentsPerGroup_OfferedCourseRepository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AttendanceService extends BaseService<OfferedCourseAttendance, AttendanceDTO,Long> {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    public AttendanceService(AttendanceRepository attendanceRepository,
                             AttendanceMapper attendanceMapper){
        super(attendanceRepository,attendanceMapper);
        this.attendanceRepository=attendanceRepository;
        this.attendanceMapper=attendanceMapper;
    }

    @Override
    public List<AttendanceDTO> findAll() {
        return super.findAll();
    }

    public List<AttendanceDTO> getLearnerOfferedCourseAttendance(Long oid,Long stdid){
        try {
            List<AttendanceDTO> attendanceDTOS = attendanceRepository.getLeanerCourseAttendanceDTOs(oid, stdid);
            System.out.println(attendanceDTOS.get(0).getDate());
            return attendanceDTOS.isEmpty() ? null : attendanceDTOS;
        }
        catch (Exception e){
            System.out.println(e.getCause());
            return null;
        }
    }
    public List<AttendanceDTO> getOfferedCourseGroupAttendance(Long gid,Long oid){
        try{
            List<OfferedCourseAttendance> attendanceList= attendanceRepository.findByGroupIdAndOfferedCourseId(gid,oid);
            return attendanceList.stream().map(attendance -> AttendanceMapper.INSTANCE.toDTOWithIds(attendance)).collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println(e.getCause());
            return null;
        }
    }
    public boolean markAttendance(List<AttendanceDTO> attendanceList){
        try {
            for (AttendanceDTO attendance : attendanceList) {
                OfferedCourseAttendance newAttendance = AttendanceMapper.INSTANCE.toModelWithIds(attendance);
                attendanceRepository.save(newAttendance);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    public List<AttendanceDTO> getOfferedCourseAttendances(Long gid,Long stid){
        try{
            List<OfferedCourseAttendance> attendanceList= attendanceRepository.findByGroupIdAndStudentId(gid,stid);
            return attendanceList.stream().map(attendance -> AttendanceMapper.INSTANCE.toDTOWithIds(attendance)).collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println(e.getCause());
            return null;
        }
    }
    public boolean updateAttendance(List<AttendanceDTO> attendanceList){
        try {
            for (AttendanceDTO attendance : attendanceList) {
                OfferedCourseAttendance newAttendance = AttendanceMapper.INSTANCE.toModelWithIds(attendance);
                System.out.println(newAttendance.getDate());
                attendanceRepository.updateAttendanceStatus(
                        newAttendance.getStudentId(),
                        newAttendance.getDate(),
                        attendance.getOfferedCourseId(),
                        attendance.getStatus()
                );
//                OfferedCourseAttendance checkAttedance=attendanceRepository.findByStudentIdAndDateAndOfferedCourseId(
//                        attendance.getStudentId(),newAttendance.getDate(),attendance.getOfferedCourseId());
//                if(!Objects.equals(checkAttedance.getStatus(), attendance.getStatus())){
//
//                   attendanceRepository.updateAttendanceStatus(
//                           attendance.getStudentId(),
//                           newAttendance.getDate(),
//                           attendance.getOfferedCourseId(),
//                           attendance.getStatus()
//                   );
//                }
//                else continue;
//                OfferedCourseAttendance newAttendance = AttendanceMapper.INSTANCE.toModelWithIds(attendance);
//                attendanceRepository.save(newAttendance);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
