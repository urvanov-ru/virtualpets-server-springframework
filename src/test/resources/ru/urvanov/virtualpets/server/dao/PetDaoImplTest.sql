
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

INSERT INTO pet(
    id,
    name,
    created_date,
    login_date,
    satiety,
    mood,
    education,
    drink,
    comment,
    user_id,
    pet_type,
    hat_id,
    cloth_id,
    bow_id
)
VALUES (
    1,
    'Котик',
    TIMESTAMP WITH TIME ZONE '2014-01-24 20:28:38+04',
    NULL,
    0,
    0,
    0,
    0,
    'Просто комментарий',
    1,
    0,
    NULL,
    NULL,
    NULL
);


