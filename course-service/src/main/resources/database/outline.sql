SELECT id, deleted, teacher_id, week, offered_course_id
	FROM public.offered_course_outline;
delete from offered_course_outline where id >6
	
select * from offered_course
select * from course

INSERT INTO offered_course_outline (week, teacher_id, offered_course_id) VALUES
(1, 1001, 1),
(2, 1002, 1);

INSERT INTO outline_topics (outline_id, sequence_number, topics) VALUES
(5, 0, 'Unit Testing'),
(5, 1, 'Stress Testing'),
(6, 0, 'Java'),
(6, 1, 'Sql');