# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**Projeto Renovar** is an environmental quality monitoring system (TCC — Trabalho de Conclusão de Curso, SI 18.2). It tracks measurements from IoT devices and displays them on an interactive Google Maps interface.

## Commands

### Frontend (Angular 19)
```bash
cd frontend
npm install          # install dependencies
npm run start        # dev server at http://localhost:4200
npm run build        # production build -> dist/RenovarWebApp
npm run watch        # build --watch (dev)
npm run lint         # ng lint
```

### Backend (Spring Boot 2, Java 8)
```bash
cd backend
mvn spring-boot:run                          # run with default profile
mvn spring-boot:run -Dspring.profiles.active=dev   # dev profile
mvn clean install                            # build + test
mvn test                                     # tests only
```

## Architecture

### System Flow
1. Frontend loads all IoT devices via `DispositivoService.findAll()` → displays markers on Google Maps (`HomePage`)
2. User clicks a device marker → navigates to `/coleta` with device state
3. `ColetaPage` fetches measurements filtered by device, indicator, and date range
4. Measurements are displayed in charts (Highcharts integration is present but commented out)

### Backend Structure (`com.renovar`)
- **`domain/`** — JPA entities: `Dispositivo` (IoT device), `Coleta` (measurement record), `Indicador` (measurement type/unit), `Coordenada`, `Usuario`
- **`dao/`** — Spring Data JPA repositories
- **`services/`** — Business logic; `DBService` handles database seeding
- **`resources/`** — REST controllers; `ColetaResource` has CORS enabled
- **`dto/`** — Request/response DTOs separate from entities
- **`config/`** — Profile-specific configs (`DevConfig`, `TestConfig`) and Swagger (`SweggerConfig`)

Database profiles:
- `dev`: `bd_renovar_dev`, `ddl-auto=none`, SQL logging on
- `prod`: `bd_renovar`, `ddl-auto=none`, SQL logging off

**Key REST endpoints:**
- `GET /coletas/dispositivo/{idDispositivo}`
- `GET /coletas/{idDispositivo}/{idIndicador}`
- `GET /coletas/intervalo/` (query params: `idDispositivo`, `idIndicador`, `dataInicial`, `dataFinal`)
- `GET /dispositivos`

Swagger UI is available at `/swagger-ui.html` in dev.

### Frontend Structure (`frontend/src`)
- **`app/app.routes.ts`** — Lazy-loaded standalone routes: `/home`, `/coleta`, `/login`, `/cadastro`, `/documentacao`
- **`app/app.config.ts`** — Global providers including the HTTP error interceptor
- **`pages/`** — One directory per route; each has `.ts`, `.html`, `.scss`, and a `.module.ts`
- **`services/domain/`** — `DispositivoService`, `ColetaService`, `IndicadorService` — all HTTP calls
- **`models/`** — TypeScript DTOs mirroring backend DTOs
- **`config/api.config.ts`** — `API_URL = 'http://150.162.6.197:8080'` (production server IP)
- **`interceptors/error-interceptor.ts`** — Global HTTP error handler

### Key Notes
- Frontend was recently migrated from Ionic 3/Angular 5 to Angular 19 standalone components. Some `.module.ts` files are remnants and may not be used.
- TypeScript strict mode is **off** (`strict: false`); `noImplicitReturns` is on.
- Highcharts stock chart code exists in `coleta.ts` but is commented out — intended for data visualization.
- The WordPress REST API (`http://lcqar.ufsc.br/novo/wp-json/wp/v2`) is used for the documentation page.