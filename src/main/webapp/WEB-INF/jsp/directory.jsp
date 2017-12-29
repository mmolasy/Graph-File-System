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
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <a class="navbar-brand" href="#">Graph File System</a>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/directory">Root directory<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="/directory/${directory.parentDirectoryId}">Back ...<span class="sr-only">(current)</span></a>
            </li>
        </ul>
        <div class="card my-2 my-lg-0">
            <div class="card-body">
                <div>Directory id: ${directory.id}</div>
                <div>Directory name: ${directory.directoryName}</div>
                <div>Directory creation date: ${directory.creationDate}</div>
                <div>Directory last update date: ${directory.lastUpdateDate}</div>
            </div>
        </div>
    </div>
</nav>
<div class="col-xs-12" style="height:50px;"></div>
<div class="container">
    <div class="row">
        <div class="col-sm-6">
            <div class="card">
                <div class="card-body">
                    <h2>Add directory</h2>
                    <form method="post" name="directoryRequestDTO" action="/directory">
                        <div class="form-group">
                            <label for="directoryInput">Directory name:</label>
                            <input name="name" class="form-control" id="directoryInput" placeholder="Please enter directory name" required/>
                            <small id="directoryNameHelp" class="form-text text-muted">Directory name will help you to identify.</small>
                        </div>
                        <input name="parentId" value="${directory.id}" hidden />
                        <button type="submit" class="btn btn-primary">Create new directory</button>
                    </form>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="card">
                <div class="card-body">
                    <h2>Add file</h2>
                    <form method="post" name="fileRequestDTO" action="/file" enctype="multipart/form-data">
                        <div class="form-group">
                            <label for="nameInput">File name:</label>
                            <input name="name" class="form-control" id="nameInput" placeholder="Please enter file name" required/>
                            <small id="nameHelp" class="form-text text-muted">Here you can name your file.</small>
                        </div>
                        <div class="form-group">
                            <label for="fileInput">Select file:</label>
                            <input type="file" name="file" class="form-control" id="fileInput" placeholder="Please select name" required/>
                            <small id="fileHelp" class="form-text text-muted">Here you can select your file.</small>
                        </div>
                        <input name="directoryId" value="${directory.id}" hidden />
                        <button type="submit" class="btn btn-primary">Add new file</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="col-xs-12" style="height:50px;"></div>
    <div class="row">
        <div class="col-sm-6">
            Subdirectories<br/>
            <ul class="list-group">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Create Date</th>
                        <th>Last Update Date</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${directory.subDirectories}" var="sub" varStatus="loop">
                    <tr>
                        <td><a href="/directory/${sub.id}">${sub.directoryName}</a></td>
                        <td><p>${sub.creationDate}</p></td>
                        <td><p>${sub.lastUpdateDate}</p></td>
                        <td>
                            <form method="post" name="directoryRequestDTO" action="/directory/delete">
                                <input name="id" value="${sub.id}" hidden />
                                <input name="parentId" value="${directory.id}" hidden />
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </ul>
        </div>
        <div class="col-sm-6">
            Files<br/>
            <ul class="list-group">
                <table class="table">
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Create Date</th>
                        <th>Image</th>
                        <th>Delete</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${directory.files}" var="file" varStatus="loop">
                        <tr>
                            <td>${file.name}</td>
                            <td>${file.creationDate}</td>
                            <td><img class="img-thumbnail" id="ItemPreview" src="data:image/jpg;base64,${file.base64}" /></td>
                            <td>
                                <form method="post" name="fileRequestDTO" action="/file/delete">
                                    <input name="id" value="${file.id}" hidden />
                                    <input name="directoryId" value="${directory.id}" hidden />
                                    <button type="submit" class="btn btn-primary">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
