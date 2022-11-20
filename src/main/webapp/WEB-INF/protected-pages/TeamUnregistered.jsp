<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Team Panel</title>
    <link href="css/bootstrap-4.3.1.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <script src="js/jquery-3.3.1.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <img class="navbar-brand image" src="images/logo.png" alt="Clements Logo">
    <a class="navbar-brand navbar-center" href="#">UIL Competition</a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent"></div>
    <div class="form-inline my-2 my-lg-0">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item navbar-right"> <a class="nav-link" href="./">Home <span class="sr-only"></span></a> </li>
        </ul>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item navbar-right"> <a class="nav-link" href="scoreboard.html">Scoreboard <span class="sr-only"></span></a> </li>
        </ul>
        <%
        if((int)request.getAttribute("teamNum") >= 0){
            %>
            <ul class="navbar-nav mr-auto">
            <li class="nav-item navbar-right"> <a class="nav-link" href="logout">Logout <span class="sr-only"></span></a> </li>
            </ul>
            <%
        }
        else{
            %>
            <ul class="navbar-nav mr-auto">
            <li class="nav-item navbar-right"> <a class="nav-link" href="login.html">Login <span class="sr-only"></span></a> </li>
            </ul>
            <%
        }
        %>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active navbar-right"> <a class="nav-link" href="redirect">My Team <span class="sr-only">(current)</span></a> </li>
        </ul>
    </div>
</nav>
<header>
    <div class="jumbotron">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <h1 class="text-center">Welcome Team <%=request.getAttribute("teamNum")%></h1>
                    <p class="text-center">You have not registered yet and cannot access team features. Please register in order to do so.</p>
                </div>
            </div>
        </div>
    </div>
</header>
<div class="container">
    <div class="row">
        <div class="col-12 col-md-8 mx-auto">
            <div class="jumbotron">
                <div class="row text-center">
                    <div class="text-center col-12">
                        <h2>Register Your Team</h2>
                        <p>Your school format should be (school name) (school level), for example: Clements HS. Your name formats should be (First Name) (Last Initial), for example: Bob J.</p>
                    </div>
                    <div class="text-center col-12">
                        <form id="registrationForm" clrs="text-center" action="register" method="POST">
                            <div class="form-group">
                                <label for="school">School Name</label>
                                <input type="text" class="form-control" id="school" name="school" placeholder="Example HS" aria-describedby="schoolRegister">
                                <span id="schoolRegister" class="form-text text-muted" style="display: none;">Please enter your school name.</span>
                            </div>
                            <div class="form-group">
                                <label for="name1">Teammate 1</label>
                                <input type="text" class="form-control" id="name1" name="name1" placeholder="Teammate 1" aria-describedby="name1Register">
                                <span id="name1Help" class="form-text text-muted" style="display: none;">Please enter your name.</span>
                            </div>
                            <div class="form-group">
                                <label for="name2">Teammate 2</label>
                                <input type="text" class="form-control" id="name2" name="name2" placeholder="Teammate 2" aria-describedby="name2Register">
                                <span id="name2Help" class="form-text text-muted" style="display: none;">Please enter your name.</span>
                            </div>
                            <div class="form-group">
                                <label for="name3">Teammate 3</label>
                                <input type="text" class="form-control" id="name3" name="name3" placeholder="Teammate 3" aria-describedby="name3Register">
                                <span id="name3Help" class="form-text text-muted" style="display: none;">Please enter your name.</span>
                            </div>
                            <button type="submit" id="feedbackSubmit" class="btn btn-primary btn-lg"> Send</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="blank-space-baby"></div>
<footer class="text-center">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <p>Made by Hunter, Damian, and Erica</p>
            </div>
        </div>
    </div>
</footer>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap-4.3.1.js"></script>
</body>
</html>