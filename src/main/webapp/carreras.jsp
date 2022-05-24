<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carreras</title>
</head>
<body>

<h3>Gestión de la App para administradores || Carreras</h3>

<c:forEach items="{carreras}" var="race">
    <tr>
        <td>${race.id}</td>
        <td>${race.titulo}</td>
        <td>${race.descripcion}</td>
    </tr>
</c:forEach>

</body>

</html>