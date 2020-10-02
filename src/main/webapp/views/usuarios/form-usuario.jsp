<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<jsp:include page="../../includes/cabecera.jsp" >
  <jsp:param name="pagina" value="usuario" />
  <jsp:param name="title" value="Editar Usuario" /> 
</jsp:include>


<h1>Registro de Usuario</h1>

	<form action="registro-usuario" method="post"">

		<div class="form-group">			
			<label for="nombre">Nombre:</label>
			<small id="nombreHelp" class="form-text"></small>
			<input type="text" name="nombre" onkeyUp="buscarUsuario(event)" id="nombre" value="${nombre}" class="form-control" placeholder="Escribe tu nombre de usuario" >			
		</div>
		
		<div class="form-group">
			<label for="pass">Contraseña:</label>
			<input type="password" name="pass" id="pass" value="" class="form-control" >
		</div>					
				
		<div class="form-group">
			<label for="repass">Repita la contraseña:</label>
			<input type="password" name="repass" id="repass" value="" class="form-control" >
		</div>
				
		<input type="submit" value="Enviar" class="btn btn-primary btn-block">
		
	</form>

<%@include file="../../includes/pie.jsp" %>

