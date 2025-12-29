# 🌐 Como Disponibilizar a Aplicação para Acesso Externo

## Solução Recomendada: ngrok

O **ngrok** cria um túnel seguro da sua máquina local para a internet, gerando uma URL pública temporária.

---

## 🚀 Instalação e Uso do ngrok

### 1️⃣ Instalar o ngrok (Linux/Debian)

```bash
# Baixar ngrok
curl -s https://ngrok-agent.s3.amazonaws.com/ngrok.asc | sudo tee /etc/apt/trusted.gpg.d/ngrok.asc >/dev/null
echo "deb https://ngrok-agent.s3.amazonaws.com buster main" | sudo tee /etc/apt/sources.list.d/ngrok.list
sudo apt update
sudo apt install ngrok
```

**OU** instalar manualmente:

```bash
# Baixar e extrair
wget https://bin.equinox.io/c/bNyj1mQVY4c/ngrok-v3-stable-linux-amd64.tgz
tar -xvzf ngrok-v3-stable-linux-amd64.tgz
sudo mv ngrok /usr/local/bin/
```

### 2️⃣ Criar conta gratuita (opcional, mas recomendado)

1. Acesse: <https://dashboard.ngrok.com/signup>
2. Crie uma conta gratuita
3. Copie seu authtoken em: <https://dashboard.ngrok.com/get-started/your-authtoken>
4. Configure:

```bash
ngrok config add-authtoken SEU_TOKEN_AQUI
```

### 3️⃣ Iniciar a aplicação Spring Boot

```bash
cd /home/debian/p2/concursos_app
mvn spring-boot:run
```

Aguarde até ver:

```
🚀 APLICAÇÃO INICIADA COM SUCESSO!
```

### 4️⃣ Expor a aplicação com ngrok (em outro terminal)

```bash
ngrok http 8080
```

### 5️⃣ Obter a URL pública

Você verá algo assim:

```
ngrok

Session Status    online
Account           seu_email@gmail.com
Version           3.x.x
Region            United States (us)
Latency           45ms
Web Interface     http://127.0.0.1:4040
Forwarding        https://a1b2-c3d4-e5f6.ngrok-free.app -> http://localhost:8080

Connections       ttl     opn     rt1     rt5     p50     p90
                  0       0       0.00    0.00    0.00    0.00
```

✅ **A URL pública é:** `https://a1b2-c3d4-e5f6.ngrok-free.app`

### 6️⃣ Compartilhar o link

Envie essa URL para os stakeholders:

```
https://a1b2-c3d4-e5f6.ngrok-free.app
```

⚠️ **Importante:** Mantenha ambos os terminais abertos:

- Terminal 1: Aplicação Spring Boot rodando
- Terminal 2: ngrok rodando

---

## 📊 Monitorar Requisições

Acesse localmente o painel do ngrok:

```
http://localhost:4040
```

Você verá todas as requisições HTTP em tempo real.

---

## ⚙️ Versão Gratuita - Limitações

- ✅ **Funciona perfeitamente** para testes com stakeholders
- ⚠️ URL muda cada vez que você reinicia o ngrok
- ⚠️ Limite de 40 conexões/minuto
- ⚠️ Banner do ngrok aparece na primeira visita
- ⚠️ Sessão expira após 2 horas (precisa reiniciar)

### Para URL fixa (plano pago ~$8/mês)

```bash
ngrok http 8080 --domain=seu-dominio.ngrok.app
```

---

## 🛡️ Segurança Básica

### Adicionar senha de acesso

```bash
ngrok http 8080 --basic-auth="usuario:senha123"
```

Stakeholders precisarão inserir usuário e senha.

### Permitir apenas IPs específicos (plano pago)

```bash
ngrok http 8080 --cidr-allow="192.168.1.0/24"
```

---

## 🔄 Alternativa: localtunnel (mais simples, sem cadastro)

### Instalar

```bash
npm install -g localtunnel
```

### Usar

```bash
lt --port 8080 --subdomain concursos-app
```

URL gerada: `https://concursos-app.loca.lt`

⚠️ Menos estável que ngrok, mas não precisa de cadastro.

---

## 🚀 Alternativa: Cloudflare Tunnel (gratuito, ilimitado)

### Instalar

```bash
wget https://github.com/cloudflare/cloudflared/releases/latest/download/cloudflared-linux-amd64.deb
sudo dpkg -i cloudflared-linux-amd64.deb
```

### Usar (sem cadastro, temporário)

```bash
cloudflared tunnel --url http://localhost:8080
```

URL gerada automaticamente.

### Com conta Cloudflare (permanente)

1. Criar conta: <https://dash.cloudflare.com/sign-up>
2. Seguir: <https://developers.cloudflare.com/cloudflare-one/connections/connect-apps/>

---

## 📋 Passo a Passo Completo (Resumo)

### Terminal 1 - Iniciar aplicação

```bash
cd /home/debian/p2/concursos_app
mvn spring-boot:run
```

### Terminal 2 - Expor com ngrok

```bash
ngrok http 8080
```

### Compartilhar

```
Olá!
Acesse nossa aplicação de testes em:
https://[sua-url].ngrok-free.app

Observação: Na primeira vez pode aparecer um aviso do ngrok,
clique em "Visit Site" para continuar.
```

---

## 🔧 Troubleshooting

### Erro: "command not found: ngrok"

```bash
# Instalar novamente
sudo apt install ngrok
```

### Erro: "Port 8080 already in use"

```bash
# Matar processo na porta 8080
lsof -i :8080
kill -9 <PID>
```

### ngrok para de funcionar após 2 horas

```bash
# Reiniciar ngrok
# Pressione Ctrl+C e execute novamente:
ngrok http 8080
```

### URL muito longa/feia

Use plano pago ou cloudflare tunnel para domínio customizado.

---

## 💡 Recomendação

Para o seu caso (testes com stakeholders):

✅ **Use ngrok** - É o mais confiável e simples
✅ **Crie conta gratuita** - Remove limite de 40 req/min
✅ **Compartilhe a URL** - Válida por até 2 horas
✅ **Monitore no painel** - <http://localhost:4040>

Quando crescer, migre para:

- Render (gratuito, permanente)
- Railway (gratuito com limites)
- Heroku ($5-7/mês)
- VPS próprio (DigitalOcean, AWS, etc)

---

## 📞 Exemplo de Mensagem para Stakeholders

```
Olá equipe!

A aplicação de cadastro de aprovados está disponível para testes:

🔗 URL: https://abc123.ngrok-free.app

📝 Como usar:
1. Acesse o link
2. Se aparecer aviso do ngrok, clique em "Visit Site"
3. Preencha o formulário e teste as funcionalidades

⏰ Disponível: Hoje das 9h às 18h
💬 Dúvidas: Me enviem WhatsApp

Att,
Jadson
```

---

**Qualquer dúvida, é só perguntar!** 🚀
