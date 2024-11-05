<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Incluir el archivo JSP de cabecera común -->
    <jsp:include page="./jsp/common/header.jsp"/>

    <Title>Sun ☀ Hotel</Title>
</head>
<body>
<%
    // Recuperar el atributo «view»  de la solicitud
    String view = (String) request.getAttribute("view");

    // Si el atributo «view» es nulo o está vacío, por defecto usar <</jsp/inicio.jsp>>
    if (view == null || view.isEmpty()) {
        view = "/jsp/inicio.jsp";
    }
%>

<!-- Incluir el archivo JSP especificado por la variable «view» -->
<jsp:include page="<%= view %>"/>

<!-- Incluir el archivo JSP de pie de página común -->
<jsp:include page="./jsp/common/footer.jsp"/>

<!-- Incluir el archivo JSP de scripts comunes -->
<jsp:include page="./jsp/common/scripts.jsp"/>

</body>
</html>