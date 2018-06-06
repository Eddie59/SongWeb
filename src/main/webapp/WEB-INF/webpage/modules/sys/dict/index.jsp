<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/webpage/taglibs.jspf" %>

<html>
<head>
    <title>dfsdf</title>
    <meta name="decorator" content="theme"/>

    <link href="${staticPath}/css/plugins/jqGrid/ui.jqgrid.css" rel="stylesheet">

</head>
<body>


<div class="ibox ">
    <div class="ibox-title">
        <!-- 查询条件 -->
        <div class="row">
            <div id="listQuery" class="col-sm-12">
                <div class="form-inline">
                    <div class="form-group">
                        <label class="control-label">标签：</label>
                        <input type="text" placeholder="标签" name="label" condition="like" class="form-control" value="abc">
                    </div>
                    <div class="form-group">
                        <label class="control-label">值：</label>
                        <input type="text" placeholder="值" name="value" condition="like" class="form-control" value="ddd">
                    </div>
                </div>
                <br>
            </div>
        </div>
        <!-- 工具栏 -->
        <div class="row">
            <div class="col-sm-12">
                <div class="pull-left">
                    <button class="btn btn-sm btn-primary"
                            onclick="create('添加字典','/admin/sys/dict/dictcreate','650px','700px')"><i
                            class="fa fa-plus"></i> 添加
                    </button>
                    <button class="btn btn-sm btn-success"
                            onclick="update('修改','/admin/sys/dict/{id}/dictupdate','','650px','700px')">
                        <i class="fa fa-file-text-o"></i> 修改
                    </button>
                    <button class="btn btn-sm btn-danger"
                            onclick="batchDel('/admin/sys/dict/batch/delete')"><i
                            class="fa fa-trash-o"></i> 删除
                    </button>
                </div>
                <div class="pull-right">
                    <button class="btn btn-sm btn-info" onclick="search()"><i class="fa fa-search"></i>
                        搜索
                    </button>
                    <button class="btn btn-sm btn-warning" onclick="reset()"><i
                            class="fa fa-refresh"></i> 重置
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="ibox-content">
        <div class="jqGrid_wrapper">
            <table id="list"></table>
            <div id="pager"></div>
        </div>
    </div>
</div>

