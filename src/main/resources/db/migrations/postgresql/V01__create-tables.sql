CREATE TABLE IF NOT EXISTS clo_clock_in (
    id               INT    PRIMARY KEY,
    user_id          INT NOT NULL,
    clock_in TIMESTAMP(6) NOT NULL
);

CREATE SEQUENCE clo_clock_in_id_seq;

ALTER TABLE clo_clock_in ALTER COLUMN id SET DEFAULT nextval('clo_clock_in_id_seq');