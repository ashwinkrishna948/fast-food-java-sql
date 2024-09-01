Table creation

create table users 
(phone_num varchar (10) primary key on delete cascade,
name varchar (20),
address varchar (30),
password varchar (10)
);

create table restaurant (
r_id number (10) primary key on delete cascade, 
r_name varchar  (20),
address varchar (30));

create table cuisine (c_name varchar (10) primary key on delete cascade);

create table restaurant_cuisine 
(
    rc_id number primary key,
    r_id number references restuarant,
    cuisine varchar (20)references cuisine,
)

create table orders (
o_id number primary key on delete cascade, 
price int, 
phone_num varchar (20) references users, 
r_id number references restaurant, 
order_time timestamp);

create table dish (d_id number primary key on delete cascade, 
r_id number references restaurant, 
d_name varchar (20), 
price number, 
isVeg number);

create table order_has_dishes (
ohd_id number primary key, 
o_id number references orders, 
d_id number references dish, 
quantity int);

create table admin(
    ad_id integer references restaurant, 
    password varchar(20));

Insertions
insert into restaurant values (1, 'Dollops', 'Tiger Circle');
insert into restaurant values (2, 'Egg Factory', 'Tiger Circle');
insert into restaurant values (3, 'Laughing Budha', 'End Point');
insert into restaurant values (4, 'Mexican Burrito', 'End Point');
insert into restaurant values (5, 'Pai Tiffins', 'Eshwar Nagar');

insert into cuisine values ('Indian');
insert into cuisine values ('Italian');
insert into cuisine values ('Chinese');
insert into cuisine values ('Mexican');

insert into restaurant_cuisine values(1, 1, 'Indian');
insert into restaurant_cuisine values(2, 2, 'Italian');
insert into restaurant_cuisine values (3, 3, 'Chinese');
insert into restaurant_cuisine values (4, 1, 'Chinese');
insert into restaurant_cuisine values (5, 4, 'Mexican');
insert into restaurant_cuisine values (6, 5, 'Indian');

insert into dish values(1, 1, 'Chicken Tikka', 140, 0);
insert into dish values(2, 1, 'Paneer Tikka', 120, 1);
insert into dish values(3, 1, 'Chilly Paneer', 150, 1);
insert into dish values (4, 1, 'Chole Bhature', 170, 1);

insert into dish values (5, 2, 'Veg Pasta', 150, 1);
insert into dish values (6, 2, 'Chicken Pasta', 170, 0);
insert into dish values (7, 2, 'Chocolate Pastry', 50, 0);

insert into dish values (8, 3, 'Veg Noodles', 100, 1);
insert into dish values (9, 3, 'Egg Noodles', 120, 0);
insert into dish values (10, 3, 'Veg Dumplings', 150, 1);
insert into dish values (11, 3, 'Chicken Dumplings', 180, 0);

insert into dish values (12, 4, 'Mushroom Burrito', 200, 1);
insert into dish values (13, 4, 'Chicken Tortilla', 250, 0);
insert into dish values (14, 4, 'Paneer Fajitas', 180, 1);
insert into dish values (15, 4, 'Egg Fajitas' , 150, 0);

insert into dish values (16, 5, 'Masala Dosa', 80, 1);
insert into dish values (17, 5, 'Idli', 60, 1);
insert into dish values (18, 5, 'Pongal', 80, 1);
insert into dish values (19, 5, 'Pav Bhaji', 70, 1);

insert into admin values(1, 'dollops1');




Queries

For restaurants
---------------

List all restaurants
select r_name, address from restaurant

Filter restaurants by name
Select r_name, address from restaurant where upper(r_name) like upper ('dollops');

Filter restaurants by cuisine
with rids (id) as (select r_id from restaurant_cuisine where cuisine = 'Indian') select r_name from restaurant, rids where rids.id = restaurant.r_id;

Filter restaurants by name and cuisine
with rids (id) as (select r_id from restaurant_cuisine where cuisine = 'Indian') select r_name from restaurant, rids where rids.id = restaurant.r_id and r_name = 'Dollops'

List orderid, restaurant and total for past orders by a user
select o_id, r_name, price from orders join restaurant on orders.r_id = restaurant.r_id where phone_num = '9748700604' order by order_time desc;

For dishes in a restaurant
--------------------------

List all dishes of a restaurant
select d_name, price from dish where r_id = 1;

Sort dish by price 
select d_name, price from dish where r_id =1 order by price;

Sort dish by popularity
with dishpop (did, c) as 
(select dish.d_id, count(dish.d_id) as count from dish left outer join order_has_dishes on dish.d_id = order_has_dishes.d_id group by dish.d_id) 
select d_name, price from dish, dishpop where r_id = 5
and dishpop.did = dish.d_id order by c desc;

Display only vegetarian dishes of a restaurant
select d_name, price from dish where r_id =1 and isVeg = 1;

Display vegetarian dishes sorted by price
select d_name, price from dish where r_id =1 and isVeg = 1 order by price;

Display vegetarian dishes sorted by popularity
with dishpop (did, c) as 
(select dish.d_id, count(dish.d_id) as count from dish left outer join order_has_dishes on dish.d_id = order_has_dishes.d_id group by dish.d_id) 
select d_name, price from dish, dishpop where r_id = 2
and dishpop.did = dish.d_id and isVeg = 1 order by c desc;


For order cart
--------------
Procedure to insert order details to order table

create or replace procedure insertOrders (
    price number,
    phone_num varchar,
    r_id number,
    order_time varchar, 
    newid out number
)
as
maxoid int:=1;
begin
    select max(o_id) into maxoid from orders;
    newid := maxoid + 1;
    insert into orders values (newid, price, phone_num, r_id, to_timestamp (order_time, 'yyyy/MM/dd HH24.mi.ss'));
end;
/

Procedure to insert each dish in an order into order_has_dishes table

create or replace procedure insertOrderHasDishes (
    oid int,
    did int,
    quantity int
)
as
newid int:= 1;
begin
    select max(ohd_id) into newid from order_has_dishes;
    newid := newid +1;
    insert into order_has_dishes values (newid, oid, did, quantity) ;
end;
/


User profile
------------
Update name

update users
set name = "Nehal" where phone_num = "9748700604";

Update address

update users
set address = "MIT Block 13" where phone_num= "9748700604";

Update password

update users
set password = "newpass" where phone_num= "9748700604";

Fetch latest 10 orders

select * from (
    select o_id, r_name, price 
    from orders join restaurant on orders.r_id = restaurant.r_id 
    where phone_num = '9748700604' 
    order by order_time desc )
where rownum <= 10;


CREATE SEQUENCE user_seq START WITH 1;

CREATE OR REPLACE TRIGGER user_bir 
BEFORE INSERT ON dish 
FOR EACH ROW

BEGIN
  SELECT user_seq.NEXTVAL
  INTO   :new.d_id
  FROM   dual;
END;
/