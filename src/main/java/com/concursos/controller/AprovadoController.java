package com.concursos.controller;

import com.concursos.model.Aprovado;
import com.concursos.service.AprovadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aprovados")
@CrossOrigin(origins = "*")
public class AprovadoController {

    @Autowired
    private AprovadoService aprovadoService;

    @Value("${upload.path:uploads}")
    private String uploadPath;

    @GetMapping
    public ResponseEntity<List<Aprovado>> listarTodos() {
        return ResponseEntity.ok(aprovadoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aprovado> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(aprovadoService.buscarPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criar(
            @Valid @RequestParam("nome") String nome,
            @Valid @RequestParam("email") String email,
            @Valid @RequestParam("telefone") String telefone,
            @Valid @RequestParam("concursosAprovados") String concursosAprovados,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem) {

        try {
            Aprovado aprovado = new Aprovado();
            aprovado.setNome(nome);
            aprovado.setEmail(email);
            aprovado.setTelefone(telefone);
            aprovado.setConcursosAprovados(concursosAprovados);

            Aprovado savedAprovado = aprovadoService.salvar(aprovado, imagem);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cadastro realizado com sucesso!");
            response.put("data", savedAprovado);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao salvar imagem: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("message", "Erro ao criar cadastro: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            aprovadoService.deletar(id);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Cadastro deletado com sucesso!");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Aprovado>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(aprovadoService.buscarPorNome(nome));
    }

    @GetMapping("/imagem/{nomeArquivo}")
    public ResponseEntity<Resource> obterImagem(@PathVariable String nomeArquivo) {
        try {
            Path arquivoPath = Paths.get(uploadPath).resolve(nomeArquivo).normalize();
            Resource resource = new UrlResource(arquivoPath.toUri());

            if (resource.exists() && resource.isReadable()) {
                String contentType = "image/jpeg"; // Default
                try {
                    contentType = java.nio.file.Files.probeContentType(arquivoPath);
                } catch (IOException e) {
                    // Use default
                }

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
