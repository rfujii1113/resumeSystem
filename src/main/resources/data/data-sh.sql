BEGIN;

-- Employee data insert
INSERT INTO employee (
    emp_id,
    emp_name,
    password,
    gender,
    birth_date,
    current_address,
    permanent_address,
    last_school,
    major,
    graduation_date,
    nearest_station,
    department,
    delete_flag,
    permission
) VALUES
(1, 'John Doe', '$2a$10$wD/s8Qpd/Z1BC3LT.PHZ8Okhz1gzVY5EFPnMRlvJfZaBbldebz1rG', 'Male', '1985-04-12', '123 Maple Street, Seoul', '456 Oak Avenue, Songpa-gu, Seoul', 'Seoul National University', 'Computer Science', '2007-02-25', 'Gangnam Station', 'Development Team', 0, 1),
(2, 'Jane Smith', '$2a$10$39ib38s2GFk/s5kIqE/JvO.9A9KNC5mHgIK5J1RBoIidZPU.bbaOW', 'Female', '1990-08-30', '789 Pine Road, Busan', '101 Cedar Lane, Haeundae-gu, Busan', 'Pusan National University', 'Information and Communication Engineering', '2013-06-15', 'Haeundae Station', 'Marketing Team', 0, 1),
(3, 'Michael Brown', '$2a$10$Vf99IHZaWeRMeYfjAEyZUePaa5VfwtJYg76K7J/nvmdnDdiSNJZbu', 'Male', '1982-12-05', '234 Birch Boulevard, Daegu', '567 Spruce Street, Suseong-gu, Daegu', 'Kyungpook National University', 'Software Engineering', '2005-11-20', 'Suseong Station', 'HR Team', 0, 1),
(4, 'Emily Davis', '$2a$10$KIfsJOG3NRZ5yRcGpOwJ/eqwJqkHKirFKuQmB6dKDJvLr81yCpzti', 'Female', '1995-03-18', '345 Elm Street, Incheon', '678 Willow Avenue, Yeonsu-gu, Incheon', 'Inha University', 'Business Administration', '2017-05-10', 'Songdo Station', 'Finance Team', 0, 1),
(5, 'William Johnson', '$2a$10$V4HtoLRbHt5xKKtjdH.Ecu/s9Djgf1JX/7XlTpd/1ujECeolMqsHy', 'Male', '1988-07-22', '456 Ash Road, Gwangju', '789 Poplar Street, Dong-gu, Gwangju', 'Chonnam National University', 'Electrical Engineering', '2011-09-05', 'Geumnam Station', 'Development Team', 0, 1);

-- Skill data insert
INSERT INTO skill (
    skill_id,
    skill_name,
    skill_category
) VALUES
(1, 'Java', 'Programming Language'),
(2, 'Python', 'Programming Language'),
(3, 'Spring Boot', 'Framework'),
(4, 'React', 'Frontend Framework'),
(5, 'PostgreSQL', 'Database'),
(6, 'Docker', 'Tool'),
(7, 'Kubernetes', 'Tool'),
(8, 'AWS', 'Cloud Service'),
(9, 'Git', 'Version Control'),
(10, 'HTML/CSS', 'Web Technology');

-- ProjectHistory data insert
INSERT INTO project_history (
    project_id,
    project_name,
    start_date,
    end_date,
    work_location,
    emp_id
) VALUES
(1, 'E-Commerce Platform Development', '2020-01-15', '2020-12-20', 'Seoul', 1),
(2, 'Mobile Application Development', '2021-03-01', '2021-11-30', 'Busan', 2),
(3, 'Data Analysis Platform Setup', '2019-05-10', '2020-08-25', 'Daegu', 3),
(4, 'E-Commerce System Maintenance', '2020-02-20', '2021-04-15', 'Incheon', 1),
(5, 'Marketing Automation Tool Development', '2021-06-05', '2022-01-30', 'Gwangju', 2),
(6, 'HR Management System Development', '2018-09-15', '2019-12-20', 'Daegu', 3),
(7, 'Financial Management Application Development', '2022-02-10', '2022-10-25', 'Incheon', 4),
(8, 'Cloud Infrastructure Setup', '2020-07-01', '2021-03-15', 'Seoul', 5),
(9, 'Website Redesign', '2019-01-20', '2019-09-30', 'Gwangju', 5),
(10, 'CRM System Integration', '2021-11-05', '2022-05-20', 'Busan', 1),
(11, 'Internal Portal Development', '2020-04-10', '2020-12-15', 'Seoul', 4),
(12, 'Big Data Analysis Platform', '2019-07-20', '2020-03-30', 'Daegu', 3),
(13, 'Mobile Payment System Development', '2021-01-15', '2021-10-10', 'Incheon', 2),
(14, 'Electronic Document Management System', '2018-05-05', '2019-02-25', 'Gwangju', 5),
(15, 'AI-Based Recommendation System', '2022-03-01', '2022-12-31', 'Seoul', 1);

