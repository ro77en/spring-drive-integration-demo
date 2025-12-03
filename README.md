# Drive Integration Demo

Este projeto é uma API REST desenvolvida em **Spring Boot** (Java 21) para integração com serviços de armazenamento em nuvem. 

O projeto utiliza os padrões de projeto **Strategy** e **Factory** para permitir a fácil extensão para múltiplos provedores (Google Drive, OneDrive, etc.) mantendo o código desacoplado.

Atualmente, suporta:
- [x] **Google Drive** (Listagem de arquivos)
- [ ] **OneDrive** (Em breve/Planejado)

---

## 🚀 Pré-requisitos

* Java 21
* Maven
* Uma conta Google (para gerar as credenciais)

---

## 🔑 Configuração do Google Drive (Passo a Passo)

Para que a integração funcione, você precisa gerar uma **Service Account** (Conta de Serviço) no Google Cloud Platform.

### 1. Criar Projeto e Habilitar API
1. Acesse o [Google Cloud Console](https://console.cloud.google.com/).
2. Crie um novo projeto.
3. No menu lateral, vá em **APIs e Serviços** > **Biblioteca**.
4. Pesquise por **"Google Drive API"**.
5. Clique em **Ativar**.

### 2. Criar a Service Account (Robô)
1. No menu lateral, vá em **IAM e Admin** > **Contas de serviço**.
2. Clique em **+ CRIAR CONTA DE SERVIÇO**.
3. Dê um nome para a conta (ex: `drive-integration-bot`) e clique em **Criar e Continuar**.
4. (Opcional) Em papéis, você pode colocar "Editor", mas para esta integração apenas a chave é necessária. Clique em **Concluir**.

### 3. Gerar o arquivo `credentials.json`
1. Na lista de contas de serviço, clique no **e-mail** da conta que você acabou de criar.
2. Vá até a aba **Chaves**.
3. Clique em **Adicionar Chave** > **Criar nova chave**.
4. Selecione o tipo **JSON** e clique em **Criar**.
5. Um arquivo será baixado automaticamente para o seu computador.

### 4. Instalar no Projeto
1. Renomeie o arquivo baixado para `credentials.json`.
2. Mova este arquivo para a pasta raíz do projeto.

**⚠️ Segurança:** Certifique-se de adicionar `credentials.json` ao seu `.gitignore` para não expor suas chaves privadas em repositórios públicos.

### 5. Dar Permissão na Pasta (IMPORTANTE)
A Service Account tem seu próprio Google Drive (que começa vazio). Para ela ver seus arquivos, você precisa compartilhar a pasta com ela:

1. Abra seu arquivo `credentials.json` e copie o valor do campo `"client_email"`.
2. Vá até o seu Google Drive no navegador.
3. Clique com o botão direito na pasta que deseja acessar > **Compartilhar**.
4. Cole o e-mail da Service Account e dê permissão de **Editor** ou **Leitor**.
5. Copie o **ID da Pasta** (é o código alfanumérico que aparece na URL da pasta no navegador).

---

## 🛠️ Como Rodar

1. Compile o projeto:
```bash
mvn clean install
````

2. Execute a aplicação:
```bash
mvn spring-boot:run
```

---

## 📡 Utilização da API

### Listar Arquivos

Retorna uma lista de arquivos contidos em uma pasta específica do provedor de nuvem selecionado.

**Rota:** `GET /drive/{provider}/files`

| Parâmetro | Local | Tipo | Descrição |
| :--- | :--- | :--- | :--- |
| `provider` | Path | `Enum` | O provedor de armazenamento. Valor suportado: `GOOGLE_DRIVE`. |
| `folderId` | Query | `String` | O ID único da pasta no provedor (o código que aparece na URL do navegador). |

### Exemplo de Requisição

**URL:**
```http
http://localhost:8080/drive/GOOGLE_DRIVE/files?folderId=1F97h0VPlkCwOOP0YLw3sYTJlWKarZX4C
```

**cURL:**
```bash
curl -X GET "http://localhost:8080/drive/GOOGLE_DRIVE/files?folderId=SEU_ID_DA_PASTA_AQUI"
```

### Exemplo de Resposta (200 OK)
```json
[
    {
        "id": "1kBy10xz9panvaQC9BHdeB1_IkGLZPFAE",
        "fileName": "Projeto_Sincronizacao.pdf",
        "webViewLink": "[https://drive.google.com/file/d/1kBy10xz9.../view?usp=drivesdk](https://drive.google.com/file/d/1kBy10xz9.../view?usp=drivesdk)",
        "mimeType": "application/pdf",
        "bytes": 45021,
        "createdAt": "2025-12-03",
        "driveProvider": "GOOGLE_DRIVE"
    },
    {
        "id": "1F97h0VPlkCwOOP0YLw3sYTJlWKarZX4C",
        "fileName": "Planilha de Custos",
        "webViewLink": "[https://docs.google.com/spreadsheets/d/1F97h0.../edit?usp=drivesdk](https://docs.google.com/spreadsheets/d/1F97h0.../edit?usp=drivesdk)",
        "mimeType": "application/vnd.google-apps.spreadsheet",
        "bytes": 0,
        "createdAt": "2025-11-28",
        "driveProvider": "GOOGLE_DRIVE"
    }
]
```
