layui.define(['layer'], function (exports) {
    var popupCenterIndex, popupCenterParam;
    var $ = layui.jquery;
    var admin = {
        ajax: function (param) {
            param.url = admin.util.addURLParam(param.url,'t',(new Date()).getTime().toString());
            param.crossDomain = (true == !(document.all));
            var currentHeader = param.headers;

            param.dataType || (param.dataType = 'json');
            var successCallback = param.success;
            param.success = function (result, status, xhr) {
                // 判断登录过期和没有权限
                var jsonRs;
                if ('json' == param.dataType.toLowerCase()) {
                    jsonRs = result;
                } else {
                    jsonRs = admin.parseJSON(result);
                }
                jsonRs && (jsonRs = result);
                if (config.ajaxSuccessBefore(jsonRs, param.url) == false) {
                    return;
                }
                successCallback(result, status, xhr);
            };
            param.error = function (xhr) {
                var jo = {code: xhr.status, msg: xhr.statusText}
                if(admin.util.isNotNullOrEmpty(xhr.responseJSON)){
                    jo.code = xhr.responseJSON.code;
                    jo.msg = xhr.responseJSON.msg;
                }
                param.success(jo);
            };

            var headers = [];
            if(config.isNeedReqPerm){
                config.getAjaxHeaders(param,function(headers){
                    param.beforeSend = function (xhr) {
                        for (var f in currentHeader) {
                            xhr.setRequestHeader(f, currentHeader[f]);
                        }

                        for (var i = 0; i < headers.length; i++) {
                            xhr.setRequestHeader(headers[i].name, headers[i].value);
                        }
                        var isolationVersion = config.isolationVersion;
                        if (isolationVersion) {
                            xhr.setRequestHeader('c-s-p-version', isolationVersion);
                        }
                    };
                    $.ajax(param);
                });
            }else{
                param.beforeSend = function (xhr) {
                    for (var f in currentHeader) {
                        xhr.setRequestHeader(f, currentHeader[f]);
                    }

                    for (var i = 0; i < headers.length; i++) {
                        xhr.setRequestHeader(headers[i].name, headers[i].value);
                    }

                    var isolationVersion = config.isolationVersion;
                    if (isolationVersion) {
                        xhr.setRequestHeader('c-s-p-version', isolationVersion);
                    }
                };
                $.ajax(param);
            }
            // $.ajax(param);
        },
        error: function(sText,callback){
            layer.msg(sText, {icon: 2},callback);
        },

        getLayerData: function (index, key) {
            debugger;
            if (index == undefined) {
                index = parent.layer.getFrameIndex(window.name);
                return parent.layui.admin.getLayerData(index, key);
            } else if (index.toString().indexOf('#') == 0) {
                index = $(index).parents('.layui-layer').attr('id').substring(11);
            }
            var layerData = admin.layerData['d' + index];
            if (key) {
                return layerData ? layerData[key] : layerData;
            }
            return layerData;
        },


        popupCenter: function (param) {
            param.id = 'adminPopupC';
            popupCenterParam = param;
            popupCenterIndex = admin.open(param);
            return popupCenterIndex;
        },

        /* 封装layer.open */
        open: function (param) {
            if (!param.area) {
                param.area = (param.type == 2) ? ['360px', '300px'] : '360px';
            }
            if (!param.skin) {
                param.skin = 'layui-layer-admin';
            }
            if (!param.offset) {
                if (admin.getPageWidth() < 768) {
                    param.offset = '15px';
                } else {
                    param.offset = '70px';
                }
            }
            if (param.fixed == undefined) {
                param.fixed = false;
            }
            param.resize = param.resize != undefined ? param.resize : false;
            param.shade = param.shade != undefined ? param.shade : .1;
            var eCallBack = param.end;
            param.end = function () {
                layer.closeAll('tips');
                eCallBack && eCallBack();
            };
            if (param.url) {
                (param.type == undefined) && (param.type = 1);
                var sCallBack = param.success;
                param.success = function (layero, index) {
                    // 防止缓存
                    var dialogUrl = param.url;
                    admin.showLoading(layero, 1);
                    $(layero).children('.layui-layer-content').load(dialogUrl, function () {
                        sCallBack ? sCallBack(layero, index) : '';
                        admin.removeLoading(layero, false);
                    });
                };
            }
            var layIndex = layer.open(param);
            (param.data) && (admin.layerData['d' + layIndex] = param.data);
            return layIndex;
        },

        /* 移除加载动画 */
        removeLoading: function (elem, fade, del) {
            if (!elem) {
                elem = 'body';
            }
            if (fade == undefined) {
                fade = true;
            }
            var $loading = $(elem).children('.page-loading');
            if (del) {
                $loading.remove();
            } else {
                fade ? $loading.fadeOut() : $loading.hide();
            }
            $(elem).removeClass('page-no-scroll');
        },

        /* 获取浏览器宽度 */
        getPageWidth: function () {
            return document.documentElement.clientWidth || document.body.clientWidth;
        },

        /* 弹窗数据 */
        layerData: {},

        /* 显示加载动画 */
        showLoading: function (elem, type, opacity) {
            var size;
            if (elem != undefined && (typeof elem != 'string') && !(elem instanceof $)) {
                type = elem.type;
                opacity = elem.opacity;
                size = elem.size;
                elem = elem.elem;
            }
            (!elem) && (elem = 'body');
            (type == undefined) && (type = 1);
            (size == undefined) && (size = 'sm');
            size = ' ' + size;
            var loader = [
                '<div class="ball-loader' + size + '"><span></span><span></span><span></span><span></span></div>',
                '<div class="rubik-loader' + size + '"></div>',
                '<div class="signal-loader' + size + '"><span></span><span></span><span></span><span></span></div>'
            ];
            $(elem).addClass('page-no-scroll');  // 禁用滚动条
            var $loading = $(elem).children('.page-loading');
            if ($loading.length <= 0) {
                $(elem).append('<div class="page-loading">' + loader[type - 1] + '</div>');
                $loading = $(elem).children('.page-loading');
            }
            opacity && $loading.css('background-color', 'rgba(255,255,255,' + opacity + ')');
            $loading.show();
        },

        /* 封装ajax请求，返回数据类型为json */
        req: function (url, data, success, method,contentType) {
            debugger;
            if (method) {
                if ('put' == method.toLowerCase()) {
                    method = 'POST';
                    data._method = 'PUT';
                } else if ('delete' == method.toLowerCase()) {
                    method = 'POST';
                    data._method = 'DELETE';
                }
            }
            admin.ajax({
                url: "http://localhost:7779/adapter/" + url,
                data: data,
                type: method||'GET',
                dataType: 'json',
                contentType: typeof(contentType) === 'undefined'?"application/x-www-form-urlencoded":contentType,
                success: success
            });
        }
    }
    exports('admin', admin);
});