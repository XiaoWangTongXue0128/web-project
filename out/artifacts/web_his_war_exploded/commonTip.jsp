<%@ page pageEncoding="utf-8" %>
<script>
    alert('${param.msg}');
    location.href='<%=request.getContextPath()%>${param.url}'
</script>