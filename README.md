# super-alfredo

Proyecto Java mavenizado según patrón de **MVC** que explota una BDD **Mysql**
CMS para gestionar los productos de un supermercado. 

Existen dos tipos de perfiles:

1. **Administrador [admin,123456]**: puede validar productos y crear, modificar y eliminar categorías.
2. **Usuario [pepe, 123456]**: puede crear, actualizar y eliminar sus productos. La creación y la actualización requieren validación por parte del Administrador para ser visibles en la parte pública

*Las contraseñas estan haseadas en MD5 dentro de la bbdd.*


## Tecnología

- Maven 4.0.0
- Java 8
- Java Servlet Api 3.1.0
- JSP 2.2
- JSTL 1.2
- Javax Validation Api 1.1
- Bootstrap 4.5.0
- FontAwesome 5.13.0
- Datatables.net 1.10.21

Para más detalles, ver [pom.xml](https://github.com/ipartek/supermercado-java/blob/master/pom.xml)


## Configuración de la BDD

Ejecutar el script [script-db.sql](https://github.com/avitoria/super-alfredo/blob/master/script-db.sql) para crear la base de datos **super-alfredo** y las tablas **categoria**, **personas**, **producto**, **rol** y **usuario**.

Para poder conectar a la BDD, configurar usuario y contraseña (username/password) en el fichero **src/main/webapp/META-INF/context.xml** 

```
<?xml version="1.0" encoding="UTF-8"?>
  <Context path="/ejemplo05">
      <Resource
          type="javax.sql.DataSource"
          auth="Container"
          name="jdbc/super"
          driverClassName="com.mysql.jdbc.Driver"
          url="jdbc:mysql://localhost:3306/supermercado"
          username="USUARIO"
          password="PASSWORD"
          maxActive="100"
          maxIdle="30"
          maxWait="10000"          
      />
 </Context>
```

## Ejecución

Ejecutar con servidor de aplicaciones (**Apache Tomcat 9.0** recomendado).


## Estructura del proyecto

Consultar la [API](https://github.com/ipartek/supermercado-java/tree/master/src/main/webapp/doc) (accesible desde el menú de navegación)

Consultar los siguientes archivos **package.info**:

- **com.ipartek.formacion.listenner** Listener que se ejecuta al arrancar la APP
- **com.ipartek.formacion.controller.backoffice** Controladores para el usuario administrador
- **com.ipartek.formacion.controller.frontoffice** Controladores para el usuario normal
- **com.ipartek.formacion.modelo.pojo** Pojos o Clases para crear Objetos e java
- **com.ipartek.formacion.modelo.dao** DAO para relacionar los Pojos de Java con las tablas dela BBDD
- **com.ipartek.formacion.seguridad** Filtros de seguridad


