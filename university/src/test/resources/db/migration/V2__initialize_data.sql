-- Insert roles into the roles table
INSERT INTO roles (id, name)
VALUES (nextval('role_sequence'), 'ROLE_TEACHER'),
       (nextval('role_sequence'), 'ROLE_ADMIN'),
       (nextval('role_sequence'), 'ROLE_STUDENT');

-- Insert into specialities
INSERT INTO specialities (id, code, description, name)
VALUES (nextval('speciality_sequence'), 073, 'Management', 'Management'),
       (nextval('speciality_sequence'), 075, 'Marketing', 'Marketing'),
       (nextval('speciality_sequence'), 076, 'Entrepreneurship and Trade', 'Entrepreneurship and Trade'),
       (nextval('speciality_sequence'), 101, 'Ecology', 'Ecology'),
       (nextval('speciality_sequence'), 122, 'Computer Science', 'Computer Science'),
       (nextval('speciality_sequence'), 123, 'Computer Engineering', 'Computer Engineering'),
       (nextval('speciality_sequence'), 125, 'Cybersecurity and Information Protection',
        'Cybersecurity and Information Protection'),
       (nextval('speciality_sequence'), 131, 'Applied Mechanics', 'Applied Mechanics'),
       (nextval('speciality_sequence'), 132, 'Materials Science', 'Materials Science'),
       (nextval('speciality_sequence'), 201, 'Physics', 'Physics'),
       (nextval('speciality_sequence'), 202, 'Mathematics', 'Mathematics'),
       (nextval('speciality_sequence'), 203, 'Chemistry', 'Chemistry'),
       (nextval('speciality_sequence'), 204, 'Biology', 'Biology'),
       (nextval('speciality_sequence'), 205, 'Statistics', 'Statistics'),
       (nextval('speciality_sequence'), 206, 'Astronomy', 'Astronomy'),
       (nextval('speciality_sequence'), 207, 'Geology', 'Geology'),
       (nextval('speciality_sequence'), 208, 'Environmental Science', 'Environmental Science'),
       (nextval('speciality_sequence'), 209, 'Engineering Physics', 'Engineering Physics'),
       (nextval('speciality_sequence'), 210, 'Biomedical Engineering', 'Biomedical Engineering'),
       (nextval('speciality_sequence'), 211, 'Materials Engineering', 'Materials Engineering');

-- Insert into disciplines
INSERT INTO disciplines (id, description, name)
VALUES (nextval('discipline_sequence'), 'Study of the physical universe and its phenomena', 'Physics'),
       (nextval('discipline_sequence'), 'Study of matter, energy, and chemical reactions', 'Chemistry'),
       (nextval('discipline_sequence'), 'Study of living organisms and their interactions', 'Biology'),
       (nextval('discipline_sequence'), 'Study of mathematical theory and its applications', 'Mathematics'),
       (nextval('discipline_sequence'), 'Study of historical events and their impacts', 'History'),
       (nextval('discipline_sequence'), 'Study of human behavior and mental processes', 'Psychology'),
       (nextval('discipline_sequence'), 'Study of societal structures and relationships', 'Sociology'),
       (nextval('discipline_sequence'), 'Study of the principles of design and visual communication', 'Graphic Design'),
       (nextval('discipline_sequence'), 'Study of computer systems and software', 'Computer Science'),
       (nextval('discipline_sequence'), 'Study of human cultures and societies', 'Anthropology'),
       (nextval('discipline_sequence'), 'Study of financial systems and management', 'Economics'),
       (nextval('discipline_sequence'), 'Study of laws and legal systems', 'Law'),
       (nextval('discipline_sequence'), 'Study of environmental systems and conservation', 'Environmental Science'),
       (nextval('discipline_sequence'), 'Study of artistic expression through music', 'Music'),
       (nextval('discipline_sequence'), 'Study of political systems and governance', 'Political Science');


-- Insert into lesson_types
INSERT INTO lesson_types (id, name)
VALUES (nextval('lesson_type_sequence'), 'Lecture'),
       (nextval('lesson_type_sequence'), 'Seminar'),
       (nextval('lesson_type_sequence'), 'Laboratory'),
       (nextval('lesson_type_sequence'), 'Workshop');

