# Sicredi Desafio QA

Automação de testes de API para o desafio Sicredi, utilizando **Java 17**, **Maven**, **TestNG**, **RestAssured** e um **mock-server** customizado em Node.js. O pipeline é orquestrado pelo **GitHub Actions** e gera relatório no **Allure**.

---

## 📋 Pré-requisitos

- Java 17+
- Maven 3.8+
- Node.js 16+
- Git

---

## ⚙️ Instalação & Execução Local

1. **Clone o repositório**  
   ```bash
   git clone https://github.com/MottaPaim/sicredi-desafio-qa.git
   cd sicredi-desafio-qa
   ```

2. **Instale as dependências do mock-server**  
   ```bash
   cd mock-server
   npm ci
   ```

3. **Inicie o mock-server**  
   ```bash
   npm run start
   # Servidor rodará em http://localhost:3000
   ```

4. **Execute os testes**  
   Volte à raiz do projeto e rode:
   ```bash
   mvn clean test -DbaseUrl=http://localhost:3000
   ```

5. **Gere relatório Allure**  
   ```bash
   mvn allure:report
   ```
   O HTML ficará em `target/site/allure-maven-plugin/index.html`.

---

## 🚀 Pipeline CI (GitHub Actions)

O `.github/workflows/ci.yml` faz:

1. **Checkout** do código.
2. **Setup** de Node.js 16 e Java 17.
3. **Start mock-server** (levanta `mock-server/server.js` e valida o health-check em `/auth/login`).
4. **Run Maven tests** apontando para `http://localhost:3000`.
5. **Generate Allure report**.
6. **Upload** do relatório como artefato (`allure-report.zip`).

---

## ✅ Cenários de Teste Automatizados

Os scripts em `src/test/java/com/sicredi/qa` contemplam:

1. **AuthTest**  
   - **Login com sucesso**: `POST /auth/login` → HTTP 200 + JWT simulado.  
   - **Login com falha**: credenciais ausentes → HTTP 400 + `{ error: "name and price are required" }`.

2. **AddProductTest**  
   - **Adicionar produto (sucesso)**: `POST /products/add` com `name`, `price > 0` → HTTP 201 + corpo do novo produto.  
   - **Adicionar produto (erro)**: payload ausente ou inválido → HTTP 400 + `{ error: "name and price are required" }`.

3. **ProductsAuthTest**  
   - **Listar produtos autenticados**: `GET /auth/products` com header `Authorization: Bearer fake-jwt-token` → HTTP 200 + lista de produtos.  
   - **Acesso proibido**: sem token → HTTP 403.  
   - **Token inválido**: token diferente → HTTP 401.

4. **StatusTest**  
   - **Verificar status do serviço**: `GET /status` → HTTP 200.

5. **UsersTest**  
   - **Listar usuários**: `GET /users` → HTTP 200 + lista de usuários.

---

## 🔮 Próximos Passos & Melhorias

1. **Validação de contrato** com JSON Schema.  
2. **Testes de limites** para `price`, `name`.  
3. **Data-driven** (CSV/JSON) para múltiplos cenários.  
4. **Performance**: medir tempo de resposta.  
5. **Fluxo E2E**: login → listagem → adição → verificação.  

---

## 📂 Estrutura de Pastas

```
.
├── .github/workflows/ci.yml      # Pipeline CI
├── mock-server/
│   ├── db.json                   # Dados simulados
│   └── server.js                 # Lógica custom de autenticação e CRUD
├── src/test/java/com/sicredi/qa  # TestNG + RestAssured
├── pom.xml                       # Configurações Maven / Allure
└── README.md
```

---

## 📈 Como visualizar o relatório Allure

1. Baixe o artefato `allure-report.zip` na aba **Actions**.  
2. Extraia todo o conteúdo e abra `index.html` num navegador.  
3. Alternativamente, use `python -m http.server` na pasta extraída para servir localmente.

---

Desafio Sicredi QA cumprido! 🚀
