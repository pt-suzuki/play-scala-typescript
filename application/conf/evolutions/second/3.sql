# --- !Ups
SET sql_mode = '';

create table in_house.admin_users (
    id SERIAL,
    username VARCHAR(254) NOT NULL,
    email VARCHAR(254) NOT NULL,
    password VARCHAR(254) NOT NULL,
    type text comment '認可種別',
    auth_id text comment '認可先のID',
    role VARCHAR(254) NOT NULL,
    created_at timestamp NULL DEFAULT NULL comment '作成日',
    updated_at timestamp NULL DEFAULT NULL comment '更新日'
);

create table in_house.admin_oauth_clients (
    id SERIAL,
    admin_users_id  BIGINT NOT NULL,
    grant_type VARCHAR(254) NOT NULL,
    client_id VARCHAR(254) PRIMARY KEY NOT NULL,
    client_secret VARCHAR(254) NOT NULL,
    redirect_uri VARCHAR(254) NOT NULL,
    created_at timestamp NULL DEFAULT NULL comment '作成日',
    updated_at timestamp NULL DEFAULT NULL comment '更新日'
);

create table in_house.admin_oauth_authorization_codes (
    id SERIAL,
    admin_users_id INTEGER NOT NULL,
    admin_oauth_clients_id BIGINT NOT NULL,
    code VARCHAR(254) NOT NULL,
    redirect_uri VARCHAR(254),
    created_at timestamp NULL DEFAULT NULL comment '作成日',
    updated_at timestamp NULL DEFAULT NULL comment '更新日'
);


create table in_house.admin_oauth_access_tokens (
    id serial,
    admin_users_id BIGINT NOT NULL,
    admin_oauth_clients_id BIGINT NOT NULL,
    access_token VARCHAR(254) NOT NULL,
    refresh_token VARCHAR(254) NOT NULL,
    created_at timestamp NULL DEFAULT NULL comment '作成日',
    updated_at timestamp NULL DEFAULT NULL comment '更新日'
);


# --- !Downs

drop table in_house.admin_oauth_access_tokens;
drop table in_house.admin_oauth_authorization_codes;
drop table in_house.admin_oauth_clients;
drop table in_house.admin_users;