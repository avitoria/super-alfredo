
<jsp:include page="../includes/cabecera.jsp" >
  <jsp:param name="pagina" value="Login" />
  <jsp:param name="title" value="Login" /> 
</jsp:include>
		
	<form action="login" method="post" class="form-login" onsubmit="cifrar()">
	
	 	<div class="form-group">
			<input type="text" name="nombre" value="pepe" placeholder="Tu Nombre"  class="form-control">
		</div>
		
		<div class="form-group">
			<input type="password" id="pass" value="123456" name="pass" placeholder="Tu Contrase�a"  class="form-control">
		</div>
		
		<div class="form-group">
			<select name="idioma" class="custom-select">		  
			  <option value="es">Castellano</option>
			  <option value="eu">Euskera</option>
			  <option value="en">Ingles</option>
			</select>
		</div>
		
		<input type="submit" value="Iniciar Sesi�n" class="btn btn-primary btn-block">
		
		<p><a href="views/usuarios/form-usuario.jsp">Registrarse</a></p>
	
	</form>
	
	

<%@include file="../includes/pie.jsp" %>

