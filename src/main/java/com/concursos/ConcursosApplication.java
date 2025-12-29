package com.concursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConcursosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConcursosApplication.class, args);
        System.out.println("\n\n========================================");
        System.out.println("🚀 APLICAÇÃO INICIADA COM SUCESSO!");
        System.out.println("========================================");
        System.out.println("📝 Frontend: http://localhost:8080");
        System.out.println("🔗 API: http://localhost:8080/api/aprovados");
        System.out.println("💾 Console H2: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}
