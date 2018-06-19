<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/webpage/taglibs.jspf" %>



<c:forEach items="${menu}" var="menu">
    <c:if test="${menu.parentId==null&&menu.isshow eq '1'}">
        <li <c:if test="fn:contains()"></c:if>>

        </li>
    </c:if>
</c:forEach>