<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/webpage/taglibs.jspf" %>

<c:set var="menus" value="${fns:getMenuList()}"/>
<c:set var="currentMenu" value="${fns:getCurrentMenu()}"/>
<c:set var="curParentIds" value="${currentMenu.parentIds}"/>

<c:forEach items="${menus}" var="menu">
    <c:if test="${menu.parentId==null&&menu.isshow eq '1'}">
        <li <c:if test="${fn:contains(curParentIds, menu.id)==true || menu.id==currentMenu.id}"> class="active" </c:if>>
        <c:choose>
            <c:when test="${menu.hasChildren}">
                <a href="#">
                    <i class="fa ${menu.menuIcon}"></i>
                    <span class="nav-label">${menu.name}</span>
                    <span class="fa arrow"/>
                </a>
                <ul class="nav nav-second-level collapse <c:if test="${fn:contains(curParentIds,menu.id)==true||currentMenu.id==menu.id}">in</c:if> ">
                    <c:forEach items="${menus}" var="secondMenu">
                        <c:if test="${secondMenu.parentId==menu.id&&secondMenu.isshow eq '1'}">
                            <li <c:if test="${secondMenu.id==currentMenu.id}">class="active"</c:if>>
                                <a href="${adminPath}/${secondMenu.url}">${secondMenu.name}</a>
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <li>
                    <a href="${adminPath}/${menu.url}">
                        <i class="fa ${menu.menuIcon}"></i>
                        <span class="nav-label">${menu.name}</span>
                    </a>
                </li>
            </c:otherwise>
        </c:choose>
        </li>
    </c:if>
</c:forEach>

<li class="landing_link">
    <a target="_blank" href="landing.html"><i class="fa fa-star"></i> <span class="nav-label">Landing Page</span>
        <span class="label label-warning pull-right">NEW</span></a>
</li>
<li class="special_link">
    <a href="package.html"><i class="fa fa-database"></i> <span class="nav-label">Package</span></a>
</li>