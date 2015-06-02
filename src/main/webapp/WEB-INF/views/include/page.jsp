<%@ page language="java" pageEncoding="utf-8" %>
<script src="${ctx}/static/bootstrap/2.3.2/js/bootstrap-paginator.min.js" type="text/javascript"></script>
<script type='text/javascript'>
    var currentPage = '${currentPage}'
    var reg = new RegExp("page=[0-9]+&");
    var currentPage = currentPage.replace(reg, "");

    var options = {
        currentPage: '${pageInfo.number+1}',
        totalPages: '${pageInfo.totalPages}',
        size: 'large',
        alignment: 'center',
        pageUrl: function (type, page, current) {


            return '${ctx}' + currentPage + "&page=" + (page - 1);

        },
        onPageChanged: function (e, oldPage, newPage) {

        }
    }
    $('#page').bootstrapPaginator(options);
    $('#alert-content').text("总共${pageInfo.totalPages}页 ${pageInfo.totalElements}条  每页${pageInfo.size}条");
    function orders(field) {

        var currentPage = '${currentPage}';
        if (currentPage.indexOf("order=") > -1) {
            var reg = new RegExp("order=(.)+($|&|\\s)");
            currentPage = currentPage.replace(reg, "order=" + field);
        } else {
            currentPage = currentPage + "&order=" + field;
        }

        location.href = '${ctx}' + currentPage;
    }
    function setSize(size) {

        var currentPage = '${currentPage}';
        if (currentPage.indexOf("size=") > -1) {
            var reg = new RegExp("size=[0-9]+($|&|\\s)");
            currentPage = currentPage.replace(reg, "");
        }
        currentPage = currentPage + "&size=" + size;

        location.href = '${ctx}' + currentPage;
    }
    $(function () {
        $("#search_createTime__GT_D").datepicker();
        $("#search_createTime__LT_D").datepicker();
    });
    function del(id,path) {
        bootbox.confirm("是否确认删除?", function (result) {
            if (result) {
                location.href = '${pageContext.request.contextPath}' + path + id;
            }
        });
    }

</script>