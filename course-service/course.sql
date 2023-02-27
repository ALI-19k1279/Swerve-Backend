select * from course
select * from learning_track
Insert into learning_track(deleted,description,name) values(false,123,'CS')
Insert into learning_track(deleted,description,name) values(false,124,'MG')
Insert into course(deleted,course_code,credits,short_description,title,learning_track_id) values(false,
																						 'CS123',3,'algorithm related course','DSA',2)
Insert into course(deleted,course_code,credits,short_description,title,learning_track_id) values(false,
																						 'CS124',3,'management fundamentals related course','CPM',3)	
Insert into course(deleted,course_code,credits,short_description,title,learning_track_id) values(false,
																						 'CS123',3,'data structures related course','DS',2)
select * from course_learner_progress
select * from course_module
select * from offered_course
select * from cycle
Insert into cycle(deleted,end_date,start_date) values(false,'2/24/2023','1/24/2023')
Insert into cycle(deleted,end_date,start_date) values(false,'2/24/2024','1/2/2023')
Insert into offered_course(deleted,fee,instructor_id,posts_and_announcements,courseid_id,cycle_id)values(false,24000,1279,'-',2,2)
Insert into offered_course(deleted,fee,instructor_id,posts_and_announcements,courseid_id,cycle_id)values(false,24000,0139,'-',3,1)
Insert into course_module(deleted,content,sequence_num,offered_course_id)values(false,111,2,1)
Insert into course_module(deleted,content,sequence_num,offered_course_id)values(false,112,2,3)		
Insert into course_learner_progress(deleted,is_done,studentid,course_module_id)values(false,false,0134,2)
Insert into course_learner_progress(deleted,is_done,studentid,course_module_id)values(false,false,0135,3)
select * from offered_course_evaluation
select * from offered_course_evaluation_item
Insert into offered_course_evaluation(deleted,code,marks_obtained,no_of_attempts,studentid,offered_course_id)values(false,'E12',10,0,0134,1)
Insert into offered_course_evaluation(deleted,code,marks_obtained,no_of_attempts,studentid,offered_course_id)values(false,'E13',10,0,0135,3)
Insert into offered_course_evaluation_item(deleted,can_reattempt,passing_marks,title,total_marks,offered_course_evaluation_id)values(false,true,10,'Quiz',12,1)
Insert into offered_course_evaluation_item(deleted,can_reattempt,passing_marks,title,total_marks,offered_course_evaluation_id)values(false,true,10,'Assignment',12,4)
select * from pre_requisite
Insert into pre_requisite(deleted,code,pre_req_for_id,pre_req_is_id)values(false,'PREQ1',2,4)
select * from session
Insert into session(deleted,mode,objectives,group_id,offered_course_id) values(false,'onsite',123,12,1)
select * from students_per_group_offered_course
Insert into students_per_group_offered_course(deleted,is_enrolled,student_id,group_id,offered_course_id)values(false,true,0134,12,1)
Insert into students_per_group_offered_course(deleted,is_enrolled,student_id,group_id,offered_course_id)values(false,true,0135,12,3)
