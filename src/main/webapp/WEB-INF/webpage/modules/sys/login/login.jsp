<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/webpage/taglibs.jspf" %>

<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>INSPINIA | Login</title>

    <link href="${staticPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${staticPath}/font-awesome/css/font-awesome.css" rel="stylesheet">

    <link href="${staticPath}/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/css/style.css" rel="stylesheet">

</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen animated fadeInDown">
    <div>
        <div>

            <h1 class="logo-name">IN+</h1>

        </div>
        <h3>Welcome to IN+</h3>
        <p>Perfectly designed and precisely prepared admin theme with over 50 pages with extra new web app views.
            <!--Continually expanded and constantly improved Inspinia Admin Them (IN+)-->
        </p>
        <p>Login in. To see it in action.</p>
        <form class="m-t" role="form" method="post">
            <div class="form-group">
                <input name="username" type="text" class="form-control" placeholder="Username" required="">
            </div>
            <div class="form-group">
                <input name="password" type="password" class="form-control" placeholder="Password" required="">
            </div>

            <div class="form-group">
                <input name="rememberMe" type="checkbox" class="form-control"> remember me
            </div>

            <button type="submit" class="btn btn-primary block full-width m-b">Login</button>

            <a href="#">
                <small>Forgot password?</small>
            </a>
            <p class="text-muted text-center">
                <small>Do not have an account?</small>
            </p>
            <a class="btn btn-sm btn-white btn-block" href="register.html">Create an account</a>
        </form>
        <p class="m-t">
            <small>Inspinia we app framework base on Bootstrap 3 &copy; 2014</small>
        </p>
    </div>
</div>

<!-- Mainly scripts -->
<script src="${staticPath}/js/jquery-2.1.1.js"></script>
<script src="${staticPath}/js/bootstrap.min.js"></script>

</body>

</html>