-- Insert into users
INSERT INTO users (id, date_of_birth, email, first_name, last_name, phone_number)
VALUES (nextval('user_sequence'), '2000-01-15', 'john.doe@example.com', 'John', 'Doe', '0509843286'),
       (nextval('user_sequence'), '1999-12-05', 'jane.smith@example.com', 'Jane', 'Smith', '0669843286'),
       (nextval('user_sequence'), '2001-03-22', 'michael.jones@example.com', 'Michael', 'Jones', '0509143216'),
       (nextval('user_sequence'), '2000-07-10', 'emily.brown@example.com', 'Emily', 'Brown', '0669841111'),
       (nextval('user_sequence'), '1998-09-25', 'david.white@example.com', 'David', 'White', '0995434212'),
       (nextval('user_sequence'), '1997-11-30', 'sarah.wilson@example.com', 'Sarah', 'Wilson', '0992134212'),
       (nextval('user_sequence'), '1996-04-15', 'daniel.martin@example.com', 'Daniel', 'Martin', '0992234212'),
       (nextval('user_sequence'), '1995-06-28', 'olivia.jackson@example.com', 'Olivia', 'Jackson', '0992334212'),
       (nextval('user_sequence'), '1994-12-01', 'james.miller@example.com', 'James', 'Miller', '0509143222'),
       (nextval('user_sequence'), '1995-10-15', 'sophia.lee@example.com', 'Sophia', 'Lee', '0509143233'),
       (nextval('user_sequence'), '2002-01-20', 'william.harris@example.com', 'William', 'Harris', '0509145555'),
       (nextval('user_sequence'), '2001-05-16', 'isabella.clark@example.com', 'Isabella', 'Clark', '0509145532'),
       (nextval('user_sequence'), '2000-08-22', 'mason.walker@example.com', 'Mason', 'Walker', '0954321999'),
       (nextval('user_sequence'), '1999-02-12', 'mia.rodriguez@example.com', 'Mia', 'Rodriguez', '0954321732'),
       (nextval('user_sequence'), '1998-07-25', 'ethan.davis@example.com', 'Ethan', 'Davis', '0954321933'),
       (nextval('user_sequence'), '1997-11-04', 'ava.garcia@example.com', 'Ava', 'Garcia', '0954321211'),
       (nextval('user_sequence'), '1996-09-30', 'jacob.martinez@example.com', 'Jacob', 'Martinez', '0956531999'),
       (nextval('user_sequence'), '1995-03-15', 'charlotte.hernandez@example.com', 'Charlotte', 'Hernandez',
        '0954222999'),
       (nextval('user_sequence'), '1994-12-01', 'noah.lopez@example.com', 'Noah', 'Lopez', '0694321999'),
       (nextval('user_sequence'), '1993-10-12', 'amelia.anderson@example.com', 'Amelia', 'Anderson', '0692221999'),
       (nextval('user_sequence'), '2002-03-16', 'liam.thomas@example.com', 'Liam', 'Thomas', '0694325199'),
       (nextval('user_sequence'), '2001-01-24', 'harper.johnson@example.com', 'Harper', 'Johnson', '0694185999'),
       (nextval('user_sequence'), '2000-09-07', 'alexander.brown@example.com', 'Alexander', 'Brown', '0614241999'),
       (nextval('user_sequence'), '1999-04-12', 'ella.jones@example.com', 'Ella', 'Jones', '0694321119'),
       (nextval('user_sequence'), '1998-11-20', 'jack.white@example.com', 'Jack', 'White', '0694321111'),
       (nextval('user_sequence'), '1997-06-22', 'lily.green@example.com', 'Lily', 'Green', '0694641461'),
       (nextval('user_sequence'), '1996-08-05', 'benjamin.lee@example.com', 'Benjamin', 'Lee', '0694633461'),
       (nextval('user_sequence'), '1995-02-20', 'zoe.harris@example.com', 'Zoe', 'Harris', '0694633161'),
       (nextval('user_sequence'), '1994-12-18', 'lucas.carter@example.com', 'Lucas', 'Carter', '0694633311'),
       (nextval('user_sequence'), '1993-05-30', 'luna.walker@example.com', 'Luna', 'Walker', '2223334444'),
       (nextval('user_sequence'), '2002-07-25', 'elijah.miller@example.com', 'Elijah', 'Miller', '5556667778'),
       (nextval('user_sequence'), '2001-10-18', 'chloe.davis@example.com', 'Chloe', 'Davis', '8889990001'),
       (nextval('user_sequence'), '2000-03-28', 'logan.jackson@example.com', 'Logan', 'Jackson', '1112223334'),
       (nextval('user_sequence'), '1999-11-01', 'sophie.martinez@example.com', 'Sophie', 'Martinez', '4442556667'),
       (nextval('user_sequence'), '1998-01-15', 'james.carter@example.com', 'James', 'Carter', '7778880001'),
       (nextval('user_sequence'), '1997-12-30', 'arabella.rodriguez@example.com', 'Arabella', 'Rodriguez',
        '0001112223'),
       (nextval('user_sequence'), '1996-09-12', 'jackson.garcia@example.com', 'Jackson', 'Garcia', '3334445556'),
       (nextval('user_sequence'), '1995-11-05', 'grace.hall@example.com', 'Grace', 'Hall', '6667778889'),
       (nextval('user_sequence'), '1994-06-15', 'logan.hernandez@example.com', 'Logan', 'Hernandez', '9990001112'),
       (nextval('user_sequence'), '1993-04-22', 'natalie.lopez@example.com', 'Natalie', 'Lopez', '2223334442'),
       (nextval('user_sequence'), '2002-08-12', 'evelyn.taylor@example.com', 'Evelyn', 'Taylor', '5554443335'),
       (nextval('user_sequence'), '2001-11-19', 'noah.martin@example.com', 'Noah', 'Martin', '4445556669'),
       (nextval('user_sequence'), '2000-12-03', 'grace.wilson@example.com', 'Grace', 'Wilson', '3332221115'),
       (nextval('user_sequence'), '1999-07-22', 'logan.thompson@example.com', 'Logan', 'Thompson', '2221113335'),
       (nextval('user_sequence'), '1998-06-10', 'sophia.miller@example.com', 'Sophia', 'Miller', '1114445557'),
       (nextval('user_sequence'), '1997-05-20', 'jack.harris@example.com', 'Jack', 'Harris', '6665554446'),
       (nextval('user_sequence'), '1996-03-16', 'aiden.carter@example.com', 'Aiden', 'Carter', '5556667779'),
       (nextval('user_sequence'), '1995-04-10', 'olivia.green@example.com', 'Olivia', 'Green', '7778889991'),
       (nextval('user_sequence'), '1994-08-25', 'elijah.james@example.com', 'Elijah', 'James', '9990001114'),
       (nextval('user_sequence'), '1993-09-05', 'chloe.brown@example.com', 'Chloe', 'Brown', '2224445557'),
       (nextval('user_sequence'), '2002-05-28', 'mia.jackson@example.com', 'Mia', 'Jackson', '4446667779'),
       (nextval('user_sequence'), '2001-06-15', 'michael.smith@example.com', 'Michael', 'Smith', '3337778880'),
       (nextval('user_sequence'), '2000-04-07', 'ella.davis@example.com', 'Ella', 'Davis', '1116669992'),
       (nextval('user_sequence'), '1999-03-22', 'jacob.clark@example.com', 'Jacob', 'Clark', '2225557779'),
       (nextval('user_sequence'), '1998-12-18', 'amelia.wilson@example.com', 'Amelia', 'Wilson', '3334449992'),
       (nextval('user_sequence'), '1997-10-05', 'aiden.miller@example.com', 'Aiden', 'Miller', '4446661113'),
       (nextval('user_sequence'), '1996-07-11', 'michael.carter@example.com', 'Michael', 'Carter', '5557772224'),
       (nextval('user_sequence'), '1995-09-16', 'sofia.harris@example.com', 'Sofia', 'Harris', '6668883335'),
       (nextval('user_sequence'), '1994-10-03', 'james.green@example.com', 'James', 'Green', '7779994446'),
       (nextval('user_sequence'), '1993-06-25', 'chloe.lee@example.com', 'Chloe', 'Lee', '8880005557'),
       (nextval('user_sequence'), '2002-02-14', 'liam.martinez@example.com', 'Liam', 'Martinez', '9991116668'),
       (nextval('user_sequence'), '2001-07-19', 'arabella.davis@example.com', 'Arabella', 'Davis', '1112229992'),
       (nextval('user_sequence'), '2000-10-05', 'noah.johnson@example.com', 'Noah', 'Johnson', '2223337779'),
       (nextval('user_sequence'), '1999-11-30', 'ava.thomas@example.com', 'Ava', 'Thomas', '3334448890'),
       (nextval('user_sequence'), '1998-04-21', 'ethan.lee@example.com', 'Ethan', 'Lee', '4445559992'),
       (nextval('user_sequence'), '1997-12-01', 'mia.jones@example.com', 'Mia', 'Jones', '5556660002'),
       (nextval('user_sequence'), '1996-01-20', 'emily.davis@example.com', 'Emily', 'Davis', '6667771112'),
       (nextval('user_sequence'), '1995-08-25', 'michael.lee@example.com', 'Michael', 'Lee', '7778882223'),
       (nextval('user_sequence'), '1994-05-20', 'julia.white@example.com', 'Julia', 'White', '8889993334'),
       (nextval('user_sequence'), '1993-08-15', 'isaac.martin@example.com', 'Isaac', 'Martin', '9991114445'),
       (nextval('user_sequence'), '1991-08-15', 'maxdiablo@example.com', 'Max', 'Diablo', '9991112245'),
       (nextval('user_sequence'), '1999-01-22', 'alexwhite@example.com', 'Alex', 'White', '9991112266'),
       (nextval('user_sequence'), '1992-04-25', 'jackrasel@example.com', 'Jack', 'Rassel', '9991112125'),
       (nextval('user_sequence'), '1993-05-09', 'matthiew@example.com', 'Matthiew', 'Holland', '9991112111'),
       (nextval('user_sequence'), '1991-10-01', 'lucydarts@example.com', 'Lucy', 'Darts', '9991112555');

