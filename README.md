# 🚀 NotMess Backend - API REST

> Servidor Spring Boot para la gestión de inventario y control APPCC

---

## 📖 Descripción

Este es el backend de NotMess, una API REST desarrollada con Spring Boot que proporciona todos los servicios necesarios para gestionar productos, albaranes, controles APPCC, usuarios y categorías de un establecimiento de hostelería.

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una arquitectura **en capas** (Layered Architecture):

```
src/main/java/com/notmess/backend/
│
├── 🚪 controller/                    # Capa de Presentación (Endpoints REST)
│   ├── AlbaranController.java       # /api/albaranes
│   ├── AppccController.java         # /api/appcc
│   ├── AuthController.java          # /api/auth
│   ├── CategoriaController.java     # /api/categorias
│   ├── ProductoController.java      # /api/productos
│   └── UsuarioController.java       # /api/usuarios
│
├── 🧠 service/                       # Capa de Lógica de Negocio
│   ├── AlbaranService.java          # Lógica de albaranes
│   ├── AppccService.java            # Lógica de APPCC
│   ├── AuthService.java             # Autenticación y JWT
│   ├── CategoriaService.java        # Lógica de categorías
│   ├── ProductoService.java         # Lógica de productos
│   └── UsuarioService.java          # Lógica de usuarios
│
├── 💾 repository/                    # Capa de Acceso a Datos
│   ├── AlbaranRepository.java       # JPA Repository
│   ├── AppccRepository.java
│   ├── CategoriaProductoRepository.java
│   ├── LineaAlbaranRepository.java
│   ├── ProductoRepository.java
│   └── UsuarioRepository.java
│
├── 📦 model/                         # Entidades JPA
│   ├── Albaran.java                 # @Entity + @Table
│   ├── Appcc.java
│   ├── AppccLimpieza.java           # @Embeddable
│   ├── AppccTemperatura.java
│   ├── AppccProducto.java
│   ├── AppccFreidora.java
│   ├── CategoriaProducto.java
│   ├── LineaAlbaran.java
│   ├── Producto.java
│   └── Usuario.java
│
├── 🔐 security/                      # Configuración de Seguridad
│   ├── JwtAuthenticationFilter.java # Filtro de autenticación JWT
│   ├── JwtUtil.java                 # Utilidad para generar/validar JWT
│   └── SecurityConfig.java          # Configuración de Spring Security
│
├── 📋 dto/                           # Data Transfer Objects
│   ├── AlbaranRequestDTO.java       # DTOs de solicitud
│   ├── LoginRequestDTO.java
│   ├── ProductoRequestDTO.java
│   └── ...
│
├── ⚙️ config/                        # Configuración
│   ├── CorsConfig.java              # Configuración CORS
│   └── WebConfig.java               # Configuración Web
│
└── 🛠️ util/                          # Utilidades
    └── FileStorageUtil.java         # Manejo de archivos
```

---

## 🔑 Componentes Principales

### 1. 🚪 Controllers (Controladores)

**Función**: Exponer endpoints REST y manejar peticiones HTTP

**Responsabilidades**:
- Recibir peticiones HTTP
- Validar datos de entrada
- Llamar a servicios
- Devolver respuestas HTTP

**Ejemplo - ProductoController.java**:
```java
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }
    
    @PostMapping
    public ResponseEntity<Producto> createProducto(
            @RequestBody ProductoRequestDTO dto) {
        Producto producto = productoService.create(dto);
        return ResponseEntity.ok(producto);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
```

**Endpoints Disponibles**:
- `GET /api/productos` - Listar todos los productos
- `POST /api/productos` - Crear producto
- `PUT /api/productos/{id}` - Actualizar producto
- `DELETE /api/productos/{id}` - Eliminar producto
- `POST /api/productos/{id}/imagen` - Subir imagen

### 2. 🧠 Services (Servicios)

**Función**: Contener la lógica de negocio

**Responsabilidades**:
- Validar reglas de negocio
- Coordinar repositorios
- Gestionar transacciones
- Procesar datos

