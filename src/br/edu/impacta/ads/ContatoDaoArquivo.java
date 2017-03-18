package br.edu.impacta.ads;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ContatoDaoArquivo implements IContatoDao {
	private String nomeArquivo;
	
	public ContatoDaoArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public List<Contato> lerTodos() {
		List<Contato> result = new ArrayList<>();
		try{
			FileReader fr = new FileReader(nomeArquivo);
			BufferedReader br = new BufferedReader(fr);
			String linha;
			while ((linha = br.readLine()) != null) {
				String[] info = linha.split(";");
				String nome = info [0];
				String telefone = info[1];
				Contato c = new Contato(nome, telefone);
				result.add(c);
			}
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado!");
		} catch (IOException e) {
			System.out.println("Erro de leitura");
		}
		return result;
	}
	
	public void gravar(List<Contato> contatos) {
		try {
			PrintWriter pw = new PrintWriter(nomeArquivo);
			for (Contato c : contatos) {
				pw.println(c.getNome() + ";" + c.getTelefone());
			}
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado!");
		}
	}
	
	@Override
	public List<Contato> buscar(String nome) {
		List<Contato> contatos = lerTodos();
		List<Contato> resultado = new ArrayList<>();
		for (Contato c : contatos) {
			if (nome.equals(c.getNome())) {
				resultado.add(c);
			}
		}
		return resultado;
	}
	
	@Override
	public void inserir(Contato c) {
		List<Contato> contatos = lerTodos();
		contatos.add(c);
		gravar(contatos);
	}
	
	@Override
	public boolean existe(Contato c) {
		List<Contato> contatos = lerTodos();
		return contatos.contains(c);
	}
	
}