-- Insert into admins
INSERT INTO admins (id, user_id)
VALUES (nextval('admin_sequence'),1),
       (nextval('admin_sequence'),2);

-- Insert into groups
INSERT INTO groups (id, name, speciality_id)
VALUES (nextval('group_sequence'),'MANA-01', 1),
       (nextval('group_sequence'),'MANA-02', 1),
       (nextval('group_sequence'),'MARK-01', 2),
       (nextval('group_sequence'),'MARK-02', 2),
       (nextval('group_sequence'),'ECO-01', 4),
       (nextval('group_sequence'),'ECO-02', 4),
       (nextval('group_sequence'),'COMP-01', 5),
       (nextval('group_sequence'),'COMP-02', 5),
       (nextval('group_sequence'),'COMP-01', 6),
       (nextval('group_sequence'),'COMP-02', 6),
       (nextval('group_sequence'),'CYBE-01', 7),
       (nextval('group_sequence'),'CYBE-02', 7),
       (nextval('group_sequence'),'APPL-01', 8),
       (nextval('group_sequence'),'APPL-02', 8),
       (nextval('group_sequence'),'MATE-01', 9),
       (nextval('group_sequence'),'MATE-02', 9),
       (nextval('group_sequence'),'PHYS-01', 10),
       (nextval('group_sequence'),'PHYS-02', 10),
       (nextval('group_sequence'),'MATH-01', 11),
       (nextval('group_sequence'),'MATH-02', 11),
       (nextval('group_sequence'),'CHEM-01', 12),
       (nextval('group_sequence'),'CHEM-02', 12),
       (nextval('group_sequence'),'BIOL-01', 13),
       (nextval('group_sequence'),'BIOL-02', 13),
       (nextval('group_sequence'),'STAT-01', 14);


