# 🎓 Sistema de Cadastro de Aprovados em Concursos Públicos

Sistema completo de cadastro de pessoas aprovadas em concursos públicos, com frontend responsivo e backend em Java/Spring Boot.

## 📋 Funcionalidades

- ✅ Cadastro de aprovados com validação de dados
- 📸 Upload de foto do aprovado
- 📱 Interface responsiva e moderna
- 🔍 Listagem de todos os aprovados cadastrados
- 🗑️ Exclusão de cadastros
- 💾 Banco de dados H2 (local)
- 🎨 Design moderno com gradientes e animações

## 🛠️ Tecnologias Utilizadas

### Backend

- **Java 17**
- **Spring Boot 3.2.1**
- **Spring Data JPA**
- **H2 Database** (banco de dados local)
- **Spring Validation**
- **Lombok**

### Frontend

- **HTML5**
- **CSS3** (com gradientes e animações)
- **JavaScript** (Vanilla JS, sem frameworks)
- **API Fetch** para comunicação com backend

## 📦 Estrutura do Projeto

```
concursos_app/
├── pom.xml                                    # Dependências Maven
├── src/
│   ├── main/
│   │   ├── java/com/concursos/
│   │   │   ├── ConcursosApplication.java      # Classe principal
│   │   │   ├── controller/
│   │   │   │   └── AprovadoController.java    # Endpoints REST
│   │   │   ├── service/
│   │   │   │   └── AprovadoService.java       # Lógica de negócio
│   │   │   ├── model/
│   │   │   │   └── Aprovado.java              # Entidade JPA
│   │   │   └── repository/
│   │   │       └── AprovadoRepository.java    # Acesso ao banco
│   │   └── resources/
│   │       ├── application.properties          # Configurações
│   │       └── static/
│   │           └── index.html                  # Frontend
├── data/                                       # Banco de dados H2 (criado automaticamente)
├── uploads/                                    # Imagens enviadas (criado automaticamente)
└── README.md
```

## 🚀 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

1. **Java JDK 17 ou superior**

   ```bash
   # Verificar versão do Java
   java -version
   ```

2. **Maven 3.6 ou superior**

   ```bash
   # Verificar versão do Maven
   mvn -version
   ```

### 📥 Instalação do Java e Maven (Ubuntu/Debian)

```bash
# Atualizar repositórios
sudo apt update

# Instalar Java 17
sudo apt install openjdk-17-jdk -y

# Instalar Maven
sudo apt install maven -y

# Verificar instalação
java -version
mvn -version
```

### 📥 Instalação do Java e Maven (Windows)

1. **Java JDK 17:**
   - Baixe em: <https://www.oracle.com/java/technologies/downloads/#java17>
   - Instale e configure a variável de ambiente JAVA_HOME

2. **Maven:**
   - Baixe em: <https://maven.apache.org/download.cgi>
   - Extraia e adicione ao PATH do sistema

## 🎯 Como Executar

### 1️⃣ Clonar/Navegar até o projeto

```bash
cd /home/debian/p2/concursos_app
```

### 2️⃣ Compilar o projeto

```bash
mvn clean package
```

### 3️⃣ Executar a aplicação

```bash
mvn spring-boot:run
```

**OU** executar o JAR gerado:

```bash
java -jar target/concursos-app-1.0.0.jar
```

### 4️⃣ Acessar a aplicação

Após a inicialização, você verá no console:

```
========================================
🚀 APLICAÇÃO INICIADA COM SUCESSO!
========================================
📝 Frontend: http://localhost:8080
🔗 API: http://localhost:8080/api/aprovados
💾 Console H2: http://localhost:8080/h2-console
========================================
```

- **Frontend:** <http://localhost:8080>
- **API REST:** <http://localhost:8080/api/aprovados>
- **Console H2:** <http://localhost:8080/h2-console>

## 🧪 Como Testar

### 1. Teste pelo Frontend (Recomendado)

1. Abra o navegador em: <http://localhost:8080>
2. Preencha o formulário com os dados:
   - **Nome:** Digite um nome completo
   - **E-mail:** Digite um e-mail válido
   - **Telefone:** Digite no formato (00) 00000-0000
   - **Concursos Aprovados:** Liste os concursos
   - **Foto:** Selecione uma imagem (opcional)
3. Clique em **CADASTRAR**
4. Veja o cadastro aparecer na lista abaixo

### 2. Teste pela API (Postman/Insomnia/cURL)

#### Listar todos os aprovados

```bash
curl http://localhost:8080/api/aprovados
```

#### Cadastrar um aprovado (sem imagem)

```bash
curl -X POST http://localhost:8080/api/aprovados \
  -F "nome=João Silva" \
  -F "email=joao@email.com" \
  -F "telefone=(11) 98765-4321" \
  -F "concursosAprovados=TRF 1ª Região - Analista (2023)"
```

