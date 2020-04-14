<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%--Redirect to login page if not logged in--%>
<c:if test="${empty sessionScope.userSessionEmail}">
    <c:redirect url = "/"/>
</c:if>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/docs/4.0/assets/img/favicons/favicon.ico">

    <title>GCU AVS - Dashboard</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/navbar-fixed/">

    <!-- Bootstrap core CSS -->
    <link href="assets/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->

    <link href="assets/css/dashboard.css" rel="stylesheet">

    <!-- google font  -->
    <link href='https://fonts.googleapis.com/css?family=Google+Sans:400,500&lang=en' rel='stylesheet' type='text/css'>
</head>