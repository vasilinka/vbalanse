<%--
  Created by IntelliJ IDEA.
  User: Василинка
  Date: 04.08.14
  Time: 20:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Declares the root element that allows behaviour to be modified through Angular custom HTML tags. -->
<html ng-app="persons">
<head>
  <title></title>
  <script src="../lib/angular.min.js"></script>
  <script src="../lib/jquery-1.9.1.js"></script>
  <script src="../lib/ui-bootstrap-0.10.0.min.js"></script>
  <script src="../lib/ng-grid.min.js"></script>

  <script src="script/person.js"></script>

  <link rel="stylesheet" type="text/css" href="../lib/bootstrap.min.css"/>
  <link rel="stylesheet" type="text/css" href="../lib/ng-grid.min.css"/>
  <link rel="stylesheet" type="text/css" href="css/style.css"/>
</head>

<body>
<a href="j_spring_security_logout"> Logout</a>

<br>

<div class="grid">
  <!-- Specify a JavaScript controller script that binds Javascript variables to the HTML.-->
  <div ng-controller="personsList">
    <table style="border: 1px solid grey;">
      <thead>
      <td>Название</td>
      <td>Дата публикации</td>
      </thead>
      <tr ng-repeat="article in persons.list">
        <td>{{article.title}}</td>
        <td>{{article.dateOfPublish | date:'MM/dd/yyyy'}}</td>
      </tr>
    </table>
    <!-- Binds the grid component to be displayed. -->
    <div class="gridStyle" ng-grid="gridOptions"></div>

    <!--  Bind the pagination component to be displayed. -->
    <pagination direction-links="true" boundary-links="true"
                total-items="persons.totalResults" page="persons.currentPage" items-per-page="persons.pageSize"
                on-select-page="refreshGrid(page)">
    </pagination>
  </div>
</div>

</body>
</html>
