<%@ page import="ru.aosges.model.Owner" %>
<%@ page import="java.util.List" %>
<!DOCTYPE HTML>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title></head>
<body>
${owners}
<c:set var="ow" value="${owners}"/>
<c:forEach items="${owners}" var="owner">
        <div>
            ${owner.getId()} ${owner.getPhone()} ${owner.getEmail()} ${owner.getPersonalAccount()}
        </div>
</c:forEach>
</body>
</html>