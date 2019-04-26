# File and commands transfer over the network

## Simple mode
### receiver
```
java -jar transfer.jar -r RECEIVER_PORT
```
### transmitter
```
java -jar transfer.jar -t RECEIVER_IP RECEIVER_PORT FILE_TO_SEND
```

## SSL mode
### receiver
```
java -jar transfer.jar -rs RECEIVER_PORT RECEIVER_KEYSTORE_PATH RECEIVER_KEYSTORE_PASSWORD
java -jar transfer.jar -rs RECEIVER_PORT RECEIVER_KEYSTORE_PATH RECEIVER_KEYSTORE_PASSWORD RECEIVER_TRUSTSTORE_PATH RECEIVER_TRUSTSTORE_PASSWORD
```
### transmitter
```
java -jar transfer.jar -ts RECEIVER_IP RECEIVER_PORT TRANSMITTER_KEYSTORE_PATH TRANSMITTER_KEYSTORE_PASSWORD FILE_TO_SEND
java -jar transfer.jar -ts RECEIVER_IP RECEIVER_PORT TRANSMITTER_KEYSTORE_PATH TRANSMITTER_KEYSTORE_PASSWORD TRANSMITTER_TRUSTSTORE_PATH TRANSMITTER_TRUSTSTORE_PASSWORD FILE_TO_SEND
```

### Command waiter
```
java -jar transfer.jar -wc WAITER_PORT WAITER_KEYSTORE_PATH WAITER_KEYSTORE_PASSWORD
```

### Command sender
```
java -jar transfer.jar -c WAITER_IP WAITER_PORT COMMANDER_KEYSTORE_PATH COMMANDER_KEYSTORE_PASSWORD
```

### How to create client / server keystores

#### 1. Create a keystores
```
keytool -genkey -alias server -keyalg RSA -keystore server.jks
keytool -genkey -alias client -keyalg RSA -keystore client.jks
```
#### 2. Export client / server public key certificates to cert files
```
keytool -export -file server.cert -keystore server.jks -storepass PASSWORD -alias server
keytool -export -file client.cert -keystore client.jks -storepass PASSWORD -alias client
```
#### 3.  Import server.cert into client keystore / client.cert into server keystore 
```
keytool -import -file client.cert -keystore server.jks -storepass PASSWORD -alias client
keytool -import -file server.cert -keystore client.jks -storepass PASSWORD -alias server
```