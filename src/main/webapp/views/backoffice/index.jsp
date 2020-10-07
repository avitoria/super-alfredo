<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../includes/office-head.jsp" />
<jsp:include page="../../includes/office-navbar-admin.jsp" />
    
<div class="row">

	<div class="col-xl-3 col-md-6">
		<div class="card bg-primary text-white mb-4 position-relative">
			<div class="card-body">Productos validados: <span class="big-number">${numero_productos}</span></div>
			<div class="card-footer d-flex align-items-center justify-content-between">
				<a class="small text-white stretched-link" href="#">Ver Detalle</a>
				<div class="small text-white"><i class="fas fa-angle-right"></i></div>
			</div>
		</div>
	</div>
	
	<div class="col-xl-3 col-md-6">
		<div class="card bg-warning text-white mb-4 position-relative">
			<div class="card-body">Productos pendientes de validar: <span class="big-number">${productos_pendientes}</span></div>
			<div class="card-footer d-flex align-items-center justify-content-between">
				<a class="small text-white stretched-link" href="views/backoffice/validar-producto">Ver Detalle</a>
				<div class="small text-white"><i class="fas fa-angle-right"></i></div>
			</div>
		</div>
	</div>
	
	<div class="col-xl-3 col-md-6">
		<div class="card bg-primary text-white mb-4 position-relative">
			<div class="card-body">Usuarios: <span class="big-number">${numero_usuarios}</span></div>
			<div class="card-footer d-flex align-items-center justify-content-between">
				<a class="small text-white stretched-link" href="#">Ver Detalle</a>
				<div class="small text-white"><i class="fas fa-angle-right"></i></div>
			</div>
		</div>
	</div>
	
	<div class="col-xl-3 col-md-6">
		<div class="card bg-primary text-white mb-4 position-relative">
			<div class="card-body">Categorías: <span class="big-number">20</span></div>
			<div class="card-footer d-flex align-items-center justify-content-between">
				<a class="small text-white stretched-link" href="#">Ver Detalle</a>
				<div class="small text-white"><i class="fas fa-angle-right"></i></div>
			</div>
		</div>
	</div>
	
</div>

<jsp:include page="../../includes/office-footer.jsp" />    
