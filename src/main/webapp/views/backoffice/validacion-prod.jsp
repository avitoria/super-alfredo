<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../../includes/office-head.jsp" />
<jsp:include page="../../includes/office-navbar-admin.jsp" />

<table class="tabla table table-striped">
	<thead>
		<tr>
			<td>Id</td>
			<td>Nombre</td>
			<td>Precio</td>
			<td>Imagen</td>
			<td>Categoría</td>
			<td>Validar</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${productos}" var="p">
			<tr>
				<td>${p.id}</td>
				<td>${p.nombre}</td>
				<td>${p.precio}</td>
				<td>${p.imagen}</td>
				<td>${p.categoria.nombre}</td>
				<td>
					<!-- <a href="#" onclick="confirmar('${p.nombre}')"><i class="fas fa-check fa-2x" title="Validar producto"></i></a> -->
					<a href="views/backoffice/validar-producto?id=${p.id}"><i class="fas fa-check fa-2x" title="Validar producto"></i></a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<jsp:include page="../../includes/office-footer.jsp" />
