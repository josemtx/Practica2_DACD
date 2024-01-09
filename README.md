# Sistema de recomendación de viajes.
![image](https://camo.githubusercontent.com/2ee4f73dd3e8197e0e82bab20e17d1d21840f6a70b3cd07e9c578bec77953fdd/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f5354415455532d494e434f4d504c4554452d6f72616e6765)
![image](https://camo.githubusercontent.com/464bb037a2b767455f921292a17ed362097b127e592e87a653cdb1b8dc6f2b36/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f52656c65617365642d4a616e75617279253230323032342d79656c6c6f77)
### Desarrollo de Aplicaciones para Ciencia de datos(DACD)
**Curso**: Segundo

**Grado**: Grado en Ciencia e Ingeniería de Datos

**Escuela**: Escuela de Ingeniería Informática

**Universidad**: Universidad de Las Palmas de Gran Canaria (ULPGC)


## Funcionalidad
El proyecto tiene como propósito sugerir destinos basándose en el clima y presupuesto del usuario. Se filtrarían datos climáticos para ajustarse a preferencias del usuario como la temperatura o la probabilidad de precipitación y se cruzarían con tarifas hoteleras, para proponer coincidencias ideales ajustadas al presupuesto del usuario. El resultado sería una lista de lugares con condiciones climáticas de la preferencia del usuario y opciones de alojamiento adecuadas al bolsillo del cliente, estas listas se acrualizarían periódicamente para mantener la relevancia de la información.

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
