<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../includes/office-head.jsp" />
<jsp:include page="../../includes/office-navbar.jsp" />
    
<h2>Añadir nuevo producto:</h2>

<form action="views/frontoffice/crear-producto" method="post">

	<div class="form-group">
		<label for="id">Id:</label>
		<input type="text" class="form-control" name="id" id="id" value="${producto.id}" readonly>
	</div>
	
	<div class="form-group">
		<label for="nombre">Nombre:</label>
		<input type="text" class="form-control" name="nombre" id="nombre" value="${producto.nombre}" placeholder="Escribe el nombre del producto" ${(guardado eq true) ? "readonly" : ""}>
	</div>
	
	<div class="form-group">
		<label for="precio">Precio:</label>
		<input type="text" class="form-control" name="precio" id="precio" value="${producto.precio}" placeholder="0.0 €" ${(guardado eq true) ? "readonly" : ""}>
	</div>
	
	<div class="form-group">
		<label for="imagen">Imagen:</label>
		<input type="text" class="form-control" name="imagen" id="imagen" value="${producto.imagen}" placeholder="URL de la imagen (.jpg o .png)" ${(guardado eq true) ? "readonly" : ""}>
	</div>
	
	<div class="form-group">
		<label for="categoria_id">Categoria:</label>
		<select class="custom-select" name="categoria_id" id="categoria_id">
			<c:forEach items="${categorias}" var="categoria">
				<option value="${categoria.id}" ${(categoria.id eq producto.categoria.id) ? "selected" : ""}>${categoria.nombre}</option>
			</c:forEach>					  					  
		</select>
	</div>
	
	<input type="submit" value="Guardar" class="btn btn-primary btn-block">
        
</form>

<jsp:include page="../../includes/office-footer.jsp" />