-- ProjectHistory data insert
INSERT INTO project_history_database (project_history_project_id, database) VALUES
(1, ARRAY['PostgreSQL']),
(2, ARRAY['MySQL']),
(3, ARRAY['PostgreSQL']),
(4, ARRAY['PostgreSQL']),
(5, ARRAY['PostgreSQL']),
(6, ARRAY['PostgreSQL']),
(7, ARRAY['PostgreSQL']),
(8, ARRAY['PostgreSQL']),
(9, ARRAY['PostgreSQL']),
(10, ARRAY['PostgreSQL']),
(11, ARRAY['PostgreSQL']),
(12, ARRAY['PostgreSQL']),
(13, ARRAY['PostgreSQL']),
(14, ARRAY['PostgreSQL']),
(15, ARRAY['PostgreSQL']);

INSERT INTO project_history_hardware (project_history_project_id, hardware) VALUES
(1, ARRAY['Server A', 'Router B']),
(2, ARRAY['Server C']),
(3, ARRAY['Server D', 'Storage E']),
(4, ARRAY['Server F']),
(5, ARRAY['Server G']),
(6, ARRAY['Server H']),
(7, ARRAY['Server I']),
(8, ARRAY['Server J']),
(9, ARRAY['Server K']),
(10, ARRAY['Server L']),
(11, ARRAY['Server M']),
(12, ARRAY['Server N']),
(13, ARRAY['Server O']),
(14, ARRAY['Server P']),
(15, ARRAY['Server Q']);

INSERT INTO project_history_language (project_history_project_id, language) VALUES
(1, ARRAY['Java', 'Spring Boot']),
(2, ARRAY['Kotlin', 'Swift']),
(3, ARRAY['Python']),
(4, ARRAY['Java']),
(5, ARRAY['Python', 'Django']),
(6, ARRAY['Java', 'Spring Boot']),
(7, ARRAY['C#', '.NET']),
(8, ARRAY['Python']),
(9, ARRAY['JavaScript', 'React']),
(10, ARRAY['Java', 'Spring Boot']),
(11, ARRAY['Java', 'Spring Boot']),
(12, ARRAY['Python', 'Scala']),
(13, ARRAY['Kotlin', 'Swift']),
(14, ARRAY['C#', '.NET']),
(15, ARRAY['Python', 'TensorFlow']);

INSERT INTO project_history_os (project_history_project_id, os) VALUES
(1, ARRAY['Linux', 'Windows']),
(2, ARRAY['iOS', 'Android']),
(3, ARRAY['Linux']),
(4, ARRAY['Windows']),
(5, ARRAY['Linux']),
(6, ARRAY['Linux']),
(7, ARRAY['Windows']),
(8, ARRAY['Linux']),
(9, ARRAY['Linux']),
(10, ARRAY['Windows']),
(11, ARRAY['Linux']),
(12, ARRAY['Linux']),
(13, ARRAY['iOS', 'Android']),
(14, ARRAY['Windows']),
(15, ARRAY['Linux']);

INSERT INTO project_history_tools (project_history_project_id, tools) VALUES
(1, ARRAY['Git', 'Docker']),
(2, ARRAY['Git']),
(3, ARRAY['Docker', 'Kubernetes']),
(4, ARRAY['Git']),
(5, ARRAY['Docker']),
(6, ARRAY['Git', 'Docker']),
(7, ARRAY['Git']),
(8, ARRAY['Docker', 'Kubernetes']),
(9, ARRAY['Git']),
(10, ARRAY['Git', 'Docker']),
(11, ARRAY['Git']),
(12, ARRAY['Hadoop', 'Spark']),
(13, ARRAY['Git']),
(14, ARRAY['Git']),
(15, ARRAY['Docker']);

INSERT INTO project_history_responsibility (project_history_project_id, responsibility) VALUES
(1, ARRAY['Backend Development', 'API Design']),
(2, ARRAY['Frontend Development']),
(3, ARRAY['Data Processing', 'Analysis']),
(4, ARRAY['System Maintenance']),
(5, ARRAY['Backend Development', 'Automation']),
(6, ARRAY['System Design', 'Development']),
(7, ARRAY['Frontend Development']),
(8, ARRAY['Infrastructure Management']),
(9, ARRAY['Frontend Development']),
(10, ARRAY['System Integration']),
(11, ARRAY['Backend Development']),
(12, ARRAY['Data Processing', 'Analysis']),
(13, ARRAY['Frontend Development']),
(14, ARRAY['System Development']),
(15, ARRAY['Machine Learning Model Development']);

-- EmployeeSkill data insert
INSERT INTO employee_skill (emp_id, skill_id) VALUES
-- John Doe (emp_id:1)
(1, 1), -- Java
(1, 3), -- Spring Boot
(1, 5), -- PostgreSQL
(1, 6), -- Docker
(1, 9), -- Git

-- Jane Smith (emp_id:2)
(2, 2), -- Python
(2, 4), -- React
(2, 5), -- PostgreSQL
(2, 8), -- AWS
(2, 10), -- HTML/CSS

-- Michael Brown (emp_id:3)
(3, 1), -- Java
(3, 3), -- Spring Boot
(3, 5), -- PostgreSQL
(3, 6), -- Docker
(3, 7), -- Kubernetes

-- Emily Davis (emp_id:4)
(4, 10), -- HTML/CSS
(4, 4), -- React
(4, 5), -- PostgreSQL
(4, 9), -- Git
(4, 8), -- AWS

-- William Johnson (emp_id:5)
(5, 1), -- Java
(5, 4), -- React
(5, 5), -- PostgreSQL
(5, 9), -- Git
(5, 10); -- HTML/CSS

COMMIT;
