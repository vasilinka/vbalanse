<!--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
  <meta charset="utf-8">
  <title>Login Page</title>
  <style>
    .error {
      padding: 15px;
      margin-bottom: 20px;
      border: 1px solid transparent;
      border-radius: 4px;
      color: #a94442;
      background-color: #f2dede;
      border-color: #ebccd1;
    }

    .msg {
      padding: 15px;
      margin-bottom: 20px;
      border: 1px solid transparent;
      border-radius: 4px;
      color: #31708f;
      background-color: #d9edf7;
      border-color: #bce8f1;
    }

    #login-box {
      width: 300px;
      padding: 20px;
      margin: 100px auto;
      background: #fff;
      -webkit-border-radius: 2px;
      -moz-border-radius: 2px;
      border: 1px solid #000;
    }
  </style>
</head>
<body onload='document.loginForm.username.focus();'>-->

<h1>Это я Security Custom Login Form (XML)</h1>

<ul class="nav navbar-nav navbar-right">
  <li class="dropdown" ng-show="authenticated">
    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
       aria-expanded="false">{{user.name}}
      <span class="caret"></span></a>
    <ul class="dropdown-menu" role="menu">
      <li><a href="#/profile">Профиль</a></li>
      <li><a href="#">Мои статьи</a></li>
      <li><a href="#">Мои сообщения</a></li>
      <li class="divider"></li>
      <li><a href="j_spring_security_logout">Выйти</a></li>
    </ul>
  </li>
  <li class="dropdown" ng-show="!(authenticated)">
    <a ng-show="!(authenticated)" href="#" class="dropdown-toggle" data-toggle="dropdown"
       role="button"
       aria-expanded="false">"Логин"
      <span class="caret"></span></a>
    <ul class="dropdown-menu" role="menu">
      <li><a href="auth.html">"Логин"</a></li>
      <li><a href="auth.html#/register">Регистрация</a></li>
    </ul>
  </li>
</ul><div id="login-box">

  <h3>Login with Username and Password</h3>

  <c:if test="${not empty error}">
    <div class="error">${error}</div>
  </c:if>
  <c:if test="${not empty msg}">
    <div class="msg">${msg}</div>
  </c:if>

  <form name='loginForm'
        action="<c:url value='j_spring_security_check' />" method='POST'>

    <table>
      <tr>
        <td>User:</td>
        <td><input type='text' name='username' value=''></td>
      </tr>
      <tr>
        <td>Password:</td>
        <td><input type='password' name='password' /></td>
      </tr>
      <tr>
        <td colspan='2'><input name="submit" type="submit"
                               value="submit" /></td>
      </tr>
    </table>

    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}" />

  </form>
</div>

<!--</body>
</html>-->