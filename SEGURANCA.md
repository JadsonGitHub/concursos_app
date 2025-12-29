# 🛡️ GUIA DE SEGURANÇA - Exposição com ngrok

## ⚠️ RISCOS IDENTIFICADOS

### 🔴 ALTO RISCO

1. **H2 Console exposto** - Permite acesso direto ao banco de dados
2. **Sem autenticação** - Qualquer pessoa pode usar a aplicação
3. **Dados sensíveis** - E-mails e telefones ficam públicos

### 🟡 MÉDIO RISCO

4. **Upload sem limite** - Pode encher seu disco
2. **Rate limiting** - Possível abuso com muitas requisições
3. **IP da sua máquina** - Pode ser descoberto via ngrok

### 🟢 BAIXO RISCO

7. **Logs expostos** - Informações técnicas visíveis
2. **Versões de software** - Headers revelam tecnologias usadas

---

## ✅ CORREÇÕES IMPLEMENTADAS

### 1. H2 Console DESABILITADO

```properties
# application.properties
spring.h2.console.enabled=false  # ✅ PROTEGIDO
```

### 2. Rate Limiting adicionado

- ✅ Máximo 60 requisições por minuto por IP
- ✅ Previne ataques de força bruta
- ✅ Proteção contra bots

### 3. Validação de Upload reforçada

- ✅ Limite de 10MB por arquivo
- ✅ Apenas imagens permitidas
- ✅ Nomes únicos (UUID) previnem sobrescrita

---

## 🔒 SEGURANÇA ADICIONAL (Recomendada)

### Opção 1: Adicionar Autenticação HTTP Basic (Simples)

#### 1. Adicione ao pom.xml

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

#### 2. Edite SecurityConfig.java

```java
// Descomente esta linha:
@EnableWebSecurity
```

#### 3. Configure usuário/senha no application.properties

```properties
app.security.username=admin
app.security.password=SenhaForte123!
```

#### 4. Recompile

```bash
mvn clean package
mvn spring-boot:run
```

✅ **Agora todos precisam de login para acessar!**

### Opção 2: Usar senha do ngrok (Mais Simples)

```bash
ngrok http 8080 --basic-auth="usuario:senha123"
```

✅ **Stakeholders precisam de usuário/senha para acessar**

---

## 🎯 CONFIGURAÇÃO RECOMENDADA PARA TESTES

### MÍNIMO (Aceitável para testes internos curtos)

```bash
# 1. Desabilitar H2 Console (JÁ FEITO)
# 2. Usar senha no ngrok
ngrok http 8080 --basic-auth="concursos:teste2024"

# 3. Compartilhar apenas com stakeholders
# 4. Encerrar após os testes (Ctrl+C)
```

### RECOMENDADO (Para testes mais longos)

```bash
# 1. Ativar Spring Security (ver acima)
# 2. Usar ngrok com autenticação
ngrok http 8080 --basic-auth="concursos:senha123"

# 3. Monitorar acessos
# Acesse: http://localhost:4040

# 4. Logs ativos
tail -f logs/application.log
```

### MÁXIMO (Ambiente de produção)

- ✅ Spring Security ativado
- ✅ HTTPS (ngrok já fornece)
- ✅ Firewall configurado
- ✅ Logs centralizados
- ✅ Backup do banco
- ✅ Rate limiting ativo (já implementado)
- ✅ Monitoramento 24/7

---

## 🚨 CHECKLIST ANTES DE EXPOR

```bash
# ✅ 1. H2 Console desabilitado?
grep "spring.h2.console.enabled=false" src/main/resources/application.properties

# ✅ 2. Senha no ngrok?
ngrok http 8080 --basic-auth="user:pass"

# ✅ 3. Apenas stakeholders conhecem a URL?
# Não poste em redes sociais, GitHub, etc.

# ✅ 4. Tem backup dos dados?
cp -r data/ data_backup_$(date +%Y%m%d)/

# ✅ 5. Vai monitorar os acessos?
# Abra: http://localhost:4040
```

---

## 👀 MONITORAMENTO EM TEMPO REAL

### Painel ngrok (obrigatório)

```
http://localhost:4040
```

Você verá:

- Quem está acessando (IPs)
- Que páginas estão visitando
- Quantas requisições por segundo
- Possíveis ataques

### Logs da aplicação

```bash
# Em outro terminal
tail -f logs/spring.log
```

