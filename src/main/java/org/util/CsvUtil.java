package org.util;

import org.model.Produto;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    private static final String ARQUIVO_CSV = "produtos.csv";

    // 1. Método para SALVAR a lista completa no CSV (sobrescreve o arquivo)
    public static void salvarCsv(List<Produto> produtos) {
        // O try-with-resources fecha o writer automaticamente
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_CSV))) {
            for (Produto p : produtos) {
                // Monta a linha: ID,Nome,Preco,Quantidade
                String linha = String.format("%d,%s,%.2f,%d",
                        p.getId(),
                        p.getNome(),
                        p.getPreco(),
                        p.getQuantidade());

                writer.write(linha);
                writer.newLine(); // Pula para a próxima linha
            }
            System.out.println("CSV salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar o CSV: " + e.getMessage());
        }
    }

    // 2. Método para LER (Carregar) os produtos do CSV
    public static List<Produto> carregarCsv() {
        List<Produto> lista = new ArrayList<>();
        File arquivo = new File(ARQUIVO_CSV);

        // Se o arquivo não existir, retorna lista vazia
        if (!arquivo.exists()) {
            return lista;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Quebra a linha nas vírgulas
                String[] partes = linha.split(",");

                if (partes.length == 4) { // Garante que tem os 4 campos
                    try {
                        Long id = Long.parseLong(partes[0].trim());
                        String nome = partes[1].trim();
                        // Troca ponto por vírgula se necessário, mas o padrão CSV usa ponto em float
                        Double preco = Double.parseDouble(partes[2].trim().replace(",", "."));
                        Integer qtd = Integer.parseInt(partes[3].trim());

                        Produto p = new Produto(id, nome, preco, qtd);
                        lista.add(p);
                    } catch (NumberFormatException e) {
                        System.err.println("Ignorando linha inválida (erro de número): " + linha);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o CSV: " + e.getMessage());
        }

        return lista;
    }
}