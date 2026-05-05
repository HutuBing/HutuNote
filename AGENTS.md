# AGENTS.md

## Cursor Cloud specific instructions

### Architecture Overview

HutuNote is a full-stack spaced-repetition note-taking app:
- **Backend**: Spring Boot 2.5.5 (Java 8) on port 8088 with context path `/hutunote`
- **Frontend**: Vue.js 2 SPA (Vue CLI 4.5, Element UI) on port 8080
- **Database**: MySQL 8.0 with database `dev`, credentials `root`/`root`

### Running Services

**MySQL** (must be started first):
```bash
mysqld --user=mysql --datadir=/var/lib/mysql &
```

**Spring Boot backend**:
```bash
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
cd /workspace && mvn spring-boot:run -pl hutunote -Pdev
```

**Vue frontend dev server**:
```bash
export NVM_DIR="$HOME/.nvm" && [ -s "$NVM_DIR/nvm.sh" ] && . "$NVM_DIR/nvm.sh" && nvm use 14
cd /workspace/hutunote-vue && npm run serve
```

### Key Gotchas

- **Java version**: Must use Java 8 (`JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64`). The project does not compile or run on newer JDKs.
- **Node.js version**: Must use Node 14 via nvm. `node-sass@4.x` requires Node 14 (does not work on Node 16+).
- **MySQL table names**: The MyBatis-Plus entity annotations use UPPERCASE table names (`TB_NOTE`, `TB_NOTE_LEARNING_TASK`). Linux MySQL is case-sensitive by default, so tables must be created with uppercase names. The `init.sql` in the repo uses lowercase; during setup, create tables with uppercase names.
- **Proxy config**: The Vue dev server proxies `/hutunote` to `http://localhost:8088` (the backend). This is configured in `vue.config.js`.
- **File upload path**: Dev profile uses `/tmp/hutunote/files/` for file storage. Ensure this directory exists.
- **Pre-existing lint errors**: The codebase has ~7 pre-existing ESLint errors in Vue source files (duplicate keys in component options). These are not introduced by dev environment setup.

### Commands Reference

| Action | Command |
|--------|---------|
| Build backend | `mvn clean install -DskipTests` (from repo root) |
| Run backend tests | `mvn test -pl hutunote -Pdev` |
| Run frontend lint | `cd hutunote-vue && npx vue-cli-service lint` |
| Start frontend dev server | `cd hutunote-vue && npm run serve` |
| API documentation | http://localhost:8088/hutunote/doc.html (Knife4j/Swagger) |
