

CREATE SCHEMA IF NOT EXISTS virtualpets_springframework;-- CHARACTER SET=utf8 COLLATE=utf8_bin;

SET search_path TO virtualpets_springframework;


CREATE TABLE chat (
                      id serial NOT NULL,
                      addressee int DEFAULT NULL,
                      sender int NOT NULL,
                      send_time timestamp with time zone NOT NULL,
                      message varchar(250) DEFAULT NULL,
                      version int NOT NULL DEFAULT 0,
                      PRIMARY KEY (id)
);


create index idx_chat_addressee on chat(addressee);

create index idx_chat_sender on chat(sender);

create index idx_chat_send_time on chat(send_time);

CREATE TABLE pet (
                     id serial NOT NULL,
                     name varchar(50) NOT NULL,
                     session_key varchar(50) DEFAULT NULL,
                     created_date timestamp with time zone NOT NULL,
                     login_date timestamp with time zone DEFAULT NULL,
                     satiety INT NOT NULL DEFAULT 0,
                     mood INT NOT NULL DEFAULT 0,
                     education INT NOT NULL DEFAULT 0,
                     drink INT NOT NULL DEFAULT 0,
                     comment varchar(50) DEFAULT NULL,
                     user_id INT NOT NULL,
                     pet_type INT NOT NULL DEFAULT 0,
                     hat_id varchar(50) DEFAULT NULL,
                     cloth_id varchar(50) DEFAULT NULL,
                     bow_id varchar(50) DEFAULT NULL,
                     experience INT NOT NULL DEFAULT 0,
                     level_id INT NOT NULL DEFAULT 1,
                     eat_count INT NOT NULL default 0,
                     drink_count INT NOT NULL default 0,
                     teach_count INT NOT NULL default 0,
                     build_count INT NOT NULL default 0,
                     hidden_objects_game_count INT default 0,
                     every_day_login_last timestamp with time zone NULL,
                     every_day_login_count INT NOT NULL DEFAULT 0,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY (id)
);

create unique index idx_pet_session_key on pet(session_key);

create index idx_pet_user_id on pet(user_id);



CREATE TABLE "user" (
                     id serial NOT NULL,
                     login varchar(50) DEFAULT NULL,
                     name varchar(50) NOT NULL,
                     password varchar(100) NULL,
                     registration_date timestamp with time zone NOT NULL,
                     login_date timestamp with time zone DEFAULT NULL,
                     active_date timestamp with time zone DEFAULT NULL,
                     sex varchar(5) DEFAULT NULL,
                     birthdate date DEFAULT NULL,
                     comment varchar(50) DEFAULT NULL,
                     country varchar(50) DEFAULT NULL,
                     city varchar(50) DEFAULT NULL,
                     role varchar(50) NOT NULL DEFAULT '0',
                     email varchar(100) DEFAULT NULL,
                     recover_password_key varchar(50) DEFAULT NULL,
                     recover_password_valid timestamp with time zone DEFAULT NULL,
                     unid varchar(1000) DEFAULT NULL,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY (id)
);

create unique index idx_user_login on "user"(login);

ALTER TABLE "user"  ADD CONSTRAINT chk_user_sex CHECK (sex = 'MAN' OR sex = 'WOMAN');

INSERT INTO "user"(login, name,password,registration_date,role)
values('admin', 'admin','$2a$10$JT0l8oNHQuohL8SMLHCBludsjTiJNpG.uDHc3QGkP5V.aMMLSEa7G',now(),'USER');


INSERT INTO pet(name,created_date,login_date,user_id,pet_type)
select 'admin',now(),now(),t_user.id,0
from "user" t_user where t_user.name='admin';


CREATE TABLE pet_cloth (
                           id serial NOT NULL,
                           pet_id INT NOT NULL,
                           cloth_id varchar(50) NOT NULL,
                           version INT NOT NULL DEFAULT 0,
                           PRIMARY KEY(id)
);

create unique index idx_cloth_pet_id_food_type on pet_cloth(pet_id, cloth_id);

CREATE TABLE cloth (
                       id varchar(50) NOT NULL,
                       cloth_type varchar(50) NOT NULL,
                       wardrobe_order INT NOT NULL,
                       hidden_objects_game_drop_rate REAL NOT NULL,
                       PRIMARY KEY(id)
);

ALTER TABLE cloth ADD CONSTRAINT chk_cloth_type CHECK (cloth_type IN ('HAT', 'CLOTH', 'BOW'));

INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('RED_HAT', 'HAT', 0, 0.1);
INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('COWBOY_HAT', 'HAT', 1, 0.1);
INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('TIARA', 'HAT', 2, 0.1);
INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('COLORED_BODY', 'CLOTH', 0, 0.1);
INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('SUIT_JACKET', 'CLOTH', 1, 0.1);
INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('PINKY_WINGS', 'CLOTH', 2, 0.1);
INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('RED_BOW', 'BOW', 0, 0.1);
INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('BLUE_BOW', 'BOW', 1, 0.1);
INSERT INTO cloth(id, cloth_type, wardrobe_order, hidden_objects_game_drop_rate)
VALUES('BLUE_FLOWER', 'BOW', 2, 0.1);


 create table UserConnection (userId varchar(255) not null,
     providerId varchar(255) not null,
     providerUserId varchar(255),
     rank int not null,
     displayName varchar(255),
     profileUrl varchar(512),
     imageUrl varchar(512),
     accessToken varchar(255) not null,
     secret varchar(255),
     refreshToken varchar(255),
     expireTime bigint,
     primary key (userId, providerId, providerUserId));

create unique index UserConnectionRank on UserConnection(userId, providerId, rank);



alter table chat add constraint fk_chat_addressee
    foreign key (addressee) references "user"(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table chat add constraint fk_chat_sender
    foreign key (sender) references "user"(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION;


alter table pet add constraint fk_pet_user_id
    foreign key (user_id) references "user"(id)
    ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet add constraint fk_pet_hat_id
    foreign key (hat_id) references cloth(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet add constraint fk_pet_cloth_id
    foreign key (cloth_id) references cloth(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet add constraint fk_pet_bow_id
    foreign key (bow_id) references cloth(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;


alter table pet_cloth add constraint fk_pet_cloth_pet_id
    foreign key (pet_id) references pet(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet_cloth add constraint fk_pet_cloth_cloth_id
    foreign key (cloth_id) references cloth(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;


-- 2014-01-17 для версии 0.20


create table refrigerator(
                             id INT NOT NULL,
                             experience INT NOT NULL,
                             PRIMARY KEY (id)
);

insert into refrigerator(id, experience) values(1, 1);
insert into refrigerator(id, experience) values(2, 3);
insert into refrigerator(id, experience) values(3, 8);
insert into refrigerator(id, experience) values(4, 11);
insert into refrigerator(id, experience) values(5, 14);
insert into refrigerator(id, experience) values(6, 17);

-- 2014-01-18
create table building_material(
                                  id varchar(50) NOT NULL,
                                  rucksack_order INT NOT NULL,
                                  newbie_box_drop_min INT NOT NULL,
                                  newbie_box_drop_max INT NOT NULL,
                                  newbie_box_drop_rate REAL NOT NULL,
                                  hidden_objects_game_drop_rate REAL NOT NULL,
                                  PRIMARY KEY (id)
);

create table pet_building_material(
                                      id serial NOT NULL,
                                      pet_id INT NOT NULL,
                                      building_material_id varchar(50) NOT NULL,
                                      building_material_count INT NOT NULL,
                                      version INT NOT NULL DEFAULT 0,
                                      PRIMARY KEY (id)
);

create unique index idx_pet_building_material_unique on pet_building_material(pet_id, building_material_id);

create index idx_pet_building_materials_pet_id on pet_building_material(pet_id);


alter table pet_building_material add constraint fk_pet_building_material_pet_id
    foreign key (pet_id) references pet(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet_building_material add constraint fk_pet_building_material_building_material_id
    foreign key (building_material_id) references building_material(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;


insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('TIMBER', 0, 1, 3, 0.6, 0.3);
insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('BOARD', 1, 1, 3, 0.3, 0.3);
insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('STONE', 2, 0, 0, 0.0, 0.3);
insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('CHIP', 3, 0, 0, 0.0, 0.1);
insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('WIRE', 4, 0, 0, 0.0, 0.1);
insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('IRON', 5, 0, 0, 0.0, 0.1);
insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('OIL', 6, 0, 0, 0.0, 0.1);
insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('BLUE_CRYSTAL', 7, 0, 0, 0.0, 0.1);
insert into building_material(id, rucksack_order, newbie_box_drop_min, newbie_box_drop_max, newbie_box_drop_rate, hidden_objects_game_drop_rate)
values('RUBBER', 8, 0, 0, 0.0, 0.1);

-- 2014-01-19

create table pet_food(
                         id serial NOT NULL,
                         pet_id INT NOT NULL,
                         food_id varchar(50) NOT NULL,
                         food_count INT NOT NULL,
                         version INT NOT NULL DEFAULT 0 ,
                         PRIMARY KEY(id)
);

create table food(
                     id varchar(50) NOT NULL,
                     refrigerator_id INT NOT NULL,
                     refrigerator_order INT NOT NULL,
                     hidden_objects_game_drop_rate REAL NOT NULL,
                     PRIMARY KEY(id)
);
alter table food add constraint fk_food_refrigerator_id foreign key (refrigerator_id)
    references refrigerator(id) on update no action on delete no action;


create unique index idx_pet_food_unique on pet_food(pet_id, food_id);

alter table pet_food add constraint fk_pet_food_pet_id
    foreign key (pet_id) references pet(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table pet_food add constraint fk_pet_food_food_id
    foreign key (food_id) references food(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

create table bookcase(
                         id INT NOT NULL,
                         experience INT NOT NULL,
                         PRIMARY KEY(id)
);

insert into bookcase(id, experience) values(1, 1);
insert into bookcase(id, experience) values(2, 3);
insert into bookcase(id, experience) values(3, 7);
insert into bookcase(id, experience) values(4, 13);
insert into bookcase(id, experience) values(5, 17);
insert into bookcase(id, experience) values(6, 21);

create table drink(
                      id varchar(50) NOT NULL,
                      machine_with_drinks_id INT NOT NULL,
                      machine_with_drinks_order INT NOT NULL,
                      hidden_objects_game_drop_rate REAL NOT NULL,
                      PRIMARY KEY(id)
);


create table machine_with_drinks(
                                    id INT NOT NULL,
                                    experience INT NOT NULL,
                                    PRIMARY KEY(id)
);

create table machine_with_drinks_cost(
                                         id serial NOT NULL,
                                         machine_with_drinks_id INT NOT NULL,
                                         building_material_id varchar(50) NOT NULL,
                                         cost INT NOT NULL,
                                         version INT NOT NULL default 0,
                                         PRIMARY KEY(id)
);

alter table machine_with_drinks_cost add constraint fk_machine_with_drinks_cost_machine_with_drinks_id
    foreign key(machine_with_drinks_id) references machine_with_drinks(id)
        on update no action on delete no action;

alter table machine_with_drinks_cost add constraint fk_machine_with_drinks_cost_building_material_id
    foreign key(building_material_id) references building_material(id)
        on update no action on delete no action;

insert into machine_with_drinks(id, experience) values(1, 1);
insert into machine_with_drinks(id, experience) values(2, 3);
insert into machine_with_drinks(id, experience) values(3, 7);
insert into machine_with_drinks(id, experience) values(4, 11);
insert into machine_with_drinks(id, experience) values(5, 12);
insert into machine_with_drinks(id, experience) values(6, 16);



insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values(1, 'TIMBER', 1);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values(1, 'STONE', 1);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (2, 'BOARD', 2);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (2, 'WIRE', 2);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (3, 'BOARD', 3);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (3, 'CHIP', 1);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (4, 'BOARD', 4);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (4, 'STONE', 1);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (5, 'BOARD', 5);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (5, 'STONE', 5);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (5, 'CHIP', 1);

insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (6, 'BOARD', 6);
insert into machine_with_drinks_cost(machine_with_drinks_id, building_material_id, cost) values (6, 'STONE', 6);





-- 2014-01-24
create table room(
                     pet_id INT NOT NULL,
                     refrigerator_id INT NULL,
                     refrigerator_x INT NULL,
                     refrigerator_y INT NULL,
                     bookcase_id int NULL,
                     bookcase_x INT NULL,
                     bookcase_y INT NULL,
                     box_newbie1 boolean NOT NULL,
                     box_newbie2 boolean NOT NULL,
                     box_newbie3 boolean NOT NULL,
                     machine_with_drinks_id INT,
                     machine_with_drinks_x INT,
                     machine_with_drinks_y INT,
                     journal_on_floor boolean not null default true,
                     every_day_box_last timestamp with time zone,
                     every_day_box boolean NOT NULL default false,
                     version INT NOT NULL DEFAULT 0,
                     PRIMARY KEY(pet_id)
);

alter table room add constraint fk_room_pet_id foreign key (pet_id)
    references pet(id) on update no action on delete no action;

alter table room add constraint fk_room_machine_with_drinks_id foreign key (machine_with_drinks_id)
    references machine_with_drinks(id) on update no action on delete no action;

alter table room add constraint fk_room_bookcase_id
    foreign key (bookcase_id) references bookcase(id)
        on update no action on delete no action;

alter table room add constraint fk_room_refrigerator_id
    foreign key (refrigerator_id) references refrigerator(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

-- 2014-05-06
create table refrigerator_cost(
                                  id serial NOT NULL,
                                  refrigerator_id INT NOT NULL,
                                  building_material_id varchar(50) NOT NULL,
                                  cost INT NOT NULL,
                                  version INT NOT NULL DEFAULT 0,
                                  PRIMARY KEY (id)
);

alter table refrigerator_cost add constraint fk_refrigerator_cost_refrigerator_id
    foreign key (refrigerator_id) references refrigerator(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

alter table refrigerator_cost add constraint fk_refrigerator_cost_building_material_id
    foreign key (building_material_id) references building_material(id)
        ON DELETE NO ACTION ON UPDATE NO ACTION;

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(1, 'TIMBER', 2);

create unique index idx_refrigerator_cost_unique on refrigerator_cost(refrigerator_id, building_material_id);

-- 2014-05-13
insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(2, 'TIMBER', 5);

-- 2014-05-14
insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(2, 'STONE', 5);

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(1, 'STONE', 2);

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(3, 'IRON', 3); -- 3 железа

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(3, 'CHIP', 3); -- 3 микросхемы

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(4, 'IRON', 4); -- 4 железа

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(4, 'CHIP', 4); -- 4 микросхемы

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(5, 'IRON', 8); -- 8 железа

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(5, 'CHIP', 5); -- 5 микросхемы

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(6, 'IRON', 10); -- 10 железа

insert into refrigerator_cost(refrigerator_id, building_material_id, cost)
values(6, 'CHIP', 6); -- 6 микросхемы




create table bookcase_cost(
                              id serial NOT NULL,
                              bookcase_id INT NOT NULL,
                              building_material_id varchar(50) NOT NULL,
                              cost INT NOT NULL,
                              PRIMARY KEY (id)
);

alter table bookcase_cost add constraint fk_bookcase_cost_bookcase_id
    foreign key (bookcase_id) references bookcase(id)
        on update no action on delete no action;

alter table bookcase_cost add constraint fk_bookcase_cost_building_material_id
    foreign key (building_material_id) references building_material(id)
        on update no action on delete no action;

create unique index idx_bookcase_cost_unique on bookcase_cost(bookcase_id, building_material_id);


insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(1, 'TIMBER', 1);

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(2, 'TIMBER', 2); -- 2 дерева

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(3, 'BOARD', 3); -- 3 доски

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(4, 'BOARD', 4);  -- 4 доски

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(5, 'TIMBER', 5); -- 5 дерева

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(5, 'BOARD', 5); -- 5 досок

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(6, 'TIMBER', 6);  -- 6 дерева

insert into bookcase_cost(bookcase_id, building_material_id, cost)
values(6, 'BOARD', 6);  -- 6 досок


create table book(
                     id varchar(50) NOT NULL,
                     bookcase_id INT NOT NULL,
                     bookcase_order INT NOT NULL,
                     hidden_objects_game_drop_rate real NOT NULL,
                     PRIMARY KEY(id)
);

alter table book add constraint fk_book_bookcase_id foreign key (bookcase_id)
    references bookcase(id) on update no action on delete no action;

CREATE TABLE pet_book (
                          id serial NOT NULL,
                          pet_id INT NOT NULL,
                          book_id varchar(50) NOT NULL,
                          version INT NOT NULL DEFAULT 0,
                          PRIMARY KEY(id)
);

create unique index idx_pet_book_pet_id_book_id on pet_book(pet_id, book_id);

alter table pet_book add constraint fk_pet_book_pet_id foreign key (pet_id)
    references pet(id) on update no action on delete no action;

alter table pet_book add constraint fk_pet_book_book_id foreign key (book_id)
    references book(id) on update no action on delete no action;

create table pet_drink(
                          id serial NOT NULL,
                          pet_id INT NOT NULL,
                          drink_id varchar(50) NOT NULL,
                          drink_count INT NOT NULL,
                          version INT NOT NULL DEFAULT 0 ,
                          PRIMARY KEY(id)
);


alter table pet_drink add constraint fk_pet_drink_drink_id foreign key (drink_id)
    references drink(id) on update no action on delete no action;

alter table pet_drink add constraint fk_pet_drink_pet_id foreign key (pet_id)
    references pet(id) on update no action on delete no action;


create table pet_journal_entry(
                                  id serial NOT NULL,
                                  created_at timestamp with time zone NOT NULL,
                                  pet_id INT NOT NULL,
                                  journal_entry_id varchar(50) NOT NULL,
                                  readed boolean NOT NULL default false,
                                  version INT NOT NULL DEFAULT 0,
                                  PRIMARY KEY(id)
);

alter table pet_journal_entry add constraint fk_pet_journal_entry_pet_id foreign key (pet_id)
    references pet(id) on update no action on delete no action;


create table level(
                      id INT NOT NULL,
                      experience INT NOT NULL,
                      PRIMARY KEY(id)
);

insert into level(id, experience) values(1, 0);
insert into level(id, experience) values(2, 10);
insert into level(id, experience) values(3, 30);
insert into level(id, experience) values(4, 60);
insert into level(id, experience) values(5, 180);


create table pet_achievement(
                                id serial NOT NULL,
                                pet_id INT NOT NULL,
                                achievement_id varchar(50) NOT NULL,
                                was_shown boolean default false,
                                version INT NOT NULL default 0,
                                PRIMARY KEY(id)
);

alter table pet_achievement add constraint fk_pet_achievement_pet_id foreign key (pet_id)
    references pet(id) on update no action on delete no action;

    
alter table drink add constraint fk_drink_machine_with_drinks_id foreign key (machine_with_drinks_id)
    references machine_with_drinks(id) on update no action on delete no action;




insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('CARROT',         1, 0, 0.5);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('DRY_FOOD',       1, 1, 0.5);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('FISH',           1, 2, 0.5);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('ICE_CREAM',      2, 0, 0.4);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('APPLE',          2, 1, 0.4);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('CABBAGE',        2, 2, 0.4);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('CHOCOLATE',      3, 0, 0.3);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('FRENCH_FRIES',   3, 1, 0.3);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('JAPANESE_ROLLS', 3, 2, 0.3);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('PIE',            4, 0, 0.2);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('POTATOES',       4, 1, 0.2);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('SANDWICH',       4, 2, 0.2);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('BANANA',         5, 0, 0.1);
insert into food(id, refrigerator_id, refrigerator_order, hidden_objects_game_drop_rate)
values('WATERMELON',     4, 1, 0.1);





insert into drink(id, machine_with_drinks_id, machine_with_drinks_order, hidden_objects_game_drop_rate)
values('WATER',        1, 0, 1.0);
insert into drink(id, machine_with_drinks_id, machine_with_drinks_order, hidden_objects_game_drop_rate)
values('MILK',         2, 0, 0.5);
insert into drink(id, machine_with_drinks_id, machine_with_drinks_order, hidden_objects_game_drop_rate)
values('BOTTLE',       3, 0, 0.4);
insert into drink(id, machine_with_drinks_id, machine_with_drinks_order, hidden_objects_game_drop_rate)
values('TEA',          4, 0, 0.2);
insert into drink(id, machine_with_drinks_id, machine_with_drinks_order, hidden_objects_game_drop_rate)
values('COFFEE',       5, 0, 0.2);
insert into drink(id, machine_with_drinks_id, machine_with_drinks_order, hidden_objects_game_drop_rate)
values('ORANGE_JUICE', 6, 0, 0.1);




insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('DESTINY',       1, 0, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('SQL',           1, 1, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('PURPLE',        1, 2, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('PLAID',         2, 0, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('PUSHKIN',       2, 1, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('BLACK',         2, 2, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('WHITE',         3, 0, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('DIRTY',         3, 1, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('EARTH',         3, 2, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('MOON_AND_STAR', 4, 0, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('GIRL',          4, 1, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('SUNSET',        4, 2, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('SAGA',          5, 0, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('NONAME',        5, 1, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('CATS',          5, 2, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('GOLD_TITLE',    6, 0, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('DARK',          6, 1, 0.1);
insert into book (id, bookcase_id, bookcase_order, hidden_objects_game_drop_rate)
values('SCHEME',        6, 2, 0.1);




insert into pet_book(pet_id, book_id)
select p.id, 'SQL'
from pet p
where not exists(select * from pet_book pb where pb.pet_id = p.id and pb.book_id = 'SQL');

