<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<jsp:include page="./include/head.jsp" />
<html>
<head>
    <title>관리자 로그인</title>

</head>
<body>
    <%@ include file="./include/nav.jsp"%>

    <%--  로그인 form 시작  --%>
    <div class="login-content d-flex align-items-center flex-column">
            <div>
                <input maxlength="10" type="text" class="form-control login-input" id="loginId" placeholder="아이디를 입력해주세요."/>
            </div>

            <div>
                <input maxlength="20" type="password" class="form-control login-input" id="loginPw" placeholder="비밀번호를 입력해주세요."/>
            </div>
        <div class="d-flex justify-content-center">
            <button type="button" class="btn login-btn btn-secondary w100">로그인</button>
        </div>
    </div>
    <%--  로그인 form 끝  --%>

    <%--  로그인 JS 시작 --%>
    <script src="/static/js/login.js"></script>
    <%--  로그인 JS 끝 --%>
</body>
</html>
