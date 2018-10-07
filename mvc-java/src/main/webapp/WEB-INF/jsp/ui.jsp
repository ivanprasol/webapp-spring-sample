<%@ page language="java"
trimDirectiveWhitespaces="true"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="_common-head.jsp" %>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
</head>
<body>
  <h1>Test web app</h1>
  <h3><s:message code="technology"/>: ${appLanguage}</h3>
  <h3>Locale: ${locale}</h3>
  <p>
    <a href="data/test.json">Show JSON data.</a>
  </p>
</body>
</html>
