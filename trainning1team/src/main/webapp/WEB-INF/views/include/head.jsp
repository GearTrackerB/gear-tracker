<%--
  Created by IntelliJ IDEA.
  User: seongho
  Date: 2023/12/19
  Time: 10:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8" language="java"  pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
  <link href="/static/css/default.css" rel="stylesheet" />
  <link href="/static/css/jquery-ui.css" rel="stylesheet" />
  <link href="/static/css/jquery-ui.structure.css" rel="stylesheet" />
  <link href="/static/css/jquery-ui.theme.css" rel="stylesheet" />
  <link href="/static/css/layout.css" rel="stylesheet" />
  <script src="/static/js/jquery-3.6.4.min.js"></script>
  <script src="/static/js/jquery-ui.js"></script>
  <script src="/static/js/common.js"></script>
  <%-- QR 생성 --%>
<%--  <script type="text/javascript" src="qrcode.js"></script>--%>
  <script type="text/javascript" src="https://unpkg.com/@zxing/library@latest"></script>
  <script type="text/javascript" src="https://unpkg.com/file-saver@latest"></script>
</head>