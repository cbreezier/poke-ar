# Poke AR
Accurate spawn locations for pokemon.

## Running
First create an env file which fills in any missing variables defined in the docker-compose file.
The format should look like:
```
KEY=value
```

Run using a command like:

`./gradlew bootJar && docker-compose -f debug.docker-compose.yml --env-file=debug.env up --build -d`

## OAuth
If you get 401s and 403s, try visiting `/oauth2/authorization/github`