<!-- jqGrid -->
<script src="${staticPath}/js/plugins/jqGrid/i18n/grid.locale-cn.js"></script>
<script src="${staticPath}/js/plugins/jqGrid/jquery.jqGrid.min.js"></script>
<script type="text/javascript">

    function colFormat(value, options, row) {
        try {
            if (!row.id) {
                return '';
            }
            var href = "";
            /*  href += "<a href=\"#\" class=\"btn btn-xs btn-primary\" class=\"btn btn-xs \"";
              href += "onclick=\"create('添加字典','/admin/sys/dict/dictcreate','650px','700px')\"";
              href += "><i class=\"fa fa-plus\"></i>&nbsp添加字典</a>&nbsp&nbsp";*/

            href += "<a href=\"#\" class=\"btn btn-xs btn-primary\" class=\"btn btn-xs \"";
            href += "onclick=\"update('修改字典','/admin/sys/dict/{id}/dictupdate','" + row.id + "','650px','700px')\"";
            href += "><i class=\"fa fa-plus\"></i>&nbsp修改</a>&nbsp&nbsp";

            href += "<a href=\"#\" class=\"btn btn-xs btn-danger\" class=\"btn btn-xs \"";
            href += "onclick=\"singleDel('/admin/sys/dict/{id}/delete','" + row.id + "')\"";
            href += "><i class=\"fa fa-trash\"></i>&nbsp删除</a>&nbsp&nbsp";
            return href;
        } catch (err) {
        }
        return value;
    }

    function colUnFormat(value, options, cellObject) {
        try {
            var html = cellObject.innerHTML;
            var cellValue = $(html).attr("originalValue");
            return cellValue;
        } catch (err) {
        }
        return value;
    }

    var data={};
    data['gridtype']="jqgrid";

    $("#listQuery input[type='text']").each(function () {
        var name=$(this).attr("name");
        var condition=$(this).attr("condition");
        var val=$(this).val();

        var key="query."+name+"||"+condition;
        data[key]=val;
    });

    var setting = {
        url: '/admin/sys/dict/list2',
        datatype: "json",
        postData: data,//给后台传递参数
        prmNames: {//请求参数格式预处理
            page: "page.pn",//传递的参数名由page改为page.pn
            rows: "page.size",
            sort: "sort",
            order: "order"
        },
        styleUI: 'Bootstrap',
//            caption: "字典管理",
        colNames: ['标签', '值', '排序', '备注'],
        colModel: [
            {
                label: '标签',
                name: 'label',
                width: 100,
                align: 'left',
                sortable: true,
                checkbox: false,
                formatter: colFormat,//对列数据格式化
                unformat: colUnFormat
            },
            {label: '值', name: 'value', index: 'value', width: 100},
            {label: '排序', name: 'sort', index: 'sort', width: 100},
            {label: '备注', name: 'remarks', index: 'remarks', width: 100}
        ],
        //width:"100%",//如果未设置，则宽度应为所有列宽的之和；如果设置了宽度，则每列的宽度将会根据shrinkToFit选项的设置，进行设置
        shrinkToFit: true,//。默认值为true。如果shrinkToFit为true且设置了width值，则每列宽度会根据width 成比例缩放；如果shrinkToFit为false且设置了width值，则每列的宽度不会成比例缩放，而是保持原有设置，而Grid将会有水平滚动 条。
        autowidth: true,//默认值为false。如果设为true，则Grid的宽度会根据父容器的宽度自动重算。重算仅 发生在Grid初始化的阶段
        height: 600,
        rowNum: 20,
        rowList: [20, 30, 50],
        pager: "#pager",
        viewrecords: true,
        jsonReader: {
            root: "records",    // json中代表实际模型数据的入口
            page: "current",    // json中代表当前页码的数据
            total: "pages",    // json中代表页码总数的数据
            records: "total", // json中代表数据行总数的数据
            repeatitems: true, //如果设为false，则jqGrid在解析json时，会根据name来搜索对应的数据元素（即可以json中元素可以不按顺序）；而所使用的name是来自于colModel中的name设定。
            cell: "cell",
            id: "id",//每条数据的id，比如checkbox的id就是这个值
            userdata: "userdata",
        },
        multiselect: true,
        multiSort: false,
        sortable: true,
        sortname: "id",
        sortorder: "asc",
        loadComplete: function (xhr) {
            //隐藏滚动条
            $("#list").closest(".ui-jqgrid-bdiv").css({'overflow-y': 'auto', 'overflow-x': 'hidden'});
        },
        onSelectRow: function (rowid, status) {
        },
        onSelectAll: function (aRowids, status) {
        },
    };

    $(function () {
        $("#list").jqGrid(setting);
        jQuery("#list").jqGrid('navGrid', '#pager2', {edit: true, add: true, del: true});
    });

    function create(title, url, width, height) {
        openUrl(title, url, width, height)
    }

    function openUrl(title, url, width, height) {
        layer.open({
            type: 2,
            title: title,
            shadeClose: true,
            shade: 0.8,
            area: [width, height],
            content: url,
        });
    }

    function update(title, url, id, width, height) {
        if (id == "") {
            //获取checkbox已选择id，返回数组
            var ids = $("#list").jqGrid("getGridParam", "selarrrow");
            if (ids.length > 1 || ids.length == 0) {
                layer.msg("请选择一条数据");
                return;
            }
            id = ids[0];
        }
        url = url.replace("{id}", id);
        openUrl(title, url, width, height);
    }

    function batchDel(url) {
        var ids = $("#list").jqGrid("getGridParam", "selarrrow");
        if (ids.length == 0) {
            layer.msg("请选择要删除的数据");
            return;
        }
        layer.confirm('确定要删除吗？', {
            btn: ['确定', '取消']
        }, function () {
            $.post(url,
                {ids: ids.toString()},
                function (data) {
                    if (data.ret == "0") {
                        layer.msg("删除成功");
                        $("#list").trigger("reloadGrid");
                    }
                    else {
                        layer.msg("删除失败");
                    }
                }, "json");
        }, function () {
        });
    }

    function singleDel(url, id) {
        if (id == '') {
            layer.msg("要删除的数据为空");
            return;
        }

        url = url.replace("{id}", id);

        layer.confirm('确定要删除吗？', {
            btn: ['确定', '取消']
        }, function () {
            $.post(url, function (data) {
                if (data.ret == "0") {
                    layer.msg("删除成功");
                    $("#list").trigger("reloadGrid");
                }
                else {
                    layer.msg("删除失败");
                }
            }, "json");
        }, function () {
        });
    }
    function search() {
        $("#listQuery input[type='text']").each(function () {
            var name=$(this).attr("name");
            var condition=$(this).attr("condition");
            var val=$(this).val();

            var key="query."+name+"||"+condition;
            data[key]=val;
        });
        //jQuery("#grid_id").setGridParam(setting).trigger("reloadGrid");
        $("#list").jqGrid(setting);
        $("#list").trigger("reloadGrid");
    }

    //表格宽度自适应
    $(window).bind('resize', function () {
        var width = $('.jqGrid_wrapper').width();
        $('#list').setGridWidth(width);
        $('#table_list_2').setGridWidth(width);
    });
</script>
</body>
</html>
