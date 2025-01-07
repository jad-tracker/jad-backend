insert into users(username, password)
values ('alex', ''), -- TODO: password
       ('mihai', ''),
       ('maria', ''),
       ('ioana','');

insert into projects(name, description, id_lead)
values ('Project 1', 'Description of Project 1', 1),
       ('Project 2', 'Description of Project 2', 3);

insert into project_members(id_user, id_project, role)
values (2, 1, 'Dev'),
       (4, 1, 'QA'),
       (1, 2, 'QA'),
       (2, 2, 'Dev');

insert into issues(summary, description, type, status, date, id_project, id_assignee)
values ('Issue 1', 'Description for Issue 1', 'BUG', 'TODO', '2024-06-12 18:00', 1, 4),
       ('Issue 2', 'Description for Issue 2', 'STORY', 'DOING', '2024-06-13 13:00', 1, 2),
       ('Issue 3', 'Description for Issue 3', 'BUG', 'TODO', '2024-06-14 09:00', 2, 1),
       ('Issue 4', 'Description for Issue 4', 'TASK', 'DONE', '2024-06-15 12:00', 2, 2);

insert into comments(content, date, id_user, id_issue)
values ('Comment 1', '2024-06-13 10:00', 1, 1),
       ('Comment 2', '2024-06-16 11:00', 3, 4);
