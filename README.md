# Concesionaria de automoviles

## Problematica
Una tienda venta de automóviles necesita de un sistema que le permita
realizar un CRUD de todos los vehículos que tiene actualmente a la 
venta para luego publicarlos en un catálogo.

Realizar el modelado correspondiente de la clase **Automovil** teniendo 
en cuenta los siguientes datos: 
* Id
* Modelo
* Marca
* Motor
* Color
* Patente/placa
* Cantidad de puertas.

Realizar los métodos necesarios para permitir las operaciones CRUD de 
cada automóvil.

#### IMPORTANTE:
Respetar el modelo de capas, separando la responsabilidad de cada 
una de ellas: 
* Lógica
* Interfaz gráfica (en caso de realizarla) 
* Persistencia. 

## Como usarlo:
Una vez clonado el repositorio:
- Abrir Docker y dejarlo en segundo plano
- Ejecutar el comando "docker-compose up" en el path de la carpeta root de la app concesionaria-automobiles
- Para testear la app se puede hacer mediante swagger: 

[Testear con swagger](http://localhost:8080/swagger-ui/index.html)