**Ejemplo - AlbaranService.java**:
```java
@Service
@Transactional
public class AlbaranService {
    
    @Autowired
    private AlbaranRepository albaranRepository;
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public Albaran createAlbaran(AlbaranRequestDTO dto) {
        // 1. Crear el albarán
        Albaran albaran = new Albaran();
        albaran.setTipo(dto.getTipo());
        albaran.setObservaciones(dto.getObservaciones());
        
        // 2. Crear líneas de albarán
        List<LineaAlbaran> lineas = new ArrayList<>();
        for (LineaAlbaranDTO lineaDto : dto.getLineas()) {
            Producto producto = productoRepository
                .findById(lineaDto.getIdProducto())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            
            LineaAlbaran linea = new LineaAlbaran();
            linea.setProducto(producto);
            linea.setCantidad(lineaDto.getCantidad());
            linea.setAlbaran(albaran);
            lineas.add(linea);
            
            // 3. Actualizar stock según tipo
            if (albaran.getTipo() == TipoAlbaran.ENTRADA) {
                producto.setCantidad(producto.getCantidad() + lineaDto.getCantidad());
            } else {
                producto.setCantidad(producto.getCantidad() - lineaDto.getCantidad());
            }
            productoRepository.save(producto);
        }
        
        albaran.setLineas(lineas);
        return albaranRepository.save(albaran);
    }
}
```

**Lógicas Clave**:
- **Albaranes**: Actualización automática de stock
- **Productos**: Validación de cantidades, manejo de imágenes
- **APPCC**: Validación de completado (todos los campos obligatorios)
- **Usuarios**: Encriptación de contraseñas, validación de roles

### 3. 💾 Repositories (Repositorios)

**Función**: Acceso a base de datos con JPA

**Características**:
- Herencia de `JpaRepository`
- Consultas personalizadas con `@Query`
- Consultas nativas SQL cuando es necesario

**Ejemplo - ProductoRepository.java**:
```java
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    // Consulta derivada (Spring Data genera SQL automáticamente)
    List<Producto> findByCategoria(CategoriaProducto categoria);
    
    // Consulta personalizada JPQL
    @Query("SELECT p FROM Producto p WHERE p.cantidad < :minimo")
    List<Producto> findProductosBajoStock(@Param("minimo") Double minimo);
    
    // Consulta nativa SQL (para operaciones complejas)
    @Modifying
    @Query(value = "DELETE FROM linea_albaran WHERE id_producto = :productoId", 
           nativeQuery = true)
    void deleteLineasAlbaranByProductoId(@Param("productoId") Long productoId);
}
```

### 4. 📦 Models (Entidades JPA)

**Función**: Mapear tablas de base de datos a objetos Java

**Características**:
- Anotaciones `@Entity`, `@Table`
- Relaciones `@OneToMany`, `@ManyToOne`, `@Embedded`
- Gestión automática de IDs con `@GeneratedValue`

**Ejemplo - Producto.java**:
```java
@Entity
@Table(name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private Double cantidad;
    
    @Column(nullable = false)
    private String medida;
    
    @Column(name = "imagen_url")
    private String imagenUrl;
    
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaProducto categoria;
    
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<LineaAlbaran> lineasAlbaran = new ArrayList<>();
    
    // Getters y Setters...
}
```

**Relaciones en el Sistema**:
```
Producto ─────< LineaAlbaran >───── Albaran
    │
    └───── CategoriaProducto

Appcc ────── AppccLimpieza (Embedded)
      ├──── AppccTemperatura (Embedded)
      ├──── AppccProducto (Embedded)
      └──── AppccFreidora (Embedded)

Usuario ──────── Rol (Enum)
```

### 5. 🔐 Security (Seguridad)

**Función**: Autenticación y autorización con JWT

**Componentes**:

#### JwtAuthenticationFilter
```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        // 1. Extraer token del header Authorization
        String token = extractToken(request);
        
        // 2. Validar token
        if (token != null && jwtUtil.validateToken(token)) {
            // 3. Extraer email del token
            String email = jwtUtil.getEmailFromToken(token);
            
            // 4. Cargar usuario y establecer autenticación
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken auth = 
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        
        filterChain.doFilter(request, response);
    }
}
```

#### JwtUtil
```java
@Component
public class JwtUtil {
    
    @Value("${jwt.secret}")
    private String secret;
    
    @Value("${jwt.expiration}")
    private Long expiration;
    
    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + expiration))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
```

