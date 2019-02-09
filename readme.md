# File transfer over the network

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
```
### transmitter
```
java -jar transfer.jar -ts RECEIVER_IP RECEIVER_PORT TRANSMITTER_KEYSTORE_PATH TRANSMITTER_KEYSTORE_PASSWORD FILE_TO_SEND
```