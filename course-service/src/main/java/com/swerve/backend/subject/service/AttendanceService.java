package com.swerve.backend.subject.service;


import com.swerve.backend.shared.service.BaseService;
import com.swerve.backend.subject.dto.AttendanceDTO;
import com.swerve.backend.subject.mapper.AttendanceMapper;
import com.swerve.backend.subject.mapper.StudentsPerGroup_OfferedCourseMapper;
import com.swerve.backend.subject.model.OfferedCourseAttendance;
import com.swerve.backend.subject.model.StudentsPerGroup_OfferedCourse;
import com.swerve.backend.subject.repository.AttendanceRepository;
import com.swerve.backend.subject.repository.StudentsPerGroup_OfferedCourseRepository;
import org.springframework.stereotype.Service;

import java.awt.desktop.SystemEventListener;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.swerve.backend.subject.util.Utility.convertDateFormat;

@Service
public class AttendanceService extends BaseService<OfferedCourseAttendance, AttendanceDTO,Long> {
    private final AttendanceRepository attendanceRepository;
    private final AttendanceMapper attendanceMapper;
    private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);
    private final GroupService groupService;
    public AttendanceService(AttendanceRepository attendanceRepository,
                             AttendanceMapper attendanceMapper,GroupService groupService){
        super(attendanceRepository,attendanceMapper);
        this.attendanceRepository=attendanceRepository;
        this.attendanceMapper=attendanceMapper;
        this.groupService=groupService;
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
    public List<AttendanceDTO> getOfferedCourseGroupAttendanceByDate(Long gid,Long oid,Date date){
        try{
            List<OfferedCourseAttendance> attendanceList= attendanceRepository.findByGroupIdAndOfferedCourseIdAndDate(gid,oid,date);
            return attendanceList.stream().map(attendance -> AttendanceMapper.INSTANCE.toDTOWithIds(attendance)).collect(Collectors.toList());
        }
        catch (Exception e){
            System.out.println(e.getCause());
            return null;
        }
    }

    public List<AttendanceDTO> populateInstructorAttendanceTable(Long gid,Long oid,Date date){
        System.out.println(date);
        try{
            List<AttendanceDTO> offeredCourseGroupAttendanceByDate = getOfferedCourseGroupAttendanceByDate(gid, oid, date);
            if(offeredCourseGroupAttendanceByDate.size()==0){
                List<StudentsPerGroup_OfferedCourse> studentsPerGroupOfferedCourses = groupService.GetSPGOCByGroupIdAndOfferedCourseId(gid, oid);
                for(StudentsPerGroup_OfferedCourse spgoc : studentsPerGroupOfferedCourses){
                    AttendanceDTO attendanceDTO = new AttendanceDTO();
                    attendanceDTO.setStudentId(String.valueOf(spgoc.getStudentId()));
                    attendanceDTO.setGroupId(gid);
                    attendanceDTO.setDuration("1");
                    attendanceDTO.setOfferedCourseId(oid);
                    attendanceDTO.setDate(convertDateFormat(date));
                    attendanceDTO.setStatus("-");
                    offeredCourseGroupAttendanceByDate.add(attendanceDTO);
                }
            }
        return offeredCourseGroupAttendanceByDate;
        }
        catch (Exception e){
            System.out.println(e.getCause());
            return null;
        }
    }
    public boolean markAttendance(List<AttendanceDTO> attendanceList){
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
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
                OfferedCourseAttendance existingAttendance = attendanceRepository.findByStudentIdAndDateAndOfferedCourseId(
                        attendance.getStudentId(), newAttendance.getDate(), attendance.getOfferedCourseId());

                if (existingAttendance == null || !Objects.equals(existingAttendance.getStatus(), attendance.getStatus())) {
                    System.out.println(existingAttendance.getStatus());
                    System.out.println(attendance.getStatus());
                    java.sql.Date sqlDate = new java.sql.Date(newAttendance.getDate().getTime());
                    attendanceRepository.updateAttendanceStatus(
                            Long.valueOf(attendance.getStudentId()),
                            sqlDate,
                            attendance.getOfferedCourseId(),
                            attendance.getStatus()
                    );
                }
            }
            return true;
        } catch (Exception e) {
            logger.error("Failed to update attendance: {}", e.getMessage());
            return false;
        }
    }
}
