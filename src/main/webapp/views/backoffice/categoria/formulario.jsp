<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../../includes/office-head.jsp" />
<jsp:include page="../../../includes/office-navbar-admin.jsp" />
    
<form action="views/backoffice/categoria" method="post">
	
	<div class="form-group">
		<label for="id">Id:</label>
		<input type="text" name="id" id="id" value="${categoria.id}" class="form-control" readonly>
	</div>
	
	<div class="form-group">
		<label for="nombre">Nombre:</label>
		<input type="text" name="nombre" id="nombre" value="${categoria.nombre}" class="form-control">
	</div>
	
	<input type="submit" value="Enviar" class="btn btn-primary btn-block">		
	
</form>

<jsp:include page="../../../includes/office-footer.jsp" />
