<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Booking</title>
</head>
<body>
<h2>Book a Room</h2>
<form action="BookingServlet" method="post">
    Room Number: <input type="text" name="room" required /><br/><br/>
    Date: <input type="date" name="date" required /><br/><br/>
    <input type="submit" value="Book" />
</form>
</body>
</html>