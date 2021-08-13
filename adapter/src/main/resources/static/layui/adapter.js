layui.config({
    base:'/adapter/layui/js/'
})
layui.use(['form', 'table', 'admin'], function () {
    var table = layui.table;
    var layer = layui.layer;
    var admin = layui.admin;
    var form = layui.form;
    var $ = layui.jquery;
    let serviceNames;

    // 页面初始化加载
    $(function () {
        debugger;
        form.render('select');
        loadServiceName()
        renderTable();
    });

    // 渲染表格
    function renderTable(params) {
        debugger;
        table.render({
            elem: '#tDataserviceConfig-table',
            skin: "line",
            url: '/adapter/tDataserviceConfig/selectAll',
            parseData: function (res) {
                return {
                    "code": res.code,
                    "msg": res.msg,
                    "count": res.data.total,
                    "data": res.data.records
                };
            },
            request: {
                pageName: 'current',
                limitName: 'size'
            },
            where: {

            },
            page: {
                layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
            },
            cellMinWidth: 120,
            cols: [[
                // {type: 'numbers'},
                {type: 'checkbox'},
                {sort: false, field: 'serviceName', title: '服务名称', templet: data=> {
                        return serviceNames[data.serviceName]
                    }},
                {sort: false, field: 'type', title: '服务类型', templet: data => {
                        return data.type === '1' ? '阿里云' : '华为云'
                    }},
                {sort: false, field: 'createTime', title: '创建时间'},
                {sort: false, field: 'updateTime', title: '修改时间'},
                {align: 'center', toolbar: '#tDataserviceConfig-tablebar', title: '操作', width: 250, fixed: 'right'}
            ]]
        });
    }

    // 列表操作列按钮事件
    table.on('tool(tDataserviceConfig-table)', function (row) {
        var data = row.data;
        var layEvent = row.event;
        if (layEvent === 'edit') {
            showForm({
                row: data,
                serviceNames: serviceNames,
                action: 'edit'
            }, "修改");
        } else if (layEvent === 'del') {
            del([data.id]);
        }
    });

    // 查询按钮事件
    form.on('submit(tDataserviceConfig-search-btn)', function () {
        table.reload('tDataserviceConfig-table', searchCondition());
        return false;
    });

    // 清除查询
    $('#tDataserviceConfig-search-reset').click(function () {
        table.reload('tDataserviceConfig-table', {where: null});
    });

    // 添加数据按钮事件
    $('#tDataserviceConfig-btn-add').click(function () {
        const data = layui.table.cache["tDataserviceConfig-table"]
        showForm({tableData: data, serviceNames: serviceNames, action: 'add'}, "新增");
    });

    // 批量删除数据按钮事件
    $('#tDataserviceConfig-btn-del').click(function () {
        var checkStatus = table.checkStatus('tDataserviceConfig-table');
        var data = checkStatus.data;
        if (data.length > 0) {
            var ids = data.map(function (item) {
                return item.id
            })
            del(ids);
        } else {
            admin.error('请选择要删除的数据行')
        }
    });

    // 弹出表单Form
    function showForm(data, title) {
        layer.open({
            type: 2 //此处以iframe举例
            ,title: title
            ,area: ['600px', '300px']
            ,shade: 0
            ,maxmin: true
            ,content: '/adapter/dataserviceConfig/form.html'
            ,data: data
            ,btn2: function(){
                layer.closeAll();
            }

            ,zIndex: layer.zIndex //重点1
            ,success: function(layero){
                layer.setTop(layero); //重点2
            }
        });


        // admin.popupCenter({
        //     title: title,
        //     area: ['600px', '300px'],
        //     data: data,
        //     url: 'dataserviceConfig/form.html'
        // });
    }

    // 构造查询条件
    function searchCondition() {
        var condition = {
            serviceName: $("#service-name").val(),
            type: null,
        }
        return {where: condition};
    }

    // 根据传入的 id 列表删除数据
    function del(ids) {
        layer.confirm('确定要删除吗？', function (i) {
            layer.close(i);
            layer.load(2);
            admin.req('adapter/tDataserviceConfig/del', {ids: ids.join(',')}, function (response) {
                layer.closeAll('loading');
                if (response.code === 0) {
                    admin.success(response.msg);
                    table.reload('tDataserviceConfig-table');
                } else {
                    admin.error(response.msg);
                }
            }, 'POST');
        });
    }

    function loadServiceName() {
        var url = '/adapter/tDataserviceConfig/getServiceNames';
        debugger;
        $.ajax({
            url: url,
            contentType: "application/json",
            dataType: 'json',
            type:"get",
            success: function (res) {
                //失败，返回错误信息
                debugger;
                if (res.code === 0) {
                    debugger;
                    serviceNames = res.data
                    for(let key in res.data) {
                        const option = `<option value="${key}">${res.data[key]}</option>`
                        $("#service-name").append(option)
                    }
                    form.render('select')
                } else {
                    admin.error(res.msg);
                }
            }
        })
    }

});
