<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container-fluid-register">
    <div class="row g-0 h-100">
        <!-- imagen -->
        <div class="col-md-6 d-flex ">
            <img src="./utils/imagen/register_0.jpg" alt="Imagen de Registro" class="img-fluid image-side">
        </div>

        <!-- Formulario de registro -->
        <div class="col-md-6 form-side d-flex align-items-center justify-content-center">
            <div class="card p-5">
                <div class="text-center">
                    <a href="/hotel" class="logo-login">
                        <img src="./utils/imagen/logo.png" alt="logo" class="mb-3 logo-image">
                    </a>
                    <h2 class="mb-4">Registrate</h2>
                </div>
                <form id="registroForm" action="registro" method="post">

                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Contraseña</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <div class="mb-3">
                        <label for="confirm-password" class="form-label">Confirmar Contraseña</label>
                        <input type="password" class="form-control" id="confirm-password" name="confirm_password"
                               required>
                    </div>
                    <input type="hidden" id="rol" name="rol" value="cliente">
                    <button type="submit" class="btn btn-primary bot-on w-100">Registrarse</button>
                </form>
                <div class="text-center mt-3">
                    <a href="/hotel/login">¿Ya tienes cuenta? Inicia sesión</a>
                </div>
            </div>
        </div>
    </div>
</div>