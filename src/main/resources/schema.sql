drop table if exists booking_room_info;
drop table if exists booking_extra_info;
drop table if exists booked_room;
drop table if exists unbooked_room;
drop table if exists booking;
drop table if exists guest;
drop table if exists house_extra_info;
drop table if exists list_item;
drop table if exists checklist;
drop table if exists sms_variable;
drop table if exists sms_template;
drop table if exists house_off;
drop table if exists invitation;
drop table if exists bed;
drop table if exists room;
drop table if exists host_house;
drop table if exists house;
drop table if exists host;
drop table if exists terms;

create table host (
    host_id bigint auto_increment,
    type varchar(31) not null,
    email varchar(255) not null,
    nickname varchar(255) not null,
    password varchar(255) not null,
    profile_image varchar(255),
    thumbnail_image varchar(255),
    city varchar(255),
    detail varchar(255),
    postcode varchar(10),
    street varchar(255),
    sub_host_status boolean default true,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    deleted_at timestamp,
    primary key (host_id)
);

create table house (
    house_id bigint auto_increment,
    uuid varchar(36),
    name varchar(255),
    city varchar(255),
    detail varchar(255),
    postcode varchar(255),
    street varchar(255),
--     main_image varchar(255),
    main_image text,
    thumbnail_image varchar(255),
    main_number varchar(15),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    deleted_at timestamp,
    primary key (house_id)
);

create table host_house (
    host_house_id bigint auto_increment,
    host_id bigint references host(host_id),
    house_id bigint references house(house_id),
    primary key (host_house_id)
);

create table room (
    room_id bigint auto_increment,
    default_capacity integer not null,
    max_capacity integer not null,
    memo varchar(255), --메모는 text?
    name varchar(255),
    off_peak_amount varchar(10),
    peak_amount varchar(10),
    room_type varchar(255),
    house_id bigint references house(house_id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp on update current_timestamp,
    deleted_at timestamp,
    primary key (room_id)
);

create table invitation (
    invitation_id bigint auto_increment,
    invitation_code varchar(10),
    expiration_date timestamp,
    house_id bigint references house(house_id),
    created_at timestamp default CURRENT_TIMESTAMP,
    primary key (invitation_id)
);

create table bed (
    bed_id bigint auto_increment,
    alias varchar(255),
    bed_type varchar(255),
    room_id bigint references room(room_id),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default current_timestamp on update current_timestamp,
    deleted_at timestamp,
    primary key (bed_id)
);

create table house_off (
    house_off_id bigint auto_increment,
    off_date timestamp,
    house_id bigint references house(house_id),
    primary key (house_off_id)
);

create table sms_template (
    sms_template_id bigint auto_increment,
    contents varchar(255), --text
    title varchar(255),
    house_id bigint references house(house_id),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default current_timestamp on update current_timestamp,
    deleted_at timestamp,
    primary key (sms_template_id)
);

create table sms_variable (
    sms_variable_id bigint auto_increment,
    name varchar(255),
    sms_template_id bigint references sms_template(sms_template_id),
    primary key (sms_variable_id)
);

create table checklist (
    checklist_id bigint auto_increment,
    category varchar(255),
    title varchar(255),
    target_data timestamp,
    house_id bigint references house(house_id),
    created_at timestamp default CURRENT_TIMESTAMP,
    deleted_at timestamp,
    primary key (checklist_id)
);

create table list_item (
    list_item_id bigint auto_increment,
    contents varchar(255),
    checked boolean not null,
    checked_at timestamp,
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default current_timestamp on update current_timestamp,
    checklist_id bigint references checklist(checklist_id),
    primary key (list_item_id)
);

create table house_extra_info (
    house_extra_info_id bigint auto_increment,
    title varchar(255),
    house_id bigint references house(house_id),
    deleted_at timestamp,
    primary key (house_extra_info_id)
);

create table guest (
    guest_id bigint auto_increment,
    email varchar(255),
    memo varchar(255),
    name varchar(255),
    phone_number varchar(10) not null,
    primary key (guest_id)
);

create table booking (
    booking_id bigint auto_increment,
    check_in timestamp not null,
    check_out timestamp not null,
    female_count integer default 0,
    male_count integer default 0,
    requirement varchar(255),
    guest_id bigint references guest(guest_id),
    house_id bigint references house(house_id),
    created_at timestamp default CURRENT_TIMESTAMP,
    updated_at timestamp default current_timestamp on update current_timestamp,
    deleted_at timestamp,
    primary key (booking_id)
);

create table unbooked_room (
    unbooked_room_id bigint auto_increment,
    entry_date timestamp not null,
    is_additional_bed boolean not null,
    is_down_bed boolean not null,
    today_amount varchar(255) not null,
    bed_id bigint references bed(bed_id),
    room_id bigint references room(room_id),
    primary key (unbooked_room_id)
);

create table booked_room (
    booked_room_id bigint auto_increment,
    unbooked_room_id bigint references unbooked_room(unbooked_room_id),
    primary key (booked_room_id)
);

create table booking_extra_info (
    booking_extra_info_id bigint auto_increment,
    attend_date timestamp not null,
    attend_status varchar(255) not null,
    memo varchar(255),
    people_count integer not null,
    house_extra_info_id bigint references house_extra_info(house_extra_info_id),
    booking_id bigint references booking(booking_id),
    primary key (booking_extra_info_id)
);

create table booking_room_info (
    booking_room_info_id bigint auto_increment,
    gender varchar(255) not null,
    booking_id bigint references booking(booking_id),
    unbooked_room_id bigint references unbooked_room(unbooked_room_id),
    deleted_at timestamp,
    primary key (booking_room_info_id)
);

create table terms (
    terms_id bigint auto_increment,
    type varchar(255) not null,
    contents text,
    primary key (terms_id)
);
