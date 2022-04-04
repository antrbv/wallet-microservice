create table player
(
    id      int auto_increment primary key,
    balance decimal(15, 2) CHECK (balance >= 0)
);

create table transaction
(
    id        int auto_increment primary key,
    trans_id  varchar,
    player_id int,
    type      varchar,
    amount    decimal(15, 2)
);

create unique index uq_trans on transaction (trans_id);