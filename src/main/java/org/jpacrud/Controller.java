package org.jpacrud;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.dao.ProdutoDAO;
import org.model.Produto;
import org.util.CsvUtil;

import java.util.ArrayList;

public class Controller {

    // --- Vínculos com a Tela (FXML) ---
    @FXML private TextField tfId;
    @FXML private TextField tfNome;
    @FXML private TextField tfPreco;
    @FXML private TextField tfQuantidade;

    @FXML private TableView<Produto> tabelaProdutos;

    @FXML private TableColumn<Produto, Long> colunaId;
    @FXML private TableColumn<Produto, String> colunaNome;
    @FXML private TableColumn<Produto, Double> colunaPreco;
    @FXML private TableColumn<Produto, Integer> colunaQuantidade;

    // Lista que conecta os dados à tabela
    private ObservableList<Produto> listaDeProdutos;

    private ProdutoDAO dao = new ProdutoDAO();

    // --- Método de Inicialização (Roda ao abrir a tela) ---
    @FXML
    public void initialize() {
        // 1. Configurar as colunas para lerem os atributos da classe Produto
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // 2. Inicializar a lista e ligar na tabela
        listaDeProdutos = FXCollections.observableArrayList();
        tabelaProdutos.setItems(listaDeProdutos);

        // 3. (Opcional) Preencher os campos ao clicar numa linha da tabela
        tabelaProdutos.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                preencherCampos(newSelection);
            }
        });
    }

    // --- Ações dos Botões ---

    @FXML
    void onAdicionar(ActionEvent event) {
        try {
            // Captura dados
            String nome = tfNome.getText();
            double preco = Double.parseDouble(tfPreco.getText());
            int qtd = Integer.parseInt(tfQuantidade.getText());

            // Cria novo produto (Simulando ID aleatório)
            Produto novoProduto = new Produto(null, nome, preco, qtd);
            dao.cadastrar(novoProduto); // O ‘ID’ é gerado aqui pelo Banco aleatóriamente

            // Adiciona na lista (A tabela atualiza sozinha)
            listaDeProdutos.add(novoProduto);

            // Salva a lista atualizada no CSV
            CsvUtil.salvarCsv(new ArrayList<>(listaDeProdutos));

            // TODO: Aqui entraria o código JPA: dao.salvar(novoProduto);

            limparCampos();
        } catch (Exception e) {
            e.printStackTrace(); // ←-- O COMANDO MÁGICO QUE MOSTRA O ERRO VERMELHO NO CONSOLE

            mostrarAlerta("Erro Crítico", "Ocorreu um erro: " + e.getMessage());
        }
    }

    @FXML
    void onAtualizar(ActionEvent event) {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            // Atualiza os dados do objeto selecionado
            produtoSelecionado.setNome(tfNome.getText());
            produtoSelecionado.setPreco(Double.parseDouble(tfPreco.getText()));
            produtoSelecionado.setQuantidade(Integer.parseInt(tfQuantidade.getText()));

            // Atualiza no banco de Dados
            dao.atualizar(produtoSelecionado);

            // Força a tabela a atualizar visualmente
            tabelaProdutos.refresh();

            // Salva a lista atualizada no CSV
            CsvUtil.salvarCsv(new ArrayList<>(listaDeProdutos));

            // TODO: Aqui entraria o código JPA: dao.atualizar(produtoSelecionado);

            limparCampos();
        } else {
            mostrarAlerta("Atenção", "Selecione um produto na tabela para atualizar.");
        }
    }

    @FXML
    void onExcluir(ActionEvent event) {
        Produto produtoSelecionado = tabelaProdutos.getSelectionModel().getSelectedItem();

        if (produtoSelecionado != null) {
            listaDeProdutos.remove(produtoSelecionado);

            dao.remover(produtoSelecionado);
            // Exclui a lista atualizada no CSV
            CsvUtil.salvarCsv(new ArrayList<>(listaDeProdutos));

            // TODO: Aqui entraria o código JPA: dao.remover(produtoSelecionado);

            limparCampos();
        } else {
            mostrarAlerta("Atenção", "Selecione um produto para excluir.");
        }
    }

    // --- Métodos Auxiliares ---

    private void limparCampos() {
        tfId.clear();
        tfNome.clear();
        tfPreco.clear();
        tfQuantidade.clear();
        tabelaProdutos.getSelectionModel().clearSelection();
    }

    private void preencherCampos(Produto produto) {
        tfId.setText(String.valueOf(produto.getId()));
        tfNome.setText(produto.getNome());
        tfPreco.setText(String.valueOf(produto.getPreco()));
        tfQuantidade.setText(String.valueOf(produto.getQuantidade()));
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


}