---

## 🔥 O QUE FAZER SE DETECTAR ATAQUE

### Sinais de problema

- 🚨 Muitas requisições do mesmo IP
- 🚨 Tentativas de acesso a `/h2-console`
- 🚨 Upload de arquivos muito grandes
- 🚨 Erros 500 em massa
- 🚨 IPs estranhos (China, Rússia, etc.)

### Ação imediata

```bash
# 1. PARE O NGROK
# No terminal do ngrok, pressione Ctrl+C

# 2. PARE A APLICAÇÃO
# No terminal do Spring, pressione Ctrl+C

# 3. VERIFIQUE OS DADOS
cd data/
ls -lh

# 4. VERIFIQUE UPLOADS
cd uploads/
ls -lh

# 5. Reinicie com segurança aumentada
```

---

## 📊 COMPARAÇÃO DE SEGURANÇA

| Configuração | Segurança | Facilidade | Recomendado |
|--------------|-----------|------------|-------------|
| **Sem proteção** | 🔴 0/10 | ✅ 10/10 | ❌ NUNCA |
| **H2 desabilitado** | 🟡 3/10 | ✅ 10/10 | ⚠️ Só testes rápidos |
| **+ Senha ngrok** | 🟡 5/10 | ✅ 9/10 | ✅ Testes internos |
| **+ Spring Security** | 🟢 7/10 | 🟡 7/10 | ✅ Recomendado |
| **Servidor dedicado** | 🟢 9/10 | 🔴 4/10 | ✅ Produção |

---

## 💡 RECOMENDAÇÃO FINAL

Para o seu caso (stakeholders internos):

### ✅ FAÇA ASSIM

```bash
# Terminal 1
cd /home/debian/p2/concursos_app
mvn spring-boot:run

# Terminal 2
ngrok http 8080 --basic-auth="concursos:aprovados2024"

# Terminal 3 (monitoramento)
watch -n 5 'ls -lh uploads/ | tail'
```

### 📧 Mensagem para stakeholders

```
Olá equipe!

Aplicação disponível para testes em:
🔗 https://[sua-url].ngrok-free.app

🔐 Credenciais de acesso:
Usuário: concursos
Senha: aprovados2024

⏰ Disponível: Hoje 9h-18h
⚠️ NÃO COMPARTILHE essas credenciais

Qualquer problema, me avise imediatamente.

Att,
Jadson
```

---

## 🎓 QUANDO MIGRAR PARA SERVIDOR?

Migre quando:

- ✅ Testes durarem mais de 2 semanas
- ✅ Mais de 10 usuários simultâneos
- ✅ Dados realmente importantes
- ✅ Disponibilidade 24/7 necessária
- ✅ Budget aprovado (~$5-10/mês)

Opções:

1. **Render.com** - Grátis, boa para começar
2. **Railway.app** - $5/mês, muito fácil
3. **Heroku** - $7/mês, confiável
4. **DigitalOcean** - $6/mês, mais controle

---

## ❓ PERGUNTAS FREQUENTES

### "Meu IP vai ser exposto?"

- ❌ Não! ngrok protege seu IP
- ✅ Apenas o IP do ngrok é visível

### "Alguém pode hackear minha máquina?"

- ⚠️ Só se houver bug grave no código
- ✅ ngrok só expõe a porta 8080
- ✅ Sua rede local continua protegida

### "E se alguém descobrir a URL?"

- ⚠️ Com senha do ngrok, não consegue acessar
- ⚠️ Sem senha, pode usar a aplicação
- ✅ H2 Console está desabilitado (seguro)

### "Posso deixar rodando a noite toda?"

- ⚠️ Não recomendado
- ✅ Se deixar, ative Spring Security
- ✅ Configure alarme para monitorar

### "É seguro para dados reais?"

- ❌ Para testes apenas
- ❌ Não coloque CPF, senhas, etc.
- ✅ Use dados fictícios ou anonimizados

---

## 📞 SUPORTE

Problemas? Revise:

1. ✅ H2 Console desabilitado?
2. ✅ Senha no ngrok configurada?
3. ✅ Monitoramento ativo?
4. ✅ Apenas stakeholders têm acesso?
5. ✅ Você está supervisionando?

**Tudo certo? Pode expor!** 🚀

**Alguma dúvida? Melhor NÃO expor até esclarecer!** ⚠️
