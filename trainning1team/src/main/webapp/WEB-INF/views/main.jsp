<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="./include/head.jsp" />
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="./include/nav.jsp"%>
<div class="main-panel">
    <div class="main-content member-list">
        <div class="nav-group">
            <h2 class="nav-left">장비 관리</h2>
            <div class="nav-right">
                <button class="btn btn-submit btn-regist btn-secondary">등록</button>
            </div>
        </div>

        <%-- 엑셀 영역 시작--%>
            <div class="d-flex justify-content-center">
                <div class="table-responsive">
                    <form id="addFromFileFrm" enctype="multipart/form-data">
                        <table class="table">
                            <colgroup>
                                <col class="w76" />
                                <col class="w12" />
                                <col class="w12" />
                            </colgroup>
                            <tbody>
                            <tr class="tac bf5f5f5">
                                <td class="filebox">
                                    <div class="d-flex pd10">
                                        <label for="file">엑셀파일선택</label>
                                        <div class="file-list-group">
                                            <div class="file-list">
                                                <div id="fileList">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <input name="newFiles" type="file" id="file" class="input-file" />
                                </td>
                                <td>
                                    <button type="button" class="btn btn-submit ml-2 btn-secondary btn-reg w80">업로드</button>
                                </td>
                                <td>
                                    <button type="button" class="btn ml-2 btn-secondary btn-download w80">양식받기</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        <%-- 엑셀 영역 끝 --%>

        <div class="d-flex search_group">
            <c:choose>
                <c:when test="${sessionScope.empInfo.AUTH_CD eq 'ROL004'}">
                    <input type="hidden" id="comp_cd" value="${sessionScope.empInfo.COMP_CD}" />
                    <input id="search_emp_no" type="hidden" value="${sessionScope.empInfo.EMP_NO}" />
                </c:when>
                <c:otherwise>
                    <div class="d-flex left_group">
                        <c:choose>
                            <c:when test="${sessionScope.empInfo.AUTH_CD eq 'ROL001'}">
                                <select id="comp_cd" class="form-control form-select">
                                    <option value="">계열사 선택</option>
                                    <c:forEach var="item" items="${compList}">
                                        <option value="${item.COMM_CD}">${item.CODE_NM}</option>
                                    </c:forEach>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <input type="hidden" id="comp_cd" value="${sessionScope.empInfo.COMP_CD}" />
                            </c:otherwise>
                        </c:choose>
                        <input class="ml-2 form-control searchInput" id="search_emp_no" type="text" placeholder="사번 검색" />
                        <input class="ml-2 form-control searchInput" id="search_emp_nm" type="text" placeholder="성명 검색" />
                        <button type="button" class="btn btn-submit btn-search ml-2 btn-secondary">검색</button>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <div class="d-flex justify-content-center">
            <div class="table-responsive">
                <table class="table table-hover" id="myTable">
                    <%--테이블 헤더 시작--%>
                    <thead>
                    <tr class="table_head header tac" type="primary">
                        <th class="m_table w10">식별코드</th>
                        <th class="w10">제품 종류</th>
                        <th class="w20">제품명</th>
                        <th class="w20">모델명</th>
                        <th class="w20">상태</th>
                        <th class="w15">배정자</th>
                        <th class="w15">최근 재물 조사 일</th>

                        <th class="w6" class="m_table">순서</th>
                        <th class="w9" class="m_table">관리</th>
                    </tr>
                    </thead>
                    <%--테이블 헤더 끝--%>

                    <%--테이블 바디 시작--%>
                    <tbody id="listBody">
                    </tbody>
                    <%--테이블 바디 끝--%>
                </table>
            </div>
        </div>
        <div id="paging"></div>
    </div>
</div>
<script src="/static/js/boardMemberList.js"></script>
</body>
</html>
