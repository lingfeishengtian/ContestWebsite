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
    <img class="navbar-brand image" src="images/logo.png" alt="Travis Logo">
    <a class="navbar-brand navbar-center" href="#">UIL Competition</a>
    <div class="collapse navbar-collapse" id="navbarSupportedContent"></div>
    <div class="form-inline my-2 my-lg-0">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item navbar-right"> <a class="nav-link" href="./">Home <span class="sr-only"></span></a> </li>
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
                    <h1 class="text-center">Welcome Team <%=request.getAttribute("teamNum")%></h1>
                    <p class="text-center">This is your team's management panel. You will register, submit appeals and download sample data. Downloading PC^2, dry run, and sample data files are secure downloads and require you to login and register. Please register or your score may not be counted.</p>
                    <p class="text-center">Go back to the home page and view the "Troubleshooting" section if you have some technical difficulties.</p>
                    <p class="text-center"><strong>Sample data download opens one minute before the contest starts.</strong></p>
                    <p class="text-center"><a class="btn btn-primary btn-lg btn-rounded" href="authorized-download?id=2" role="button">Download PC^2</a> <a class="btn btn-primary btn-lg btn-rounded" href="downloadContestSampleData" role="button">Download Sample Data</a> <a class="btn btn-primary btn-lg btn-rounded" href="authorized-download?id=3" role="button">Download DryRun Data</a></p>
                    <h1 class="text-center">Registration Information</h2>
                    <p class="text-center">Thank you for registering, please double check your registration information. If there are any errors, please report them to staff.</p>
                    <p class="text-center"><strong>School: </strong><%=request.getAttribute("school")%></p>
                    <p class="text-center"><strong>Team Members: </strong><%=request.getAttribute("members")%></p>
                </div>
            </div>
        </div>
        <div class="blank-space-baby"></div>
        <div>
            <p id="countdownTimer" class="countdown-text"></p>
            <p id="contest-info" class="countdown-text-sub">until contest starts.</p>
            <script>
                function startTimer(duration, display) {
                    var start = Date.now(),
                        diff,
                        minutes,
                        seconds;
                    var interval;
                    function timer() {
                        diff = duration - (((Date.now() - start) / 1000) | 0);

                        minutes = (diff / 60) | 0;
                        seconds = (diff % 60) | 0;

                        minutes = minutes < 10 ? "0" + minutes : minutes;
                        seconds = seconds < 10 ? "0" + seconds : seconds;

                        display.textContent = minutes + " minutes " + seconds + " seconds";

                        if (diff <= 0 || duration < 0){
                            display.textContent = "The contest has STARTED!"
                            document.getElementById("contest-info").textContent = "";
                            clearInterval(interval)
                        } else {
                            document.getElementById("contest-info").textContent = "until contest starts.";
                        }
                    };
                    timer();
                    interval = setInterval(timer, 1000);
                }

                $.get('timeTillContestStart', function (data) {
                    var fiveMinutes = parseInt(data),
                        display = document.querySelector('#countdownTimer');
                    startTimer(fiveMinutes, display);
                })
            </script>
        </div>
    </div>
</header>
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h1 class="text-center">Appeals</h1>
                <p class="text-center">After the competition is over, you can submit appeals here. But you only have 10 minutes after the competition before appeals are closed! You may only submit one appeal for each problem. Submitting an appeal for the same problem will override the old one. Once the status for your problem has been changed, the admins will not check your appeal again.</p>
                <div class="col-12 col-md-8 mx-auto">
                    <div class="row text-center">
                        <div class="text-center col-12">
                            <h2>Please fill out the Appeal Form</h2>
                        </div>
                        <div class="text-center col-12">
                            <form id="feedbackForm" class="text-center" action = "AppealForm" method = "POST">
                                <div class="form-group">
                                    <label for="problem">Problem Number</label>
                                    <input type="text" class="form-control" id="problem" name="problem" placeholder="Problem Number (Ex. 2)" aria-describedby="emailHelp">
                                    <span id="emailHelp" class="form-text text-muted" style="display: none;">Please enter a valid problem number.</span>
                                </div>
                                <div class="form-group">
                                    <label for="message">Message</label>
                                    <textarea rows="10" cols="100" class="form-control" id="message" name="message" placeholder="Message" aria-describedby="messageHelp"></textarea>
                                    <span id="messageHelp" class="form-text text-muted" style="display: none;">Please enter a message.</span>
                                </div>
                                <button type="submit" id="appealSubmit" class="btn btn-primary btn-lg"> Send</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
<div>
<div class="blank-space-baby"></div>
<div class="jumbotron">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <h1 class="text-center">My Appeals</h1>
                <p class="text-center">These are the appeals that you have submitted.<br>Refresh to update status!</p>
                <div class="col-12 col-md-8 mx-auto">
                    <div class="row-horz">
                        <%=request.getAttribute("appealRowElements")%>
                    </div>
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
                <p>Made by Hunter Han</p>
            </div>
        </div>
    </div>
</footer>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap-4.3.1.js"></script>
</body>
</html>