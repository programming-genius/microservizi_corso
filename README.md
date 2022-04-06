# Microservizi

UTILIZZO DOCKER:

Dopo la compilazione dei file jar dei vari progetti da inserire nelle cartelle:

docker network create mobile_net

sudo docker build . -f postgres_auth.dev -t postgres_auth

sudo docker run --net mobile_net -d -p 5432:5432 --name postgres_auth postgres_auth

sudo docker build . -f postgres_anagrafica.dev -t postgres_anagrafica

sudo docker run --net mobile_net -d -p 5433:5432 --name postgres_anagrafica postgres_anagrafica

sudo docker build . -f sim_anagrafica.dev -t postgres_mobile

sudo docker run --net mobile_net -d -p 5434:5432 --name postgres_mobile postgres_mobile

sudo docker build . -f eureka.dev -t eureka

sudo docker run --net mobile_net -d -p 80:80 --name eureka eureka

sudo docker build . -f zuul.dev -t zuul

sudo docker run --net mobile_net -d -p 81:80 --name zuul zuul

sudo docker build . -f anagrafica.dev -t anagrafica

sudo docker run --net mobile_net -d -p 82:80 --name anagrafica anagrafica

sudo docker build . -f auth.dev -t auth

sudo docker run --net mobile_net -d -p 83:80 --name auth auth

sudo docker build . -f sim.dev -t mobile

sudo docker run --net mobile_net -d -p 84:80 --name mobile mobile

sudo docker build . -f registration.dev -t registration

sudo docker run --net mobile_net -d -p 85:80 --name registration registration

sudo docker build . -f operator.dev -t operator

sudo docker run --net mobile_net -d -p 86:80 --name operator operator

sudo docker build . -f hystrix-dashboard.dev -t hystrix-dashboard

sudo docker run --net mobile_net -d -p 87:80 --name hystrix-dashboard hystrix-dashboard


UTILIZZO DOCKER COMPOSE:

Dopo la generazione dei vari file jar da inserire nella cartella docker compose:

docker network create mobile_net

sudo docker-compose up -d
