# Sistema de recomendación de viajes.
### Application Development for Data Science(DACD)
**Course**: Second  
**Degree**: Bachelor in Data Science and Engineering  
**School**: School of Computer Engineering  
**University**: Universidad de Las Palmas de Gran Canaria (ULPGC)

## Functionalidad
El propósito de este proyecto inacabado es ofrecerle al usuario un destino óptimo en base a sus preferencias tanto climáticas como de presupuesto.
## ¿Qué funciona ahora mismo?

**Módulo PredictionProvider**: obtiene datos de la predicción climática de las Islas Canarias y envía estos datos a un broker para su posterior procesamiento.

**Módulo ComparationProvider**: obtiene datos de los precios de varios hoteles en diferentes plataformas de reserva de estos como Booking, Agoda o FindHotel y envía estos datos a un broker para su posterior procesamiento.

**Módulo EventStoreBuilder**: (he de cambiar el nombre) crea una conexión con el bróker y una sesión además crear un consumidor durable a los tópicos que publican los sensores. También crea un datalake organizado por topics en los que se almacenan los archivos `.events`

**Módulo Business-Unit**: consume los datos de los topics y crea y maneja la inserción de estos datos en las tablas para su posterior análisis.

## ¿Qué he de cambiar/hacer?
  Casi todos los cambios que tienen que ver con el funcionamiento se encuentran en el módulo Business-Unity la interfaz de usuario, el resto de módulos cumple como mínimo con lo esperado en el proyecto.
1. **Personalización de la consulta**: Ahora mismo la aplicación esta diseñada para escoger datos de las Islas Canarias y de unos hoteles de Amsterdam, esto se hizo a modo de prueba del funcionamiento, he de hacer que sea el usuario quien elija el lugar (introduciendo lat y lon), la duración de su estancia, (introduciendo fecha de check_in y check_out) y su hotel, (cambiando la hotel key)
 
2. **Cruce de datos**: He de relacionar los datos referentes al clima con los referentes a los precios de los hoteles, para ello esta creada la tabla Destinations, pero me falta la lógica para implementar el cruce.

3. **Actualización de los datos**: Ahora mismo el módulo Business-Unit no actualiza las tablas con los nuevos datos que vienen de los topics, sólo inserta los nuevos mensajes en las tablas, esto no es nada difícil de implementar, con la sentencia UPDATE.

4. **Interfaz de usuario**: Tengo que hacer una interfaz amigable en la medida de lo posible que permita la consulta del datamart de manera sencilla y precisa.

5. **Principios SOLID**: El proyecto actualmente incumple varios de estos protocolos, con algo de tiempo sería posible aplicarle más modularidad, restarle acoplamiento y demás detalles. El proyecto debido a problemas de planificación esta diseñado para que "funcione" a secas, sin corregir errores de estilo ni de formato. Además no todo el código esta en inglés y hay muchos comentarios y codigo zombie.

6. **Cambio de nombres**: Hay variables clases y métodos que podrían tener un nombre mejor o directamente tener un nombre que correspponda a la funcionalidad, pero arreglar eso es muy sencillo.
