<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   

<jsp:include page="../../includes/office-head.jsp" />
<jsp:include page="../../includes/office-navbar.jsp" />
    
       
            
           
                        
                        <h2>Mis Productos</h2>
                        <div class="row">
                        
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-primary text-white mb-4">
                                    <div class="card-body">Validados <span class="big-number">${productos_aprobados}</span></div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="views/frontoffice/productos">Ver Detalle</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-xl-3 col-md-6">
                                <div class="card bg-warning text-white mb-4">
                                    <div class="card-body">Pendientes de validación <span class="big-number">${productos_pendientes}</span></div>
                                    <div class="card-footer d-flex align-items-center justify-content-between">
                                        <a class="small text-white stretched-link" href="views/frontoffice/productos?validados=0">Ver Detalle</a>
                                        <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                                    </div>
                                </div>
                            </div>                            
                           
                        </div>
                        
                         <h2>Mis Datos</h2>
                        <div class="row">
                        	${usuario_login}
                        </div>
                        
  
 <jsp:include page="../../includes/office-footer.jsp" />                  