# 🌎 GeoClimate API

API REST desenvolvida em **Java + Spring Boot** que consulta informações de **localização e clima a partir de um CEP brasileiro**.

A aplicação integra duas APIs externas:

- ViaCEP → informações de endereço
- OpenWeatherMap → dados climáticos

Além disso, todas as consultas são armazenadas em um histórico no banco de dados.

---

# 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Maven
- H2 Database (para desenvolvimento)
- Docker
- JUnit + Mockito (testes)
- API ViaCEP
- API OpenWeatherMap

---

# 📦 Estrutura do projeto

```
geoclimate
 ┣ controller
 ┣ service
 ┣ client
 ┣ dto
 ┣ model
 ┣ repository
 ┣ exception
 ┣ config
 ┗ resources
```

---

# 🔎 Funcionalidades

### Buscar clima por CEP

Consulta um CEP brasileiro, obtém o endereço e retorna o clima atual da cidade.

**Endpoint**

```
GET /api/geoclimate/{cep}
```

Exemplo:

```
http://localhost:8080/api/geoclimate/01001000
```

Resposta exemplo:

```json
{
  "cep": "01001-000",
  "cidade": "São Paulo",
  "estado": "SP",
  "logradouro": "Praça da Sé",
  "bairro": "Sé",
  "clima": {
    "temperatura": 24.5,
    "descricao": "céu limpo"
  }
}
```

---

### Histórico de consultas

Lista todas as consultas realizadas na API.

```
GET /api/geoclimate/historico
```

Exemplo:

```
http://localhost:8080/api/geoclimate/historico
```

Resposta exemplo:

```json
[
  {
    "cep": "01001000",
    "cidade": "São Paulo",
    "estado": "SP",
    "temperatura": 24.5,
    "descricao": "céu limpo",
    "dataConsulta": "2026-03-06T14:20:00"
  }
]
```

---

# 🧪 Testes

O projeto possui testes unitários utilizando:

- JUnit 5
- Mockito

Para rodar os testes:

```
mvn test
```

---

# 🐳 Rodando com Docker

### 1️⃣ Build da imagem

```
docker build -t geoclimate .
```

### 2️⃣ Rodar o container

```
docker run -p 8080:8080 geoclimate
```

A API ficará disponível em:

```
http://localhost:8080
```

---

# ▶️ Rodando localmente

### 1️⃣ Clonar o projeto

```
git clone https://github.com/SEU-USUARIO/geoclimate.git
```

### 2️⃣ Entrar na pasta

```
cd geoclimate
```

### 3️⃣ Rodar a aplicação

```
mvn spring-boot:run
```

A API ficará disponível em:

```
http://localhost:8080
```

---

# 🧪 Testando a API

## Buscar CEP

```
GET http://localhost:8080/api/geoclimate/01001000
```

## Ver histórico

```
GET http://localhost:8080/api/geoclimate/historico
```

---

# 🗄 Banco de dados H2

O banco H2 está habilitado para desenvolvimento.

Console:

```
http://localhost:8080/h2-console
```

Configuração:

```
JDBC URL: jdbc:h2:mem:geoclimate
User: sa
Password: (vazio)
```

---

# 📚 APIs utilizadas

ViaCEP  
https://viacep.com.br

OpenWeatherMap  
https://openweathermap.org/api

---

# 👨‍💻 Autor

Projeto desenvolvido como desafio técnico utilizando **Java e Spring Boot**.

Pedro Henrique