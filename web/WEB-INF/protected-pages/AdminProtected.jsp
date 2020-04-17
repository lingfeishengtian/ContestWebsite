<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Admin Panel</title>
    <!-- Bootstrap -->
    <link href="css/bootstrap-4.3.1.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    </head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
<img class="navbar-brand image" src="images/travis_logo.png" alt="Travis Logo">
<a class="navbar-brand navbar-center" href="#">UIL Competition</a>
<div class="collapse navbar-collapse" id="navbarSupportedContent"></div>
<div class="form-inline my-2 my-lg-0">
<ul class="navbar-nav mr-auto">
<li class="nav-item navbar-right"> <a class="nav-link" href="index.html">Home <span class="sr-only"></span></a> </li>
</ul>
<ul class="navbar-nav mr-auto">
<li class="nav-item navbar-right"> <a class="nav-link" href="scoreboard.html">Scoreboard <span class="sr-only"></span></a> </li>
</ul>
<ul class="navbar-nav mr-auto">
<li class="nav-item navbar-right"> <a class="nav-link" href="login.html">Login <span class="sr-only"></span></a> </li>
</ul>
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
                    <h1 class="text-center">Welcome Administrator&nbsp;</h1>
                    <p class="text-center">This is where you input written scores and do admin stuff!</p>
                    <p class="text-center">Once you enter individual written scores, all scores including programming ones will be automatically added together and placed. A custom scoreboard would be generated. For you, a file with all data will be generated!</p>
                    <p class="text-center"> <a class="btn btn-primary btn-lg btn-rounded" href="authorized-download?id=1" role="button">Download PC^2 Judge</a></p>
                    <p class="text-center"><a class="btn btn-primary btn-lg btn-rounded" href="generateReport" role="button">Generate Full Score Report</a> <a class="btn btn-primary btn-lg btn-rounded" href="generateIndividual?id=1" role="button">Get Individual Written Scoreboard</a> <a class="btn btn-primary btn-lg btn-rounded" href="generateIndividual?id=2" role="button">Get Individual Programming Scoreboard</a></p>
                    </div>
                </div>
            </div>
        </div>
    </header>
<div class="container">
    <div class="row">
        <div class="col-12">
            <h1 class="text-center">Input Scores in Database&nbsp;</h1>
            <p class="text-center">Hey admins! Here, registered teams will popup with details on team number, school, and students. What you can do is input written scores and generate a data report!&nbsp;</p>
            <p class="text-center">If there are any issues with team number or values you cannot edit, <strong>I strongly urge you to contact the system administrator.</strong> He will be able to safely modify those values.</p>
            <div class="row text-center">
            <div class="text-center col-12">
            <form id="editForm" class="text-center" action = "editscore" method = "POST">
                <label>How do I use this? Enter missing values or modify existing values to edit the database. You will then be able to generate a scoreboard.</label>
                <%=request.getAttribute("formData")%>
                <button type="submit" id="appealSubmit" class="btn btn-primary btn-lg"> Save Changes</button>
                        </form>
                      </div>
                    </div>
              </div>
        </div>
    </div>
<div class="blank-space-baby"></div>
<div class="jumbotron">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h1 class="text-center">Open Appeals</h1>
                <p class="text-center">You can manually resolve appeals here and give a reason for the rejection or approval. The appeals are ordered by "whether unresolved or not" to "team number" then "problem number".</p>
                <div class="row-horz">
                    <%=request.getAttribute("appealRowElements")%>
                </div>
            </div>
        </div>
    </div>
</div>
<div></div>
    <footer class="text-center">
    <div class="container">
          <div class="row">
                <div class="col-12">
                      <p>Made by Hunter Han</p>
                    </div>
              </div>
        </div>
        </footer>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) --> 
    <script src="js/jquery-3.3.1.min.js"></script> 
    <!-- Include all compiled plugins (below), or include individual files as needed --> 
    <script src="js/popper.min.js"></script> 
    <script src="js/bootstrap-4.3.1.js"></script>
  </body>
</html>
