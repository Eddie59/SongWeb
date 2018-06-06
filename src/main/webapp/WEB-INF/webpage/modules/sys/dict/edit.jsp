<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="cn.modules.sys.entity.Dict" %>
<%@ page import="cn.modules.sys.entity.DictGroup" %>
<%@ page import="com.sun.tools.javac.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/webpage/taglibs.jspf" %>

<html>
<head>
    <title>添加字典</title>
    <meta name="decorator" content="form"/>
</head>
<body>
<form:form modelAttribute="data" class="form-horizontal">
    <form:hidden path="id"/>

    <div class="form-group"><label class="col-sm-2 control-label">标签</label>
        <div class="col-sm-10">
        <form:input path="label" placeholder="标签" class="form-control"/>
        </div>
    </div>

    <div class="hr-line-dashed"></div>
    <div class="form-group"><label class="col-sm-2 control-label">值</label>
        <div class="col-sm-10">
            <form:input path="value" placeholder="值" class="form-control"/>
            <span class="help-block m-b-none">A block of help text that breaks onto a new line and may extend beyond one line.</span>
        </div>
    </div>

    <div class="hr-line-dashed"></div>
    <div class="form-group"><label class="col-sm-2 control-label">所属组</label>
        <div class="col-sm-8">
            <select name="gid">
                <c:forEach items="${dictGroups}" var="item" varStatus="status">
                    <c:choose>
                        <%--待完善--%>
                        <c:when test="${item.id==''}">
                            <option value="${item.id}" selected>${item.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option value="${item.id}">${item.name}</option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="hr-line-dashed"></div>
    <div class="form-group"><label class="col-sm-2 control-label">排序</label>

        <div class="col-sm-10">
            <form:input path="sort" placeholder="排序" class="form-control"/>
        </div>
    </div>

    <div class="hr-line-dashed"></div>
    <div class="form-group"><label class="col-sm-2 control-label">备注</label>
        <div class="col-sm-10">
            <form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"  placeholder="备注"/>
        </div>
    </div>

    <div class="hr-line-dashed"></div>
    <div class="form-group">
        <div class="col-sm-4 col-sm-offset-2">
            <button class="btn btn-primary" type="submit">保存</button>
            <button class="btn btn-white" type="button">取消</button>
        </div>
    </div>
</form:form>

<script type="text/javascript">
    $(function () {
        $('form').validate({
            rules: {
                label:"required",
                value: "required",
            },
            messages: {
                label:"标签不为空",
                value: "值不为空",
            },
            errorElement: "em",
            errorClass:"has-error",
            success: function (label) {
                label.parent().addClass("has-success");
            },
            error:function () {
              alert("eoor")  ;
            },
            submitHandler: function (form) {
                //提交form
                $('form').ajaxSubmit({
                    type:"post",
                    url:"/admin/sys/dict/create",
                    success: function (data) {
//                        待完善
                        alert(data);
                        if (data.ret==0) {
                            parent.layer.msg("保存成功");
                            $(window.parent.document).find("#list").trigger("reloadGrid");
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                        }
                        else {
                            parent.layer.msg("保存失败");
                        }
                    }
                });
            },
            invalidHandler: function () {
                alert("invalidHandler")
                return false;
            }
        });
    });
</script>
</body>
</html>
