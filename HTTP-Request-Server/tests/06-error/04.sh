#!/bin/bash

PORT=$@

REQUEST=$'GET /write HTTP/1.1\r\n\r\n'

printf "$REQUEST" | nc 127.0.0.1 $PORT
printf "\n"

REQUEST=$'POST /ping HTTP/1.1\r\n\r\n'

printf "$REQUEST" | nc 127.0.0.1 $PORT
printf "\n"

REQUEST=$'POST /read HTTP/1.1\r\n\r\n'

printf "$REQUEST" | nc 127.0.0.1 $PORT
printf "\n"

REQUEST=$'POST /tests/05-files/index.html HTTP/1.1\r\n\r\n'

printf "$REQUEST" | nc 127.0.0.1 $PORT
printf "\n"

REQUEST=$'GET/tests/05-files/index.html HTTP/1.1\r\n\r\n'

printf "$REQUEST" | nc 127.0.0.1 $PORT
printf "\n"

REQUEST=$'GETGETGET /tests/05-files/index.html HTTP/1.1\r\n\r\n'

printf "$REQUEST" | nc 127.0.0.1 $PORT
printf "\n"