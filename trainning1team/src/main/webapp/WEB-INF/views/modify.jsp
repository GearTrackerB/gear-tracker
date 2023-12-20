<%--
  Created by IntelliJ IDEA.
  User: seongho
  Date: 2023/12/20
  Time: 12:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="./include/head.jsp" />
<html>
<head>
    <title>장비 수정</title>
</head>
<body>
<%@ include file="./include/nav.jsp"%>
<div class="main-panel">
  <div class="detail_info">
    <form onsubmit="return false" class="info-form d-flex">
<%--      <input type="hidden" id="brd_mem_id" value="${info.ID}" />--%>
      <div class="card card bg-white border">

        <div class="card-body">
          <h2 class="nav-brand">장비 수정</h2>

          <div class="form-row">
            <div class="form-group col-md-6">
              <label for="serialNo" class="essential">식별코드</label>
              <div class="input-group mb-4">
                <input ref="serialNo" type="text" class="form-control" id="serialNo" value="${info.serialNo}"/>
              </div>
            </div>
            <div class="form-group col-md-3">
              <label for="eqType" class="essential">제품 종류</label>
              <div class="input-group mb-3">
                <select id="eqType" class="form-control form-select">
                  <option value="">제품종류 선택</option>
                  <c:forEach var="type" items="${typeList}">
                    <option value="${type.type}" <c:if test="${type.type eq info.eqType}">selected</c:if>>${type.type}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group col-md-3">
              <label for="eqStatus" class="essential">상태</label>
              <div class="input-group mb-3">
                <select id="eqStatus" class="form-control form-select">
                  <option value="">상태 선택</option>
                  <c:forEach var="status" items="${statusList}">
                    <option value="${status.status}" <c:if test="${status.status eq info.eqStatus}">selected</c:if>>${status.status}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>

          <div class="form-row">
            <div class="form-group col-md">
              <label class="form_tite essential" for="eqNm">제품명</label>
              <div class="input-group mb-3">
                <input ref="eqNm" type="text" class="form-control" id="eqNm" value="${info.eqNm}" placeholder="사번을 입력해주세요." maxlength="15"/>
              </div>
            </div>
            <div class="form-group col-md">
              <label for="eqModel">모델명</label>
              <div class="input-group mb-3">
                <input ref="eqModel" type="text" class="form-control" id="eqModel" value="${info.eqModel}" placeholder="성명을 입력해주세요." maxlength="5"/>
              </div>
            </div>
            <div class="form-group col-md">
              <label for="empNo">배정자</label>
              <div class="input-group mb-3">
                <input ref="empNo" type="text" class="form-control" id="empNo" value="${info.empNo}" maxlength="20" readonly/>
              </div>
            </div>
            <div class="form-group col-md">
              <label for="regAt">최근 재고 조사 일</label>
              <div class="input-group mb-3">
                <input ref="regAt" type="text" class="form-control" id="regAt" value="${info.regAt}" maxlength="10" readonly/>
              </div>
            </div>
          </div>

          <div class="group_btn">
            <%-- 목록 페이지로 이동 버튼 시작 --%>
            <div class="float-left">
              <button type="button" class="btn btn-submit btn-secondary" id="btnList">목록</button>
            </div>
            <%-- 목록 페이지로 이동 버튼 끝 --%>
            <%-- 수정, 삭제 버튼 시작 --%>
            <div class="float-right">
              <button type="button" class="btn btn-submit btn-secondary" id="btnRegist">수정</button>
              <button type="button" class="btn btn-submit btn-secondary" id="btnDelete">삭제</button>
            </div>
            <%-- 수정, 삭제 버튼 끝 --%>
          </div>
        </div>
      </div>
    </form>
  </div>
</div>
<script src="/static/js/modify.js"></script>
</body>
</html>
