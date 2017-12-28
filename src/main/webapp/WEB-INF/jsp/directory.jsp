<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-light">
    <ul class="navbar-nav">
        <li class="nav-item">
            <a class="nav-link" href="/directory">Root directory</a>
        </li>
    </ul>
</nav>
<div>Directory id: ${directory.id}</div>
<div>Directory name: ${directory.directoryName}</div>
<div>Directory creation date: ${directory.creationDate}</div>
<div>Directory last update date: ${directory.lastUpdateDate}</div>
Subdirectories<br/>
<ul class="list-group">
    <c:forEach items="${directory.subDirectories}" var="sub" varStatus="loop">
        <li class="list-group-item" onclick="/directory/${sub.id}">${sub.directoryName}</li>
    </c:forEach>
</ul>
Files<br/>
<ul class="list-group">
    <c:forEach items="${directory.files}" var="file" varStatus="loop">
        <li class="list-group-item" onclick="/file/${file.id}">${file.name}</li>
        <div>${file.base64}</div>
        <img id="ItemPreview" src="data:image/jpg;base64,${file.base64}" />
    </c:forEach>
</ul>
Add directory<br/>
<form method="post" name="directoryRequestDTO" action="/directory">
    <input name="name" placeholder="Please enter directory name" required/><br/>
    <input name="parentId" value="${directory.id}" hidden />
    <input type="submit" value="Create new directory" />
</form>
Add file<br/>
<form method="post" name="fileRequestDTO" action="/file">
    <input name="name" placeholder="Please enter file name" required/><br/>
    <input type="file" name="bytes" required/><br/>
    <input name="directoryId" value="${directory.id}" hidden />
    <input type="submit" value="Add new file" />
</form>
</body>
</html>
