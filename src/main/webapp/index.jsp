<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="fns" uri="/WEB-INF/tlds/songweb-functions.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta name="decorator" content="theme"/>
    <c:set var="curUser" value="${fns:getUser()}"/>
</head>
<body>
index content
<br>
${curUser.id}
</body>
</html>