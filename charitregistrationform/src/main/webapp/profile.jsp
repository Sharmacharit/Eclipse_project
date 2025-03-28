<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #000;
            color: #fff;
            padding: 20px;
        }
        .container {
            max-width: 500px;
            margin: 0 auto;
            background-color: #222;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h2 {
            color: #fff;
        }
        .logout-btn {
            position: absolute;
            top: 20px;
            right: 20px;
            background-color: #f44336;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .logout-btn:hover {
            background-color: #e53935;
        }
        .message {
            margin-top: 20px;
        }
        .thank-you-message {
            font-size: 18px;
            color: #4CAF50;
        }
    </style>
</head>
<body>

<div class="container">
    <form action="profile" method="post">
        <button class="logout-btn" type="submit">Logout</button>
    </form>
    <h2>Welcome, <span id="user-email"><%= request.getAttribute("userEmail") %></span></h2>
    <div class="message">
        <p>Welcome to your profile page. Here you can view your information and manage your account.</p>
    </div>
</div>

<script>
    // The email is populated dynamically by the servlet in the <span> tag above
    const email = "<%= request.getAttribute("userEmail") %>";  // Retrieve from request attribute
    document.getElementById('user-email').innerText = email;
</script>

</body>
</html>