#### SecurityConfig
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .cors().and()
            .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/productos/**").authenticated()
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
```

---

## 🗄️ Base de Datos PostgreSQL

### Estructura de Tablas

```sql
-- Categorías de productos
CREATE TABLE categorias (
    id_categoria SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE
);

-- Productos
CREATE TABLE productos (
    id_producto SERIAL PRIMARY KEY,
    nombre VARCHAR(200) NOT NULL,
    cantidad DECIMAL(10, 2) NOT NULL,
    medida VARCHAR(50) NOT NULL,
    imagen_url VARCHAR(500),
    id_categoria INTEGER REFERENCES categorias(id_categoria)
);

-- Albaranes
CREATE TABLE albaranes (
    id_albaran SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('ENTRADA', 'SALIDA', 'MERMA')),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observaciones TEXT
);

-- Líneas de albarán (relación muchos a muchos)
CREATE TABLE linea_albaran (
    id_linea SERIAL PRIMARY KEY,
    id_albaran INTEGER REFERENCES albaranes(id_albaran) ON DELETE CASCADE,
    id_producto INTEGER REFERENCES productos(id_producto) ON DELETE CASCADE,
    cantidad DECIMAL(10, 2) NOT NULL
);

-- APPCC (todos los campos embebidos en una tabla)
CREATE TABLE appcc (
    id_appcc SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    turno VARCHAR(20) NOT NULL CHECK (turno IN ('manana', 'tarde', 'noche')),
    completado BOOLEAN DEFAULT FALSE,
    
    -- Campos de limpieza (10 campos boolean)
    limpieza_mesas BOOLEAN,
    limpieza_suelos BOOLEAN,
    limpieza_banos BOOLEAN,
    -- ... más campos
    
    -- Campos de temperatura (8 campos decimal)
    temp_camara_frio DECIMAL(5, 2),
    temp_congelador DECIMAL(5, 2),
    -- ... más campos
    
    -- Campos de productos (16 campos)
    estado_lacteos VARCHAR(50),
    temp_lacteos DECIMAL(5, 2),
    estado_carnes VARCHAR(50),
    temp_carnes DECIMAL(5, 2),
    -- ... más campos
    
    -- Campos de freidoras (4 campos)
    temp_freidora_1 DECIMAL(5, 2),
    tpm_freidora_1 DECIMAL(5, 2),
    temp_freidora_2 DECIMAL(5, 2),
    tpm_freidora_2 DECIMAL(5, 2),
    
    -- Observaciones
    observaciones_generales TEXT,
    observaciones_limpieza TEXT,
    observaciones_temperatura TEXT,
    observaciones_producto TEXT,
    observaciones_freidoras TEXT
);

-- Usuarios
CREATE TABLE usuarios (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,  -- BCrypt hash
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('ADMIN', 'USUARIO')),
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Índices y Optimizaciones

```sql
-- Índices para mejorar rendimiento de búsquedas
CREATE INDEX idx_productos_categoria ON productos(id_categoria);
CREATE INDEX idx_linea_albaran_producto ON linea_albaran(id_producto);
CREATE INDEX idx_linea_albaran_albaran ON linea_albaran(id_albaran);
CREATE INDEX idx_albaranes_fecha ON albaranes(fecha DESC);
CREATE INDEX idx_appcc_fecha ON appcc(fecha DESC);
CREATE INDEX idx_usuarios_email ON usuarios(email);
```

---

## ⚙️ Configuración

### application.properties

```properties
# Configuración del servidor
server.port=8080

# Configuración de base de datos
spring.datasource.url=jdbc:postgresql://db:5432/notmess
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Configuración de JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000

# Configuración de archivos
file.upload-dir=/app/imagenes
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB

# Configuración de logging
logging.level.com.notmess=DEBUG
logging.level.org.springframework.security=DEBUG
```

### Variables de Entorno (.env)

```env
# Base de datos
DB_USER=notmess_user
DB_PASSWORD=tu_password_seguro

# JWT
JWT_SECRET=tu_clave_secreta_muy_larga_y_segura

# PostgreSQL
POSTGRES_DB=notmess
POSTGRES_USER=notmess_user
POSTGRES_PASSWORD=tu_password_seguro
```

---

## 🔄 Flujo de Peticiones

### Ejemplo: Crear un Albarán

```
1. Cliente (Flutter App)
   ↓
   POST /api/albaranes
   Body: {
     "tipo": "ENTRADA",
     "observaciones": "Pedido semanal",
     "lineas": [
       { "idProducto": 1, "cantidad": 10.0 },
       { "idProducto": 2, "cantidad": 5.0 }
     ]
   }
   ↓
2. JwtAuthenticationFilter
   - Valida token JWT
   - Establece autenticación
   ↓
3. AlbaranController
   - Recibe DTO
   - Llama a service
   ↓
4. AlbaranService
   - Valida datos
   - Crea albarán
   - Crea líneas de albarán
   - Actualiza stock de productos
   - Guarda todo en transacción
   ↓
5. AlbaranRepository & ProductoRepository
   - Ejecuta SQL INSERT
   - Ejecuta SQL UPDATE
   ↓
6. Base de Datos PostgreSQL
   - Persiste datos
   ↓
7. Respuesta
   ← AlbaranService devuelve albarán creado
   ← AlbaranController devuelve ResponseEntity 200
   ← Cliente recibe albarán con ID asignado
```

---

## 📡 API Endpoints

### 🔐 Autenticación

```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "admin@notmess.com",
  "password": "admin123"
}

Response 200:
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "usuario": {
    "idUsuario": 1,
    "nombre": "Admin",
    "email": "admin@notmess.com",
    "rol": "ADMIN"
  }
}
```

### 📦 Productos

```http
GET /api/productos
Authorization: Bearer {token}

Response 200:
[
  {
    "idProducto": 1,
    "nombre": "Tomates",
    "cantidad": 50.0,
    "medida": "kg",
    "imagenUrl": "/imagenes/tomates.jpg",
    "categoria": {
      "idCategoria": 1,
      "nombre": "Verduras"
    }
  }
]
```

```http
POST /api/productos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Lechugas",
  "cantidad": 20.0,
  "medida": "unidad",
  "idCategoria": 1
}
```

### 📋 Albaranes

```http
GET /api/albaranes
Authorization: Bearer {token}

Response 200:
[
  {
    "idAlbaran": 1,
    "tipo": "ENTRADA",
    "fecha": "2025-01-15T10:30:00",
    "observaciones": "Pedido semanal",
    "lineas": [
      {
        "idLinea": 1,
        "producto": { "idProducto": 1, "nombre": "Tomates" },
        "cantidad": 10.0
      }
    ]
  }
]
```

### 🧪 APPCC

```http
GET /api/appcc?fecha=2025-01-15
Authorization: Bearer {token}

Response 200:
[
  {
    "idAppcc": 1,
    "fecha": "2025-01-15",
    "turno": "manana",
    "completado": true,
    "limpiezaMesas": true,
    "tempCamaraFrio": 4.5,
    "estadoLacteos": "CORRECTO",
    // ... más campos
  }
]
```

---

## 🚀 Despliegue

### Con Docker Compose

El backend se despliega junto con PostgreSQL usando Docker Compose.

**docker-compose.yml**:
```yaml
version: '3.8'

services:
  db:
    image: postgres:15
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    volumes:
      - ./bbdd:/var/lib/postgresql/data
    ports:
      - "5432:5432"

  app:
    build:
      context: ./notmess-backend
    ports:
      - "8080:8080"
    env_file:
      - .env
    volumes:
      - ./imagenes:/app/imagenes
    depends_on:
      - db
```

**Dockerfile** (en notmess-backend/):
```dockerfile
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Comandos de Despliegue

```bash
# 1. Compilar el proyecto
cd notmess-backend
./mvnw clean package -DskipTests

# 2. Levantar servicios
cd ..
docker-compose up -d

# 3. Ver logs
docker-compose logs -f app

# 4. Detener servicios
docker-compose down
```

---

## 🧪 Testing

```java
@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void testGetAllProductos() throws Exception {
        mockMvc.perform(get("/api/productos")
                .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }
}
```

---

## 🛠️ Herramientas de Desarrollo

### Maven Commands

```bash
# Compilar
./mvnw clean compile

# Ejecutar tests
./mvnw test

# Empaquetar (crear JAR)
./mvnw clean package

# Ejecutar en desarrollo
./mvnw spring-boot:run

# Saltar tests al empaquetar
./mvnw clean package -DskipTests
```

---

## 📊 Logging y Monitoreo

```java
@Service
public class ProductoService {
    
    private static final Logger logger = LoggerFactory.getLogger(ProductoService.class);
    
    public Producto create(ProductoRequestDTO dto) {
        logger.info("Creando producto: {}", dto.getNombre());
        
        try {
            Producto producto = new Producto();
            // ... lógica
            
            logger.info("Producto creado exitosamente con ID: {}", producto.getIdProducto());
            return producto;
        } catch (Exception e) {
            logger.error("Error al crear producto: {}", e.getMessage(), e);
            throw e;
        }
    }
}
```

---

## ✅ Características Implementadas

- ✅ API REST completa con Spring Boot 3
- ✅ Autenticación JWT con Spring Security
- ✅ Persistencia con JPA/Hibernate y PostgreSQL
- ✅ Gestión de transacciones automática
- ✅ Manejo de archivos (imágenes externas)
- ✅ CORS configurado para desarrollo
- ✅ Validación de datos con Bean Validation
- ✅ Logging estructurado
- ✅ Dockerización completa

---

## 📚 Dependencias Principales

```xml
<!-- Spring Boot -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Spring Security + JWT -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>

<!-- PostgreSQL Driver -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>

<!-- Lombok (opcional, reduce boilerplate) -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
</dependency>
```

---
