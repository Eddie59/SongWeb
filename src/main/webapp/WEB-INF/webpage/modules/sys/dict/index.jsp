<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link type="text/css" href="/static/vendors/bootstrap/css/bootstrap.css"/>
    <link type="text/css" href="/static/vendors/jqgrid/css/ui.jqgrid-bootstrap.css"/>

    <script type="text/javascript" src="/static/vendors/jquery/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/static/vendors/jqgrid/js/jquery.jqGrid.min.js"></script>



    <script type="text/javascript">
        $.jgrid.defaults.width = 780;
        $.jgrid.defaults.styleUI = 'Bootstrap';

        $(function () {
                $("#list").jqGrid({
                url: '/admin/sys/dict/list2',
                datatype: "json",
                styleUI : 'Bootstrap',
                colModel: [
                    {name: 'label', index: 'label'},
                    {name: 'value', index: 'value'},
                    {name: 'sort', index: 'sort'},
                    {name: 'remarks', index: 'remarks'}
                ],
                viewrecords: true,
                height: 250,
                rowNum: 10,
                pager: "#pager",
                    loadComplete:function (xhr) {
                      var test="abc";
                    }
            });

        });
    </script>
</head>
<body>
<div id="wrapper"></div>
<table id="list"></table>
 <div id="pager"></div>
</body>
</html>
