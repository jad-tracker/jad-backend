truncate table users restart identity cascade;
truncate table projects restart identity cascade;
truncate table project_members restart identity cascade;
truncate table issues restart identity cascade;
truncate table comments restart identity cascade;

insert into users(username, password)
values ('alex', '$2a$10$mn4uqw941tuQls3upBNH8ejtIWJipcdOAErOCNPjtvnDyWYJL8Jku'), -- password: alex
       ('mihai', '$2a$10$ALo2CNYA/KVv/0Sy3ig2xOahW/S7/r2SXlsAO1sfwZW1oNj1EQFlu'), -- password: mihai
       ('maria', '$2a$10$NiBd0CHMDpvxbPfZmZ7EBOl7qod1nR/fWUKABAQLr8FDmt44nBJ1O'), -- password: maria
       ('ioana', '$2a$10$jzZwR2GZZnmUP2HZoSZ1e.d2XE1koUcijYK8YSHEu.0ob3/fTqKWy'); -- password: ioana

INSERT INTO public.projects (id, id_lead, description, name) VALUES (2, 3, 'Our cloud storage offering', 'Contoso Cloud');
INSERT INTO public.projects (id, id_lead, description, name) VALUES (3, 1, 'Team messaging & calls', 'Congregate');
INSERT INTO public.projects (id, id_lead, description, name) VALUES (1, 1, 'Diff checking at scale', 'Conflate');


insert into project_members(id_user, id_project, role)
values (2, 1, 'Dev'),
       (4, 1, 'QA'),
       (1, 2, 'QA'),
       (2, 2, 'Dev');

INSERT INTO public.issues (date, id, id_assignee, id_project, description, status, summary, type) VALUES ('2025-01-15 23:09:00.000000', 5, 1, 2, e'FREE & OPEN SOURCE

# [SonarQube Community Build](https://www.sonarsource.com/open-source-editions/sonarqube-community-edition/ "Link")

SonarQube Community Build provides development teams with a workflow-integrated solution to keep their codebase in a state of Clean Code. SonarQube Community Build supports dozens of popular and classic programming languages, frameworks and cloud technologies.', 'DOING', 'Integrate SonarQube into project', 'TASK');
INSERT INTO public.issues (date, id, id_assignee, id_project, description, status, summary, type) VALUES ('2024-06-15 12:00:00.000000', 4, 2, 2, e'* Obtinere proiecte proprii ale unui utilizator (inclusiv proiectele in care e membru)
  * GET /api/projects
  * Response-ul contine o lista de proiecte cu id (number), name (string), description (string), lead (string - username-ul lead-ului) - cod 200 in caz de succes
* Creare proiect
  * POST /api/projects
  * Request-ul contine un obiect cu name, description
  * Response-ul un obiect cu structura de la GET-ul anterior - cod 201 in caz de succes
* Redenumire proiect
  * PUT /api/projects/{projectId}
  * Request-ul contine un obiect cu name
  * Response-ul un obiect cu structura de la GET-ul anterior - cod 200 in caz de succes
* Stergere proiect
  * DELETE /api/projects/{projectId}
  * Response - cod 204 in caz de succes
* Obtinere membri din proiect
  * GET /api/projects/{projectId}/members
  * Response-ul contine o lista de membri cu userId (number), username (string) si role (string)
* Adaugare membru la proiect
  * POST /api/projects/{projectId}/members
  * Request-ul contine un obiect cu username (string - username-ul userului adaugat), role (string)
  * Response un obiect cu structura de la GET-ul anterior - cod 201 in caz de succes
* Stergere membru de la proiect
  * DELETE /api/projects/{projectId}/members/{memberId}
  * Response - cod 204 in caz de succes', 'DONE', 'Implement Backend', 'STORY');
INSERT INTO public.issues (date, id, id_assignee, id_project, description, status, summary, type) VALUES ('2024-06-14 09:00:00.000000', 3, 1, 2, e'* [x] Reproduce the issue
* [x] Find the cause
* [ ] Fix for next release
* [ ] Backport to 3.15



The issue seems to be caused by the **WebDAV** URL being wrongly parsed. The `/dav` part gets confused with the `/david` representing the username.', 'DOING', '[Bug] Users named David can''t log in', 'BUG');
INSERT INTO public.issues (date, id, id_assignee, id_project, description, status, summary, type) VALUES ('2025-01-15 23:57:00.000000', 6, 3, 2, 'There''s not much to it, we just need to show our work since last time', 'TODO', 'Prepare slide deck for VP meeting', 'TASK');
INSERT INTO public.issues (date, id, id_assignee, id_project, description, status, summary, type) VALUES ('2025-01-16 00:25:00.000000', 7, 1, 3, e'Weirdly enough, this seems related to distance. Messages only start to disappear after \\~500 miles



[https://lowendbox.com/blog/we-cant-send-email-more-than-500-miles-a-true-story/](https://lowendbox.com/blog/we-cant-send-email-more-than-500-miles-a-true-story/)', 'DOING', 'Find out why some messages disappear', 'BUG');
INSERT INTO public.issues (date, id, id_assignee, id_project, description, status, summary, type) VALUES ('2024-06-12 18:00:00.000000', 1, 4, 1, e'Our CI pipeline takes *way too long* to run, and some developers (not to be named) have taken to avoiding testing altogether.

We need to improve things', 'TODO', 'DevEx - Better rigs for everyone', 'TASK');
INSERT INTO public.issues (date, id, id_assignee, id_project, description, status, summary, type) VALUES ('2024-06-13 13:00:00.000000', 2, 2, 1, '**Conflating** things is the opposite of <u>diff</u>erentiating them.', 'DOING', 'Find a better product name', 'STORY');

INSERT INTO public.comments (date, id, id_issue, id_user, content) VALUES ('2025-01-15 23:54:00.000000', 3, 5, 3, 'I''ll need an update on this');
INSERT INTO public.comments (date, id, id_issue, id_user, content) VALUES ('2025-01-16 00:01:00.000000', 4, 3, 3, 'This one''s rather unusual');
INSERT INTO public.comments (date, id, id_issue, id_user, content) VALUES ('2024-06-13 10:00:00.000000', 1, 1, 1, 'Seems good to me');
