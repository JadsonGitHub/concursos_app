package com.concursos.service;

import com.concursos.model.Aprovado;
import com.concursos.repository.AprovadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class AprovadoService {

    @Autowired
    private AprovadoRepository aprovadoRepository;

    @Value("${upload.path:uploads}")
    private String uploadPath;

    public List<Aprovado> listarTodos() {
        return aprovadoRepository.findAll();
    }

    public Aprovado buscarPorId(Long id) {
        return aprovadoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aprovado não encontrado com id: " + id));
    }

    public Aprovado salvar(Aprovado aprovado, MultipartFile imagem) throws IOException {
        if (imagem != null && !imagem.isEmpty()) {
            String nomeArquivo = salvarImagem(imagem);
            aprovado.setImagemNome(nomeArquivo);
            aprovado.setImagemPath(uploadPath + "/" + nomeArquivo);
        }
        return aprovadoRepository.save(aprovado);
    }

    public void deletar(Long id) {
        Aprovado aprovado = buscarPorId(id);

        // Deletar imagem se existir
        if (aprovado.getImagemPath() != null) {
            try {
                Path path = Paths.get(aprovado.getImagemPath());
                Files.deleteIfExists(path);
            } catch (IOException e) {
                // Log error but don't fail
                System.err.println("Erro ao deletar imagem: " + e.getMessage());
            }
        }

        aprovadoRepository.deleteById(id);
    }

    public List<Aprovado> buscarPorNome(String nome) {
        return aprovadoRepository.findByNomeContainingIgnoreCase(nome);
    }

    private String salvarImagem(MultipartFile imagem) throws IOException {
        // Criar diretório se não existir
        Path uploadDir = Paths.get(uploadPath);
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Gerar nome único para o arquivo
        String extensao = "";
        String nomeOriginal = imagem.getOriginalFilename();
        if (nomeOriginal != null && nomeOriginal.contains(".")) {
            extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
        }
        String nomeArquivo = UUID.randomUUID().toString() + extensao;

        // Salvar arquivo
        Path destinoArquivo = uploadDir.resolve(nomeArquivo);
        Files.copy(imagem.getInputStream(), destinoArquivo, StandardCopyOption.REPLACE_EXISTING);

        return nomeArquivo;
    }
}
