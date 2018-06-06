<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/webpage/taglibs.jspf" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><sitemesh:title/></title>
    <link href="${staticPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${staticPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${staticPath}/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${staticPath}/css/animate.css" rel="stylesheet">
    <link href="${staticPath}/css/style.css" rel="stylesheet">

    <link href="${staticPath}/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">

    <!-- Mainly scripts -->
    <script src="${staticPath}/js/jquery-2.1.1.js"></script>
    <script src="${staticPath}/js/bootstrap.min.js"></script>
    <script src="${staticPath}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="${staticPath}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- Custom and plugin javascript -->
    <script src="${staticPath}/js/inspinia.js"></script>
    <script src="${staticPath}/js/plugins/pace/pace.min.js"></script>

    <%--form--%>
    <link href="${staticPath}/js/plugins/jquery-validate/css/validate.css" rel="stylesheet">
    <script src="${staticPath}/js/plugins/jquery-validate/js/jquery.validate.js"></script>
    <script src="${staticPath}/js/jquery.form.js"></script>

    <style type="text/css">
        body {
            background-color: white;
        }
    </style>
    <sitemesh:head/>
</head>
<body>

<div class="row">
    <div class="col-xs-12">
        <div class="ibox-content">
            <sitemesh:body/>
        </div>
    </div>
</div>
</body>
</html>
