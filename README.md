# Sistema de recomendación de viajes.
### Application Development for Data Science(DACD)
**Course**: Second  
**Degree**: Bachelor in Data Science and Engineering  
**School**: School of Computer Engineering  
**University**: Universidad de Las Palmas de Gran Canaria (ULPGC)

## Funcionalidad
El propósito de este proyecto inacabado es ofrecerle al usuario un destino óptimo en base a sus preferencias tanto climáticas como de presupuesto. Por ejemplo, si un usuario prefiere temperaturas entre 20 y 25 grados Celsius, el sistema filtraría los registros de la tabla Weather para encontrar coincidencias en ese rango durante las fechas especificadas. Paralelamente, el sistema puede consultar la tabla HotelRates para encontrar opciones de alojamiento que se ajusten al presupuesto del usuario. Una vez identificados los destinos con el clima deseado y las opciones de alojamiento adecuadas, el sistema debería cruzar estos datos para encontrar coincidencias. Esto significa buscar destinos donde tanto el clima como las opciones de alojamiento cumplen con las preferencias del usuario. El sistema generaría una lista de recomendaciones que incluiría destinos, información climática para las fechas seleccionadas, y opciones de hoteles con sus respectivas tarifas. Dado que tanto las condiciones climáticas como las tarifas de los hoteles pueden cambiar, el sistema debería actualizarse periódicamente para reflejar la información más reciente.

## ¿Qué funciona ahora mismo?

**Módulo PredictionProvider**: obtiene datos de la predicción climática de las Islas Canarias, selecciona los datos de interés y envía estos datos a un broker para su posterior procesamiento.

**Módulo ComparationProvider**: obtiene datos de los precios de varios hoteles en diferentes plataformas de reserva de estos como Booking, Agoda o FindHotel y envía estos datos a un broker para su posterior procesamiento.

**Módulo EventStoreBuilder**: (he de cambiar el nombre) crea una conexión con el bróker y una sesión además crear un consumidor durable a los tópicos que publican los sensores. También crea un datalake organizado por topics en los que se almacenan los archivos `.events`

**Módulo Business-Unit**: consume los datos de los topics, procesa los datos seleccionando y descartando aquellos que no tengan que ver con el modelo de negocio y crea y maneja la inserción de estos datos en las tablas para su posterior análisis.

## ¿Qué he de cambiar/hacer?
  Casi todos los cambios que tienen que ver con el funcionamiento se encuentran en el módulo Business-Unit y la interfaz de usuario, el resto de módulos cumple como mínimo con lo esperado en el proyecto.
1. **Personalización de la consulta**: Implementar una interfaz de usuario que permita introducir coordenadas, fechas y claves de hotel para personalizar las búsquedas.
 
2. **Cruce de datos**:  Desarrollar un algoritmo en DestinationsDataManager que relacione el clima con los precios hoteleros, llenando la tabla Destinations para las consultas.

3. **Actualización de los datos**:  Establecer procedimientos periódicos que actualicen las tablas con los últimos datos recibidos, garantizando información reciente. Esto no es nada difícil de implementar, con la sentencia UPDATE.

4. **Interfaz de usuario**: Diseñar una interfaz gráfica que facilite la consulta del datamart de manera sencilla y precisa.

5. **Principios SOLID**:  Refactorizar el código para cumplir con SOLID y buenas prácticas.

6. **Cambio de nombres**: Hay variables clases y métodos que podrían tener un nombre mejor o directamente tener un nombre que correspponda a la funcionalidad, pero arreglar eso es muy sencillo.
