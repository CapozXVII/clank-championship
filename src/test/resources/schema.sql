create table hibernate_sequence (
    next_val bigint
);
insert into hibernate_sequence
values (1);
CREATE TABLE championship (
    id                BIGINT NOT NULL,
    championship_name VARCHAR(255) NULL,
    starting_date     datetime NULL,
    ending_date       datetime NULL,
    CONSTRAINT pk_championship PRIMARY KEY (id)
);

ALTER TABLE championship
    ADD CONSTRAINT uc_9f2f12e34a3ea9d628f259654 UNIQUE (championship_name);

CREATE TABLE game (
    id              BIGINT NOT NULL,
    location        VARCHAR(255) NULL,
    game_date       datetime NULL,
    championship_id BIGINT NOT NULL,
    CONSTRAINT pk_game PRIMARY KEY (id)
);

ALTER TABLE game
    ADD CONSTRAINT FK_GAME_ON_CHAMPIONSHIP FOREIGN KEY (championship_id) REFERENCES championship (id);
create table player (
    id       bigint       not null,
    nickname varchar(255) not null,
    fullname varchar(255) not null,
    primary key (id, nickname)
);

CREATE TABLE points (
    id               BIGINT        NOT NULL,
    artifacts        INT default 0 not null,
    eggs             INT default 0 not null,
    monkeys          INT default 0 not null,
    chalices         INT default 0 not null,
    maps             INT default 0 not null,
    tomes            INT default 0 not null,
    prisoners        INT default 0 not null,
    gems             INT default 0 not null,
    undefined_cards  INT default 0 not null,
    money            INT default 0 not null,
    crowns           INT default 0 not null,
    market           INT default 0 not null,
    mastery          INT default 0 not null,
    trophies         INT default 0 not null,
    other_categories VARCHAR(255) NULL,
    character_enum   VARCHAR(255) NULL,
    game             BIGINT,
    player_nickname  VARCHAR(255),
    player_id        VARCHAR(255),
    CONSTRAINT pk_points PRIMARY KEY (id)
);

ALTER TABLE points
    ADD CONSTRAINT FK_POINTS_ON_GAME FOREIGN KEY (game) REFERENCES game (id);
alter table points
    add constraint FK_POINTS_ON_PLAYER foreign key (player_id, player_nickname) references player (id, nickname);

CREATE TABLE prevision (
    id              BIGINT NOT NULL,
    predictions     VARCHAR(255),
    player_nickname VARCHAR(255),
    player_id       VARCHAR(255),
    championship    BIGINT,
    CONSTRAINT pk_prevision PRIMARY KEY (id)
);

ALTER TABLE prevision
    ADD CONSTRAINT FK_PREVISION_ON_GAME FOREIGN KEY (championship) REFERENCES championship (id);
alter table prevision
    add constraint FK_PREVISION_ON_PLAYER foreign key (player_id, player_nickname) references player (id, nickname);
