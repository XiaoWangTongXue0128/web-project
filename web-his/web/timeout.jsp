<%@ page pageEncoding="UTF-8" %>
<h2>还未登录或会话超时，请重新登录.....</h2>
<script>
    setTimeout(function(){
        //location.href="login.jsp" ;
        window.open('<%=request.getContextPath()%>/login.jsp',"_parent")
    },1500);
</script>