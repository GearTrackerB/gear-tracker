<%--
  Created by IntelliJ IDEA.
  User: seongho
  Date: 2023/12/20
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="./include/head.jsp" />
<html>
<head>
    <title>장비 등록</title>
</head>
<body>
<div class="main-panel">
    <div class="detail_info">
        <form onsubmit="return false" class="info-form d-flex">
            <div class="card card bg-white border">
                <div class="card-body">
                    <h2 class="nav-brand">장비 등록</h2>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="serialNo" class="essential">식별코드</label>
                            <div class="input-group mb-3">
                                <input ref="serialNo" type="text" class="form-control" id="serialNo" placeholder="식별코드를 입력해주세요." maxlength="20"/>
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="eqType" class="essential">제품종류</label>
                            <div class="input-group mb-3">
                                <select id="eqType" class="form-control form-select">
                                    <option value="">제품종류 선택</option>
                                    <c:forEach var="type" items="${typeList}">
                                        <option value="${type.type}">${type.type}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group col-md-3">
                            <label for="eqNm" class="essential">제품명</label>
                            <div class="input-group mb-3">
                                <input ref="position_level" type="text" class="form-control" id="eqNm" placeholder="제품명을 입력해주세요." maxlength="45"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md">
                            <label class="form_tite essential" for="eqModel" >모델명</label>
                            <div class="input-group mb-3">
                                <input ref="eqModel" type="text" class="form-control" id="eqModel" placeholder="모델명을 입력해주세요." maxlength="45"/>
                            </div>
                        </div>
                        <div class="form-group col-md">
                            <label class="form_tite " for="eqMaker" >제조사</label>
                            <div class="input-group mb-3">
                                <input ref="eqMaker" type="text" class="form-control" id="eqMaker" placeholder="제조사를 입력해주세요." maxlength="20"/>
                            </div>
                        </div>
                        <div class="form-group col-md">
                            <label for="empNo" class="essential">배정자</label>
                            <div class="input-group mb-3">
                                <select id="empNo" class="form-control form-select">
                                    <option value="">배정자 선택</option>
                                    <c:forEach var="emp" items="${empList}">
                                        <option value="${emp.empNo}">${emp.empNo}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <%-- 버튼 시작 --%>
                    <div class="float-right group_btn">
                        <!--버튼 클릭 시 write 실행-->
                        <button type="button" class="btn btn-submit btn-secondary" id="btnRegist">등록</button>
                        <!--버튼 클릭 시 cancel 실행-->
                        <button type="button" class="btn btn-submit ml-1 btn-secondary" id="btnCancel">취소</button>
                    </div>
                    <%-- 버튼 끝 --%>
                </div>
            </div>
        </form>
    </div>
</div>
<script src="/static/js/regist.js"></script>
</body>
</html>
