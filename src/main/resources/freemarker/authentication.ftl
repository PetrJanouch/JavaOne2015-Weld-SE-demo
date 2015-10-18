<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Authenticate!</title>

    <!-- Bootstrap -->
    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">

</head>
<body style="margin-left: 10px; width: 30%">
<h1>Log in!</h1>
<h2>Available users</h2>
<table class="table table-condensed" >
    <thead>
        <tr>
            <th>Username</th>
            <th>Password</th>
        </tr>
    </thead>
    <tbody>
    <#list users?keys as username>
    <tr>
        <th>${username}</th>
        <th>${users[username]}</th>
    </tr>
    </#list>
    </tbody>
</table>

<form method="post">
    <#if errorMessage??>
      <div class="alert alert-danger" role="alert">
          ${errorMessage}
      </div>
    </#if>
  <input type="text" name="username" style="float: left; clear:both; margin: 5px">
    <input type="password" name="password" style="float: left; clear:both; margin: 5px">
    <input class="btn btn-default" type="submit" value="Log in" style="float: left; clear:both; margin: 5px">
</form>


<script src="/jquery/jquery-2.1.4.min.js"></script>
<script src="/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>