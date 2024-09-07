
INSERT INTO "user"(
    id,
    login,
    name,
    password,
    registration_date,
    login_date,
    active_date,
    sex,
    birthdate,
    comment,
    country,
    city,
    roles,
    email)
VALUES (
    1,
    'Clarence',
    'John',
    'pass',
    TIMESTAMP WITH TIME ZONE '2013-01-01 00:00:00+04',
    TIMESTAMP WITH TIME ZONE '2013-07-25 00:00:00+04',
    TIMESTAMP WITH TIME ZONE '2013-07-25 00:00:00+04',
    'WOMAN',
    DATE '2000-11-11',
    'mycomment',
    'Russia',
    'Moscow',
    'USER',
    'just-some-email@localhost'
);



