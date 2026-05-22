package br.com.fiap3espg.autoescola3espg.service;

import br.com.fiap3espg.autoescola3espg.domain.instrutor.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InstrutorService {
    private final InstrutorRepository repository;

//    public InstrutorService(InstrutorRepository repository) {
//        this.repository = repository;
//    }

    @Transactional
    public DadosDetalhamentoInstrutor cadastrarInstrutor(DadosCadastroInstrutor dados) {
        Instrutor instrutor = new Instrutor(dados);
        Instrutor saved = repository.save(instrutor); // saved para garantir o fluxo
        return new DadosDetalhamentoInstrutor(saved);
    }

    public Page<DadosListagemInstrutor> listarInstrutores(Pageable paginacao) {
        return repository.findAllByAtivoTrue(paginacao).map(DadosListagemInstrutor::new);
    }

    public DadosDetalhamentoInstrutor detalharInstrutor(Long id) {
        Instrutor instrutor = repository
                .findById(id)
                .orElseThrow(() -> new InstrutorNotFoundException("ID do instrutor informado não existe!"));
        // aqui houve criação de variável, se precisar de memória é ruim
        return new DadosDetalhamentoInstrutor(instrutor);
    }

    @Transactional // vai fazer transação no banco de dados
    public DadosDetalhamentoInstrutor atualizarInstrutor(DadosAtualizacaoInstrutor dados) {
        Instrutor instrutor = repository
                .findById(dados.id())
                .orElseThrow(() -> new InstrutorNotFoundException("ID do instrutor informado não existe!"));
        instrutor.atualizarInformacoes(dados);
        // Criado para garantir o fluxo, caso ele retorne sem atualizar
        Instrutor saved = repository.save(instrutor);
        return new DadosDetalhamentoInstrutor(saved);
    }

    @Transactional
    public void excluirInstrutor(Long id) {
        Instrutor instrutor = repository
                .findById(id)
                .orElseThrow(() -> new InstrutorNotFoundException("ID do instrutor informado não existe!"));
        instrutor.excluir();
        repository.save(instrutor);
    }
}
