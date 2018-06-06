<%@ page import="cn.modules.sys.entity.Dict" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript" src="/static/vendors/jquery/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/vendors/jqgrid/js/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="/static/vendors/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="/static/vendors/layer/layer.min.js"></script>

    <link rel="stylesheet" type="text/css" href="/static/vendors/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/static/vendors/jqgrid/css/ui.jqgrid-bootstrap-ui.css"/>

    <script type="text/javascript" src="/static/common/js/curdtools_jqgrid.js"></script>
    <script type="text/javascript" src="/static/vendors/Validform_v5.3.2/js/Validform_v5.3.2.js"></script>

    <%--<script type="text/javascript">
        //初始化表单
        var validateForm;
        var callFunc;

        //回调函数，在编辑和保存动作时，供openDialog调用提交表单。
        function doSubmit(func) {
            callFunc = func;
            validateForm.ajaxPost();
        }

        $(document).ready(function () {

            validateForm = $("#dictForm").Validform(
                //Validform需要的配置信息
                {
                    tiptype: function (msg, o, cssctl) {
                        if (!o.obj.is("form")) {
                            var objtip = o.obj.siblings(".Validform_checktip");
                            cssctl(objtip, o.type);
                            objtip.text(msg);
                        }
                    },
                    beforeSubmit: function (curform) {
                        try {
                            var beforeFunc = beforeSubmit;
                            if (beforeFunc && typeof(beforeFunc) == "function") {
                                return beforeFunc(curform);
                            }
                        } catch (err) {

                        }
                        return true;
                    },
                    callback: function (result) {
                        alert(result);
                        if (result.ret == 0) {
                            alert(result.ret);
                            layer.alert(result.msg, {icon: 0, title: '提示'});
                            //运行回调
                            callFunc();
                        } else {
                            layer.alert(result.msg, {icon: 0, title: '警告'});
                        }
                    }
                });
        });
    </script>--%>
</head>
<body>
<%
    Dict dict = (Dict) request.getAttribute("data");
%>

<body class="white-bg" formid="dictForm">
<form method="post" action="/admin/sys/dict/create" class="form-horizontal">

    <input type="hidden" name="id" value="<%=dict.getId()%>"/>

    <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
        <tbody>
        <tr>
            <td class="width-15 active text-right">
                <label><font color="red">*</font>标签:</label>
            </td>
            <td class="width-35">
                <input type="text" name="label" value="<%=dict.getLabel()%>"/>
                <label class="Validform_checktip"></label>
            </td>
            <td class="width-15 active text-right"><label><font color="red">*</font>值:</label></td>
            <td class="width-35">
                <input type="text" name="value" value="<%=dict.getValue()%>"/>
                <label class="Validform_checktip"></label>
            </td>
        </tr>
        <tr>
            <td class="width-15 active text-right">
                <label><font color="red">*</font>排序:</label>
            </td>
            <td class="width-35">
                <input type="text" name="sort" value="<%=dict.getSort()%>"/>
                <label class="Validform_checktip"></label>
            </td>
            <td class="width-15 active text-right"><label><font color="red">*</font>备注:</label></td>
            <td class="width-35" colspan="3">
                <textarea name="" content="<%=dict.getRemarks()%>" cols="30" rows="10"></textarea>
            </td>
        </tr>
        </tbody>
    </table>
    <%--<input type="submit" name="submit" value="提交"/>--%>
</form>
</body>

</body>
</html>
