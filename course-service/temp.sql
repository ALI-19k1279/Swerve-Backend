select * from course_module

select * from offered_course_evaluation;
select * from offered_course_evaluation_item;
select * from students_per_group_offered_course;
select * from offered_course_evaluation_item evi where evi.offered_course_evaluation_id in(
(select ev.id from offered_course oc,offered_course_evaluation ev where oc.id=ev.offered_course_id and oc.id in
(select offered_course_id from students_per_group_offered_course where student_id=134 ))
)
select evi from offered_course_evaluation ev,offered_course_evaluation_item evi where 
ev.id=evi.offered_course_evaluation_id and ev.studentid='134'
select * from course_material

Alter table offered_course_evaluation alter column studentid type bigint USING studentid::bigint