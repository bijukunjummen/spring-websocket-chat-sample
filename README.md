Start up by running 

    mvn spring-boot:run

The code does not have any external dependencies and should start up cleanly. If you are
interested in using RabbitMQ as the external broker to work with Websockets, then the change should be to `bk.tailfile.config.WebSocketDefaultConfig` class:

    //		config.enableStompBrokerRelay("/topic/", "/queue/");
	config.enableSimpleBroker("/topic/", "/queue/");

TO:

     config.enableStompBrokerRelay("/topic/", "/queue/");
	//	config.enableSimpleBroker("/topic/", "/queue/");


Just bring up an instance of RabbitMQ and it should work cleanly.
