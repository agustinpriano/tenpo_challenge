Tenpo Challenge

*Pasos para levantar la API:*

1. Descargar el código.
2. Abrir una terminal y situarse en la raíz del proyecto.
2. Tirar el comando "mvn install" para generar el .war
3. Tirar el comando "docker-compose up" para generar las imágenes y levantarlas en dos container distintos (app y db).
4. Importar las request de Postman ubicadas en el archivo Tenpo Challenge.postman_collection.json dentro de _src/main/resources/static_ y probar los dos servicios expuestos por la API.
