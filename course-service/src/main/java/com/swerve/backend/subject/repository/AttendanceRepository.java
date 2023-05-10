package com.swerve.backend.subject.repository;

import com.swerve.backend.shared.repository.BaseRepository;
import com.swerve.backend.subject.dto.AttendanceDTO;
import com.swerve.backend.subject.mapper.AttendanceMapper;
import com.swerve.backend.subject.model.OfferedCourseAttendance;
import com.swerve.backend.subject.model.StudentsPerGroup_OfferedCourse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface AttendanceRepository extends BaseRepository<OfferedCourseAttendance,Long> {


    @Query(value = "SELECT attendance FROM OfferedCourseAttendance attendance WHERE " +
            "attendance.studentId = :stdid " +
            "AND attendance.offeredCourse.id = :ocid")
    List<OfferedCourseAttendance> getLeanerCourseAttendance( Long ocid, Long stdid);

    default List<AttendanceDTO> getLeanerCourseAttendanceDTOs( Long ocid, Long stdid) {
        List<OfferedCourseAttendance> attendanceList = getLeanerCourseAttendance( ocid, stdid);
        return attendanceList.stream().map(attendance -> AttendanceMapper.INSTANCE.toDTOWithIds(attendance)).collect(Collectors.toList());
    }
    @Query("SELECT attendance FROM OfferedCourseAttendance attendance WHERE attendance.studentId = :studentId " +
            "AND attendance.group.id = :groupId AND attendance.offeredCourse.id = :offeredCourseId")
    List<OfferedCourseAttendance> findByStudentIdGroupIdAndOfferedCourseId(String studentId,
                                                                           Long groupId,
                                                                           Long offeredCourseId);


    OfferedCourseAttendance findByStudentIdAndDateAndOfferedCourseId(String studentId, Date date, Long offeredCourseId);

    List<OfferedCourseAttendance> findByGroupIdAndOfferedCourseId(Long groupId,Long offeredCourseId);

    List<OfferedCourseAttendance> findByGroupIdAndOfferedCourseIdAndDate(Long groupId,Long offeredCourseId, Date date);


    List<OfferedCourseAttendance> findByGroupIdAndStudentId(Long groupId,Long studentId);
    @Query(
            value = "update offered_course_attendance set status=:status where student_id=:studentId " +
                    "and oc_id=:offeredCourseId and date=:date",
            nativeQuery = true
    )
    void updateAttendanceStatus(Long studentId, Date date, Long offeredCourseId, String status);


}
