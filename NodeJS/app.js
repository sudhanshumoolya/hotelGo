const express = require('express');
const mysql = require('mysql');
//const bodyParser = require('body-parser');
const localtunnel = require('localtunnel');
//const { deepEqual } = require('assert');

const app = express();
/*app.use(bodyParser.json())
app.use(bodyParser.urlencoded({   
    extended: true
  }));
*/

app.use(express.json());
app.use(express.urlencoded());

//const router = express.Router();

const db = mysql.createConnection({
    host     : 'localhost',
    user     : 'root',
    password : 'password',
    database : 'hotel_managements',
    multipleStatement : true

});

db.connect((err)=>{
    if(!err){
        console.log('Connected');
    }
    else{
        console.log(err.stack);
    }
});




app.get("/hotels",(req,res)=>{

    const city = req.query.city;

    db.query("SELECT * FROM hotels h, locations l WHERE h.loc_id=l.loc_id and l.city=?",[city],(err, rows, fields)=>{
        if(!err){
            res.send(rows);
        }
        else{
            console.log(err);
        }
    })
});

app.get("/hotelDetails",(req,res)=>{
    const hotelId = req.query.hotelId;

    db.query("SELECT * FROM hotel_details WHERE hotel_id=?", hotelId, (err,result,fields)=>{
        if(!err){
            res.send(result);
        }
        else{
            console.log(err);
        }
    });
});


app.get("/login",(req,res)=>{
    
    const userEmail = req.query.userEmail;
    const userPassword = req.query.userPassword;

    db.query("SELECT * FROM login WHERE login_email=? AND login_password=?",[userEmail, userPassword],(err,rows,fields)=>{
        if(!err){
            if(rows.length>0)
            {
                res.status(200).send("Login Successful");
            }
            else{
                res.status(400).send("Login UnSuccessful");
            }
            
        }
        else{
            console.log(err);
        }
    });
})

app.post("/signup",(req,res)=>{

    const data= [req.body.userEmail,req.body.userPassword];

    db.query("INSERT INTO login (login_email, login_password) VALUES (?, ?)",data,(err,result)=>{
        if(!err)
        {
            res.send("Insertion complete");
        }
        else
        {
            res.send("Insertion INcomplete");
            console.log(err);
        }

    });

});


app.post("/user",(req,res)=>{

    const data = [req.body.email,req.body.name,req.body.age,req.body.address,req.body.mobileNo];

    db.query("INSERT INTO users (user_mail, user_name, user_age, user_address, mobile_number) VALUES (?, ?, ?, ?, ?)",data,(err,result)=>{
            if(!err){
                res.send("Insertion complete");
            }
            else
            {
                res.send("Insertion INcomplete");
               // console.log(err);
            }
    });
})


app.get("/user",(req,res)=>{

    const userEmail = req.query.userEmail;

    db.query("SELECT * FROM users WHERE user_mail=?",[userEmail],(err,results,fields)=>{
        if(!err)
        {
            if(results.length>0)
            {
                res.send(results);
            }
            else{
                res.status(400).send(results);
            }
        }
        else{
            res.status(400).send(err);
        }
    });
});


app.get("/room",(req,res)=>{

    const checkIn = req.query.checkIn;
    const checkOut = req.query.checkOut;
    const hotelId = req.query.hotelId;
    const noOfGuest = req.query.noOfGuest;

    const data = [noOfGuest, hotelId, hotelId, noOfGuest, checkIn, checkOut, checkIn, checkOut, checkIn, checkOut];

    db.query("SELECT * FROM rooms R WHERE no_of_guest=? AND hotel_id=? AND total_rooms>(SELECT COUNT(*) FROM booking B WHERE hotel_id=? AND B.number_of_guest=? AND ((check_in>? AND check_in<?) OR (check_out>? AND check_out<?) OR (check_in<? AND check_out>?)))",data,(err,results,fields)=>{
        if(!err)
        {
            if(results.length>0)
            {
                res.send(results);
            }
            else{
                res.status(400);
            }
        }
        else{
            res.status(400).send(err);
        }

     });

});


app.post("/booking",(req,res)=>{

    const data = [req.body.user_id, req.body.hotel_id, req.body.room_id, req.body.check_in, req.body.check_out, req.body.number_of_guest];

    db.query("INSERT INTO booking ( user_id, hotel_id, room_id, check_in, check_out, number_of_guest) VALUES (?, ?, ?, ?, ?, ? )",data,(err,fields)=>{
        if(!err)
        {
            res.status(200).send("Successfully booked the room");
        }
        else{
            res.status(400).send("Falied to book the room"+err);
        }

    });
});

app.get("/booked",(req,res)=>{

    const userId = req.query.userId;

    db.query("SELECT b.book_id, b.check_in, b.check_out, b.number_of_guest, h.hotel_name, h.hotel_img_url FROM booking b, hotels h WHERE b.user_id=? AND b.hotel_id=h.hotel_id",userId,(err,results,fields)=>{
        if(!err)
        {
            res.status(200).send(results);
        }
        else
        {
            res.status(400).send(err);
        }
    })


});

app.post("/cancelHotel",(req,res)=>{

    const bookId = req.body.bookId;
    console.log(bookId);

    //db.query("SET SQL_SAFE_UPDATES = 0");
    db.query("DELETE FROM booking WHERE book_id=?", [bookId], (err, result)=>{
        if(!err)
        {
            res.status(200).send("Canceled the room");
        }
        else{
            res.status(400).send("Falied to cancel the room"+err);
        }
    });


});




app.listen('8080',function(req,res){
    console.log("Server Running");
});