#### Cadastrar com imagem

```bash
curl -X POST http://localhost:8080/api/aprovados \
  -F "nome=Maria Santos" \
  -F "email=maria@email.com" \
  -F "telefone=(21) 99999-8888" \
  -F "concursosAprovados=Polícia Federal - Agente (2022)" \
  -F "imagem=@/caminho/para/foto.jpg"
```

#### Deletar um aprovado

```bash
curl -X DELETE http://localhost:8080/api/aprovados/1
```

### 3. Acessar o Console do Banco H2

1. Acesse: <http://localhost:8080/h2-console>
2. Configure:
   - **JDBC URL:** `jdbc:h2:file:./data/concursosdb`
   - **User Name:** `sa`
   - **Password:** (deixe em branco)
3. Clique em **Connect**
4. Execute SQL diretamente:

   ```sql
   SELECT * FROM APROVADOS;
   ```

## 📊 API Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/aprovados` | Lista todos os aprovados |
| GET | `/api/aprovados/{id}` | Busca aprovado por ID |
| GET | `/api/aprovados/buscar?nome={nome}` | Busca por nome |
| GET | `/api/aprovados/imagem/{nomeArquivo}` | Retorna imagem |
| POST | `/api/aprovados` | Cria novo aprovado |
| DELETE | `/api/aprovados/{id}` | Deleta aprovado |

## 💾 Banco de Dados

O sistema utiliza **H2 Database** em modo arquivo:

- **Localização:** `./data/concursosdb.mv.db`
- **Tipo:** Banco local persistente
- **Modo:** Arquivo (os dados são salvos permanentemente)

**Estrutura da tabela APROVADOS:**

```sql
CREATE TABLE aprovados (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    telefone VARCHAR(255) NOT NULL,
    concursos_aprovados TEXT NOT NULL,
    imagem_nome VARCHAR(255),
    imagem_path VARCHAR(255),
    data_cadastro TIMESTAMP NOT NULL
);
```

## 📁 Armazenamento de Imagens

As imagens enviadas são salvas em:

- **Diretório:** `./uploads/`
- **Formato:** Nome único (UUID) + extensão original
- **Exemplo:** `a1b2c3d4-e5f6-7890-abcd-ef1234567890.jpg`

## 🔧 Configurações

Arquivo: [src/main/resources/application.properties](src/main/resources/application.properties)

```properties
# Porta do servidor
server.port=8080

# Banco de dados H2
spring.datasource.url=jdbc:h2:file:./data/concursosdb
spring.datasource.username=sa
spring.datasource.password=

# Tamanho máximo de upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Diretório de uploads
upload.path=uploads
```

## 🎨 Capturas de Tela

### Formulário de Cadastro

- Design moderno com gradiente roxo
- Campos validados
- Upload de imagem com preview
- Máscara automática no telefone

### Lista de Aprovados

- Cards com informações completas
- Foto do aprovado
- Botão de exclusão
- Data de cadastro formatada

## 🐛 Troubleshooting

### Erro: "Port 8080 is already in use"

```bash
# Encontrar processo na porta 8080
lsof -i :8080

# Matar o processo
kill -9 <PID>
```

### Erro: "Java version not compatible"

```bash
# Verificar versão do Java
java -version

# Deve ser Java 17 ou superior
```

### Erro: "mvn: command not found"

```bash
# Instalar Maven
sudo apt install maven -y
```

### Imagens não aparecem

- Verifique se o diretório `uploads/` foi criado
- Verifique permissões da pasta
- Confirme que o backend está rodando

## 📝 Validações Implementadas

- **Nome:** Obrigatório, não pode ser vazio
- **E-mail:** Obrigatório, formato válido
- **Telefone:** Obrigatório, formato brasileiro
- **Concursos:** Obrigatório, não pode ser vazio
- **Imagem:** Opcional, máximo 10MB

## 🔐 Segurança

- Validação de entrada no backend
- Nomes de arquivos únicos (UUID)
- Proteção contra path traversal
- CORS configurado
- Validação de tipo de arquivo

## 📚 Recursos Adicionais

- **Spring Boot Docs:** <https://spring.io/projects/spring-boot>
- **H2 Database:** <http://www.h2database.com/>
- **Maven:** <https://maven.apache.org/>

## 👨‍💻 Desenvolvimento

Para modo de desenvolvimento com hot reload:

```bash
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

## 📄 Licença

Este projeto é de código aberto para fins educacionais.

## 🤝 Contribuições

Sinta-se à vontade para:

- Reportar bugs
- Sugerir melhorias
- Fazer fork do projeto

---

**Desenvolvido com ❤️ usando Java, Spring Boot e muito ☕**
