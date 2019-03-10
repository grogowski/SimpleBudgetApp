<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge"/>
    <title>Welcome to Simple Budget App</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/css/welcome.css">
</head>
<body>
<header class="header">
    <div class="overlay">
        <div class="container">
            <div class="description">
                <h2>Welcome to Simple Budget App!</h2>
                <p>Read on to find out how to use it.</p>
                <h2>Budget view</h2>
                <p>This will be the next screen you see. You will see the list of 10 default spending categories.
                    To edit a category name just click on it, type your new name and accept it with a green tick icon.
                    To edit a budgeted amount follow the same steps. Click on the number, edit, accept changes.
                    Hit the "Add spending category" button below to add a new category.</p>
                <h2>Transactions view</h2>
                <p>Here you log all your income and spending. Whenever there is any money movement on your account hit the
                    "Add income" or "Add spending" button and fill the simple form. This will let you monitor where does your money go.
                You can edit/delete a record later in case of any mistake.</p>
                <h2>Charts view</h2>
                <p>A bit of graphic presentation for those to whom pictures say more than numbers.</p>
                <h2>Manage categories</h2>
                <p>This screen lets you edit and add your income and spending categories.</p>
                <h2>Lets start budgeting!</h2>
                <p>Enter your present account balance below and you are good to go.
                    If you don't want to check it now just input an estimate value, you can edit it later.</p>
                <form action="/user/welcome" method="post">
                    <input type="number" step="0.01" name="initialBalance">
                    <input class="btn btn-primary" type="submit" value="Next">
                </form>
            </div>
        </div>
    </div>
</header>
<script type="text/javascript" src='/js/index.js'></script>
</body>
</html>
