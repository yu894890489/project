# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is RuoYi-Vue-Plus 5.4.1, a multi-tenant management system based on Spring Boot 3.4 and Vue 3 with TypeScript. It's a comprehensive enterprise-level admin framework with workflow capabilities.

## Common Development Commands

### Backend (Java/Spring Boot)
```bash
# Navigate to backend directory
cd backend

# Clean build and compile (skipping tests)
mvn clean compile -DskipTests -pl ruoyi-admin -am

# Run the main application
mvn spring-boot:run -pl ruoyi-admin

# Package for production
mvn clean package -DskipTests

# Run tests
mvn test
```

### Frontend (Vue 3 + TypeScript)
```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install --registry=https://registry.npmmirror.com

# Start development server
npm run dev

# Build for production
npm run build:prod

# Build for development
npm run build:dev

# Lint code
npm run lint:eslint

# Fix linting issues
npm run lint:eslint:fix

# Format code
npm run prettier
```

### Docker Deployment
```bash
# Start all services using docker compose
cd backend/script/docker
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down
```

## Architecture Overview

### Backend Architecture
- **Main Application**: `DromaraApplication.java` - Spring Boot entry point running on port 8082
- **Module Structure**: 
  - `ruoyi-admin` - Main application module with web layer
  - `ruoyi-common` - Shared utilities and configurations (17 sub-modules)
  - `ruoyi-modules` - Business modules (system, workflow, project, demo, generator, job)
  - `ruoyi-extend` - Extensions (monitor-admin, snailjob-server)

- **Key Technologies**:
  - Spring Boot 3.4.7 with Java 21
  - MyBatis-Plus 3.5.12 for ORM
  - Sa-Token for authentication/authorization
  - Redisson for Redis operations
  - Warm-Flow 1.7.4 for workflow engine
  - Dynamic datasource support
  - Multi-tenancy enabled by default

### Frontend Architecture
- **Framework**: Vue 3.5.13 + TypeScript + Element Plus 2.9.8
- **Build Tool**: Vite 6.3.2
- **State Management**: Pinia 3.0.2
- **Routing**: Vue Router 4.5.0
- **HTTP Client**: Axios 1.8.4
- **Main Entry**: `src/main.ts` - Application bootstrap
- **Development Server**: Runs on port 80, proxies API calls to backend port 8082

### Key Business Modules
- **System Management**: Users, roles, permissions, departments, menus
- **Workflow Engine**: Process definitions, instances, tasks (Warm-Flow)
- **Project Management**: Custom project and demand tracking modules
- **Multi-tenancy**: Tenant and package management
- **File Storage**: MinIO integration for distributed file storage
- **Monitoring**: Spring Boot Admin integration

### Database Schema
- Primary database scripts in `backend/script/sql/`
- Main schema: `ry_vue_5.X.sql`
- Workflow schema: `ry_workflow.sql`
- Project schema: `project.sql`
- Job scheduler schema: `ry_job.sql`

### Configuration Notes
- Backend configuration: `backend/ruoyi-admin/src/main/resources/application*.yml`
- Frontend proxy configuration in `frontend/vite.config.ts`
- Multi-environment support (dev, prod, local)
- Multi-tenancy is enabled by default
- Workflow engine (Warm-Flow) is enabled with UI designer

### Development Workflow
1. Backend runs on port 8082 via Spring Boot
2. Frontend dev server runs on port 80 with Vite
3. API calls proxy from frontend to backend automatically
4. Database initialization via SQL scripts in `script/sql/`
5. Docker compose available for full environment setup

### Testing
- Backend: Maven Surefire plugin with profile-based test execution
- Frontend: Vitest 3.1.2 for unit testing
- Single test execution: Use Maven `-Dtest=ClassName` parameter

### Build Outputs
- Backend: JAR files in respective `target/` directories
- Frontend: Static files in `dist/` directory
- Docker images: Multi-stage builds supported via provided Dockerfiles