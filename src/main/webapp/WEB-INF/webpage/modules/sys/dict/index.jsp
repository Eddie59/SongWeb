<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <script type="text/javascript" src="/static/vendors/jquery/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/vendors/jqgrid/js/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="/static/vendors/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="/static/common/js/curdtools_jqgrid.js"></script>
    <script type="text/javascript" src="/static/vendors/layer/layer.min.js"></script>
    <script type="text/javascript" src="/static/vendors/sweetalert/sweetalert.min.js"></script>

    <link rel="stylesheet" type="text/css" href="/static/vendors/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/static/vendors/jqgrid/css/ui.jqgrid-bootstrap-ui.css"/>


    <script type="text/javascript">

        function colFormat(value, options, row){
            try{
                if(!row.id) {
                    return '';
                }
                var href="";
                href +="<a href=\"#\" class=\"btn btn-xs btn-primary\" class=\"btn btn-xs \"";
                href +="onclick=\"rowDialogDetailRefresh('添加字典','/admin/sys/dict/create?gid="+row.id+"','list','"+row.id+"','1000px','500px')\"";
                href +="><i class=\"fa fa-plus\"></i>&nbsp添加字典</a>&nbsp&nbsp";

                href +="<a href=\"#\" class=\"btn btn-xs btn-primary\" class=\"btn btn-xs \"";
                href +="onclick=\"rowDialogDetailRefresh('修改字典','/admin/sys/dict/"+row.id+"/update','"+row.id+"','1000px','500px')\"";
                href +="><i class=\"fa fa-plus\"></i>&nbsp修改字典</a>&nbsp&nbsp";

                href +="<a href=\"#\" class=\"btn btn-xs btn-danger\" class=\"btn btn-xs \"";
                href +="onclick=\"deleteRowData('删除','/admin/sys/dict/{id}/delete','"+row.id+"','list')\"";
                href +="><i class=\"fa fa-trash\"></i>&nbsp删除</a>&nbsp&nbsp";
                return href;
            }catch(err){}
            return value;
        }
        function colUnFormat(value, options, cellObject) {
            try{
                var html=cellObject.innerHTML;
                var cellValue= $(html).attr("originalValue");
                return cellValue;
            }catch(err){}
            return value;
        }

        $(function () {
            $("#list").jqGrid({
                url: '/admin/sys/dict/list2',
                datatype: "json",
                postData: {test1: "test1", test2: "test2"},//给后台传递参数
                prmNames: {//请求参数格式预处理
                    page: "page.pn",//传递的参数名由page改为page.pn
                    rows: "page.size",
                    sort: "sort",
                    order: "order"
                },
                styleUI: 'Bootstrap',
                caption: "JSON Example",
                colNames: ['标签', '值', '排序', '备注'],
                colModel: [
                    {
                        label: '标签',
                        name: 'label',
                        width: 100,
                        align: 'left',
                        sortable: true,
                        checkbox: true,
                        formatter: colFormat,//对列数据格式化
                        unformat: colUnFormat
                    },
                    {label: '值', name: 'value', index: 'value'},
                    {label: '排序', name: 'sort', index: 'sort'},
                    {label: '备注', name: 'remarks', index: 'remarks'}
                ],
                height: 250,
                rowNum: 5,
                rowList: [5, 10, 20, 30],
                pager: "#pager",
                viewrecords: true,
                jsonReader: {
                    root: "records",    // json中代表实际模型数据的入口
                    page: "current",    // json中代表当前页码的数据
                    total: "pages",    // json中代表页码总数的数据
                    records: "total", // json中代表数据行总数的数据
                    repeatitems: true, //如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
                    cell: "cell",
                    id: "id",
                    userdata: "userdata",
                },
                multiSort: false,
                sortable: true,
                sortname: "id",
                sortorder: "asc",
            });
            jQuery("#list").jqGrid('navGrid', '#pager2', {edit: true, add: true, del: true});
        });
    </script>

</head>
<body>
<div id="wrapper"></div>
<table id="list"></table>
<div id="pager"></div>
</body>
</html>