-- Insert into students
INSERT INTO students (id, group_id, user_id)
VALUES (nextval('student_sequence'),1, 3),
       (nextval('student_sequence'),2, 4),
       (nextval('student_sequence'),2, 5),
       (nextval('student_sequence'),2, 6),
       (nextval('student_sequence'),3, 7),
       (nextval('student_sequence'),3, 8),
       (nextval('student_sequence'),3, 9),
       (nextval('student_sequence'),4, 10),
       (nextval('student_sequence'),4, 11),
       (nextval('student_sequence'),4, 12),
       (nextval('student_sequence'),5, 13),
       (nextval('student_sequence'),5, 14),
       (nextval('student_sequence'),5, 15),
       (nextval('student_sequence'),6, 16),
       (nextval('student_sequence'),6, 17),
       (nextval('student_sequence'),6, 18),
       (nextval('student_sequence'),7, 19),
       (nextval('student_sequence'),7, 20),
       (nextval('student_sequence'),7, 21),
       (nextval('student_sequence'),8, 22),
       (nextval('student_sequence'),8, 23),
       (nextval('student_sequence'),8, 24),
       (nextval('student_sequence'),9, 25),
       (nextval('student_sequence'),9, 26),
       (nextval('student_sequence'),9, 27),
       (nextval('student_sequence'),10, 28),
       (nextval('student_sequence'),10, 29),
       (nextval('student_sequence'),10, 30),
       (nextval('student_sequence'),11, 31),
       (nextval('student_sequence'),11, 32),
       (nextval('student_sequence'),11, 33),
       (nextval('student_sequence'),12, 34),
       (nextval('student_sequence'),12, 35),
       (nextval('student_sequence'),12, 36),
       (nextval('student_sequence'),13, 37),
       (nextval('student_sequence'),13, 38),
       (nextval('student_sequence'),13, 39),
       (nextval('student_sequence'),14, 40),
       (nextval('student_sequence'),14, 41),
       (nextval('student_sequence'),14, 42),
       (nextval('student_sequence'),15, 43),
       (nextval('student_sequence'),15, 44),
       (nextval('student_sequence'),15, 45),
       (nextval('student_sequence'),16, 46),
       (nextval('student_sequence'),16, 47),
       (nextval('student_sequence'),16, 48),
       (nextval('student_sequence'),17, 49),
       (nextval('student_sequence'),17, 50);

