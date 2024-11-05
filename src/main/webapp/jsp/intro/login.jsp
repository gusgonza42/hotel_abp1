<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container-login-main">

    <div class="container-login-form">
        <a href="/hotel" class="logo-login"><img src="./utils/imagen/logo.png" alt="logo"></a>
        <h2>LOGIN</h2>
        <form action="login" method="post">
            <label for="usuario">Email:</label><br>
            <input type="text" id="usuario" name="usuario" required><br>
            <label for="password">Contraseña:</label><br>
            <input type="password" id="password" name="password" required><br><br>
            <input type="submit" value="Login" class="button">
        </form>
        <a href="/hotel/registro">¿Aún no tienes cuenta? <u>Regístrate aquí</u></a>
    </div>
    <div class="container-login-photo">
        <button class="button"><a href="/hotel/registro">Registro</a></button>
        <img src="./utils/imagen/login.jpg" alt="Login Image">

    </div>
</div>