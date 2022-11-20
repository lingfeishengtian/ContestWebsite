<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Clements UIL Competition</title>
  <!-- Bootstrap -->
  <link href="./css/bootstrap-4.3.1.css" rel="stylesheet">
  <link href="./css/style.css" rel="stylesheet" type="text/css">
  <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
  <script src="js/jquery-3.3.1.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <img class="navbar-brand image" src="images/logo.png" alt="Clements Logo">
  <a class="navbar-brand navbar-center" href="#">UIL Competition</a>
  <div class="collapse navbar-collapse" id="navbarSupportedContent"></div>
  <div class="form-inline my-2 my-lg-0">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active navbar-right"> <a class="nav-link" href="./">Home <span class="sr-only">(current)</span></a> </li>
    </ul>
    <ul class="navbar-nav mr-auto">
      <li class="nav-item navbar-right"> <a class="nav-link" href="scoreboard.html">Scoreboard <span class="sr-only"></span></a> </li>
    </ul>
    <ul class="navbar-nav mr-auto">
      <li class="nav-item navbar-right"> <a class="nav-link" href="login.html" id="login-logout-nav">Login <span class="sr-only"></span></a> </li>
    </ul>
    <ul class="navbar-nav mr-auto">
      <li class="nav-item navbar-right"> <a class="nav-link" href="redirect">My Team <span class="sr-only"></span></a> </li>
    </ul>
  </div>
</nav>
<header>
  <div class="jumbotron">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <h1 class="text-center">Clements UIL Contest</h1>
          <p class="text-center">Welcome to the Clements UIL Programming competition. To get started, log in and register!</p>
          <p id="logged-in-status" class="text-center">Login Status</p>
          <p class="text-center">If you were logged in previously and now it says you are logged out, you might've cleared your cache. If you didn't do this, please contact staff <strong>RIGHT NOW</strong> because your team's credentials may have been leaked.</p>
          <script>
              $.get('login', function (response) {
                document.getElementById("logged-in-status").textContent = response;
                if (response.startsWith("Logged in as")) {
                  document.getElementById("login-logout-nav").textContent = "Logout";
                  document.getElementById("login-logout-nav").href = "logout";
                }
              });
          </script>
          <p class="text-center"><a class="btn btn-primary btn-lg btn-rounded" href="redirect" role="button">Go to Team Page</a>
        </div>
      </div>
    </div>
  </div>
</header>
<section>
  <div class="container">
    <div class="row">
      <div class="col-lg-12 mb-4 mt-2 text-center">
        <h2>Downloads</h2>
      </div>
    </div>
  </div>
  <div class="container ">
    <div class="row">
      <div class="col-lg-4 col-md-6 col-sm-12 text-center">
        <a href="downloads/jdk-8-windows-x64.zip"><img class="rounded-circle" alt="140x140" style="width: 140px; height: 140px;" src="images/epicdownloadbutton.png" data-holder-rendered="true"> </a>
        <h3>JDK 8 Windows x64</h3>
        <p>If you didn't come prepared, we gotchu.&nbsp;&nbsp;</p>
      </div>
      <div class="col-lg-4 col-md-6 col-sm-12 text-center">
        <a href="downloads/jdk-8-windows-x32.zip"><img class="rounded-circle" alt="140x140" style="width: 140px; height: 140px;" src="images/epicdownloadbutton.png" data-holder-rendered="true"> </a>
        <h3>JDK 8 Windows x32</h3>
        <p>We also have old grandma software.&nbsp;</p>
      </div>
      <div class="col-lg-4 col-md-6 col-sm-12 text-center">
        <a href="downloads/jdk-8-mac.zip"><img class="rounded-circle" alt="140x140" style="width: 140px; height: 140px;" src="images/epicdownloadbutton.png" data-holder-rendered="true"> </a>
        <h3>JDK 8 Mac</h3>
        <p>We also have <strong>superior</strong> mac software!</p>
      </div>
    </div>
    <div class="row">
    </div>
  </div>
  <div class="blank-space-baby"></div>
  <div class="jumbotron">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <h1 class="text-center">Troubleshooting</h1>
          <p class="text-center">A section dedicated to solving your technical problems!</p>
          <h2 class="text-center">Website Issues</h2>
          <p class="text-center"><b>I don't see my team and name on the scoreboard!</b><br>See the Registration section. (scroll down)</p>
          <p class="text-center"><b>Where do I download PC^2?</b><br>Click login on the top right hand corner and follow the form.</p>
          <p class="text-center"><b>I can't download anything on my team page!</b><br>See the Registration section. (scroll down)</p>
          <p class="text-center"><b>I can't submit appeals!</b><br>The contest hasn't ended yet. Appeals can only be submitted within 10 minutes of the contest ending. If the contest has ended and 10 minutes has not passed and you still cannot submit appeals, contact staff.</p>
          <h2 class="text-center">PC^2 Issues</h2>
          <p class="text-center"><b>I can't run the pc2team file!</b><br>Run the RunPC2Windows.bat file if you are on Windows and the RunPC2TeamMacOrLinux file if you are on Mac or Linux. If the window closes automatically, try re-running with administrator privileges. If these files don't exist, contact staff!</p>
          <p class="text-center"><b>The RunPC2 file immediately closes!</b><br>Ensure you have a Java Runtime installed (JDK or JRE) in your Path. You can check this by opening up a command prompt and running <code>java -version</code>. <strong>JUST BECAUSE YOU CAN COMPILE/RUN ON ECLIPSE DOES NOT MEAN THERE IS A JAVA RUNTIME ON YOUR PATH.</strong> Anything above Java 8 should work, if it still isn't working then please contact staff.</p>
          <p class="text-center"><b>The PC^2 window doesn't open and it says "Cannot start PC^2, pc2v9.ini file not found in ... check logs"!</b><br>Please look at the answer to "I can't run the pc2team file!"</p>
          <p class="text-center"><b>I'm on Mac/Linux and I can't run the RunPC2TeamMacOrLinux file!</b><br>To run the RunPC2TeamMacOrLinux file on any Linux based system (which includes Mac), you should run <code>chmod +x RunPC2TeamMacOrLinux</code> from the terminal in the folder with the RunPC2TeamMacOrLinux file. If the file still closes immediately after doing this, see the above tip about the RunPC2.</p>
          <p class="text-center"><b>Any other issues will require staff assistance.</b></p>
        </div>
      </div>
    </div>
  </div>
  <div class="blank-space-baby"></div> <!-- who put this here? -->
  <div>
    <div class="container">
      <div class="row">
        <div class="col-12">
          <h1 class="text-center">Registration</h1>
          <p class="text-center">Did you register yet? Well you can do that by logging in (go to the top right corner, click login, and login with the credentials provided to you). You will be redirected to the team menu, or if you already are logged in, click "My Team" on the top right corner. Scroll down to the Registration section and follow the instructions and form provided. You can only submit this form once so please ensure the information is correct.</p>
        </div>
      </div>
    </div>
  </div>
</section>
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
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/popper.min.js"></script>
<script src="js/bootstrap-4.3.1.js"></script>
</body>
</html>