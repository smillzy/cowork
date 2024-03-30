# Stylish-Backend-Java

### Deployment

1. Start MySQL Server
2. Import database:
    - `mysql -u <user_name> -p <stylish_db_name> < stylish_backend.sql`
3. Start a redis server in `localhost` at port `6379` (Optional, the server can still work without redis server)
4. Fill properties: `application.properties` for back-end
   or [set up your own configuration](https://spring.io/blog/2020/04/23/spring-tips-configuration)
    - Set `MYSQL_HOST`, `DB_USERNAME`, `DB_PASSWORD`, `DB_DATABASE` for MySQL server
    - Set `TOKEN_SECRET`, `TOKEN_EXPIRE` for jwt
    - Set `TAPPAY_PARTNER_KEY`, `TAPPAY_MERCHANT_ID` for cash flow (copy from your STYLiSH project)
    - Set `BCRYPT_SALT` for password encryption (Optional)
    - Set `CACHE_HOST` and `CACHE_PORT` based on your redis server (Optional)
    - Set `RATE_LIMIT_COUNT` and `RATE_LIMIT_WINDOW` for rate limiter (Optional)
    - Set `STYLISH_DOMAIN`, `STYLISH_PORT`, `STYLISH_SCHEME` for your server config (Optional)
5. Build `./mvnw clean package` (add `-DskipTests` if you want to skip tests)
6. Start server: `java -jar target/STYLiSH-0.0.1-SNAPSHOT.jar`
7. Clear Browser localStorage if needed. The same address will use the same space to records localStorage key-value
   pairs which might conflict with mine. (Optional)