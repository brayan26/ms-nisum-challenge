#!/bin/bash
echo "Start: Sleep 10 seconds"
sleep 10;

# Creando el topic 'created.transaction'
echo "Creando el topic  =>> 'ms.spring.template.topic.in'"
kafka-topics --create --if-not-exists --zookeeper zookeeper:2181 --partitions 5 --replication-factor 1 --topic 'ms.spring.template.topic.in'
echo "topic 'ms.spring.template.topic.in' creado"


# Comando de espera infinita para mantener el contenedor en ejecución
tail -f /dev/null