-- Insert into teachers
INSERT INTO teachers (id, user_id)
VALUES (nextval('teacher_sequence'),51),
       (nextval('teacher_sequence'),52),
       (nextval('teacher_sequence'),53),
       (nextval('teacher_sequence'),54),
       (nextval('teacher_sequence'),55),
       (nextval('teacher_sequence'),56),
       (nextval('teacher_sequence'),57),
       (nextval('teacher_sequence'),58),
       (nextval('teacher_sequence'),59),
       (nextval('teacher_sequence'),60),
       (nextval('teacher_sequence'),61),
       (nextval('teacher_sequence'),62),
       (nextval('teacher_sequence'),63),
       (nextval('teacher_sequence'),64),
       (nextval('teacher_sequence'),65),
       (nextval('teacher_sequence'),66),
       (nextval('teacher_sequence'),67),
       (nextval('teacher_sequence'),68),
       (nextval('teacher_sequence'),69),
       (nextval('teacher_sequence'),70),
       (nextval('teacher_sequence'),71),
       (nextval('teacher_sequence'),72),
       (nextval('teacher_sequence'),73),
       (nextval('teacher_sequence'),74),
       (nextval('teacher_sequence'),75);


-- Insert into lessons
INSERT INTO lessons (id, classroom_number, date, end_time, start_time, discipline_id, lesson_type_id)
VALUES (nextval('lesson_sequence'),101, '2024-09-01', '10:00:00', '08:00:00', 1, 1),
       (nextval('lesson_sequence'),102, '2024-09-02', '12:00:00', '10:00:00', 2, 2),
       (nextval('lesson_sequence'),103, '2024-09-03', '14:00:00', '12:00:00', 3, 3),
       (nextval('lesson_sequence'),104, '2024-09-04', '16:00:00', '14:00:00', 4, 4),
       (nextval('lesson_sequence'),105, '2024-09-05', '08:00:00', '16:00:00', 1, 1),
       (nextval('lesson_sequence'),106, '2024-09-06', '10:00:00', '08:00:00', 2, 2),
       (nextval('lesson_sequence'),107, '2024-09-07', '12:00:00', '10:00:00', 3, 3),
       (nextval('lesson_sequence'),108, '2024-09-08', '14:00:00', '12:00:00', 4, 4);

-- Insert into group_lesson
INSERT INTO group_lesson (group_id, lesson_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6),
       (4, 7),
       (4, 8),
       (5, 1),
       (5, 2),
       (6, 3),
       (6, 4),
       (7, 5),
       (7, 6),
       (8, 7),
       (8, 8);

-- Insert into user_role
INSERT INTO user_role (user_id, role_id)
VALUES (1, 1),
       (2, 1);

-- Assign teachers to disciplines
INSERT INTO teacher_discipline (teacher_id, discipline_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 2),
       (5, 2),
       (6, 2),
       (7, 3),
       (8, 3),
       (9, 4),
       (10, 4),
       (11, 5),
       (12, 5),
       (13, 6),
       (14, 6),
       (15, 7),
       (16, 7),
       (17, 8),
       (18, 8),
       (19, 9),
       (20, 9),
       (21, 10),
       (22, 11),
       (23, 12),
       (24, 13),
       (25, 14),
       (24, 15);

