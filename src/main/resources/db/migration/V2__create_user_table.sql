create table users(
	username TEXT not null primary key,
	password TEXT not null,
	enabled boolean not null
);

create table authorities (
	username TEXT not null,
	authority TEXT not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);