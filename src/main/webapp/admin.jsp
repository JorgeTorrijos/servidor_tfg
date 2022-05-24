<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administradores</title>
</head>
<body>

<form action="http://localhost:8080/servidor-1.0-SNAPSHOT/login" method="post">

    <h3>Gestión de la App para administradores</h3>

    <div>
        <label>Usuario: </label>
        <input type="text" id="user" name="user" class="form-control">
    </div>

    <div>
        <label>Password: </label>
        <input type="password" id="password" name="password" class="form-control">
    </div>

    <div class="form-group">
        <button type="submit">LOGIN</button>
    </div>

</form>

</body>
</html>