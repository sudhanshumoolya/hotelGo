CREATE DATABASE HotelZ;
USE hotelz;


CREATE TABLE login(
    login_email VARCHAR(50) NOT NULL UNIQUE,
    login_password VARCHAR(50) NOT NULL,

    PRIMARY KEY (login_email)
);


CREATE TABLE locations(
    loc_id int AUTO_INCREMENT NOT NULL,
    street VARCHAR(100) NOT NULL,
    city VARCHAR(40) NOT NULL,
    state VARCHAR(40) NOT NULL,

    PRIMARY KEY(loc_id)
);



CREATE TABLE hotels(
    hotel_id INT AUTO_INCREMENT PRIMARY KEY,
    hotel_name VARCHAR(50) NOT NULL UNIQUE, 
    hotel_ratings FLOAT,
    hotel_no_rating INT,
    loc_id int NOT NULL,
    hotel_price INT NOT NULL,
    hotel_img_url VARCHAR(150),

    CONSTRAINT hotel_ratings CHECK(hotel_ratings BETWEEN 1 AND 5),
    FOREIGN KEY(loc_id) REFERENCES locations(loc_id)
);

CREATE TABLE rooms(
    room_id int NOT NULL,
    hotel_id int NOT NULL,
    room_name VARCHAR(20) NOT NULL,
    room_size INT NOT NULL,
    no_of_guest INT,
    room_cost INT NOT NULL,
    total_rooms INT NOT NULL,

    PRIMARY KEY (room_id,hotel_id),
    FOREIGN KEY(hotel_id) REFERENCES hotels(hotel_id)
);

 
CREATE TABLE users(
    user_id int AUTO_INCREMENT ,
    user_mail VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    user_age INT NOT NULL,
    user_address VARCHAR(150),
    mobile_number CHAR(10),

    PRIMARY KEY (user_id),
    FOREIGN KEY(user_mail) REFERENCES login(login_email)
);

 CREATE TABLE booking(
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hotel_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in MEDIUMTEXT,
    check_out MEDIUMTEXT,
    number_of_guest INT NOT NULL,
     
    FOREIGN KEY(room_id) REFERENCES rooms(room_id),
    FOREIGN KEY(user_id) REFERENCES users(user_id),
    FOREIGN KEY(hotel_id) REFERENCES hotels(hotel_id)
);



CREATE TABLE hotel_details(
    hotel_id INT NOT NULL,
    hotel_desc VARCHAR(400),
    mobile_number CHAR(10),
    hotel_email VARCHAR(50),
    wifi INT(1),
    tv INT(1),
    AC INT(1),
    cafe INT(1),

    PRIMARY KEY(hotel_id),
    FOREIGN KEY(hotel_id) REFERENCES hotels(hotel_id)
);
 


---------------------------------------------------------------------------

-- Cancle booking

CREATE TABLE canceledBooking
(
    book_id INT PRIMARY KEY,
    user_id INT NOT NULL,
    hotel_id INT NOT NULL ,
    room_id INT NOT NULL,
    check_in LONG NOT NULL,
    check_out LONG NOT NULL,
    number_of_guest INT,

    FOREIGN KEY(hotel_id) REFERENCES hotels(hotel_id),
	FOREIGN KEY(user_id) REFERENCES users(user_id),
    FOREIGN KEY(room_id) REFERENCES rooms(room_id)
);


DELIMITER $$
create trigger canceled_booking 
before delete
on booking for each row  
BEGIN
	INSERT INTO canceledBooking(book_id, user_id, hotel_id, room_id, check_in, check_out, number_of_guest) 
    VALUES (OLD.book_id, OLD.user_id, OLD.hotel_id, OLD.room_id, OLD.check_in, OLD.check_out, OLD.number_of_guest);
END$$
DELIMITER ;


---------------------------------------------------------------------
-- triggers for user name

DELIMITER $$
CREATE TRIGGER uppercase
before INSERT
ON users
FOR EACH ROW
BEGIN
  SET NEW.user_name=LOWER(NEW.user_name);
  SET NEW.user_mail=LOWER(NEW.user_mail);
  SET NEW.user_address=LOWER(NEW.user_address);
END$$
DELIMITER ;