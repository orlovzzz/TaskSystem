INSERT INTO projects(id, name, description, owner)
    VALUES (1000002, 'test', 'test', 'test'),
           (1000003, 'test', 'test', 'test'),
           (1000004, 'test', 'test', 'test'),
           (1000005, 'Project', 'Description', 'test');
INSERT INTO projects_users(login, project_id) VALUES ('test10', 1000004),
                                                     ('test11', 1000004),
                                                     ('test12', 1000004),
                                                     ('test', 1000002),
                                                     ('test0', 1000002);