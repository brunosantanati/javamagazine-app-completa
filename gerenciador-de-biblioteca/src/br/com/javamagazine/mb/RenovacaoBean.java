package br.com.javamagazine.mb;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.EmprestimoDao;
import br.com.javamagazine.dao.LivroDao;
import br.com.javamagazine.entidades.Emprestimo;
import br.com.javamagazine.entidades.Livro;
import br.com.javamagazine.interceptadores.Transacional;
import br.com.javamagazine.util.MensagemUtil;

@Named
@RequestScoped
public class RenovacaoBean {
	
	@Inject
	private LivroDao livroDao;
	private Integer idLivro;
	private List<Livro> livrosEmprestados;
	@Inject
	private EmprestimoDao emprestimoDao;
	private Emprestimo emprestimo = new Emprestimo();
	private String dataEmprestimoFormatada;
	private String dataPrevistaFormatada;
	DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public void alteradoLivroSelecionado(){
		if(idLivro != null){
			
			setarEmprestimo();
			dataEmprestimoFormatada = emprestimo.getDataEmprestimo().format(formatador);
			dataPrevistaFormatada = emprestimo.getDataPrevista().format(formatador);
			
		}else{
			limparCampos();
		}
	}

	@Transacional
	public void realizarRenovacao(){
		if(idLivro != null){
			setarEmprestimo();
			
			LocalDate dataAtual = LocalDate.now();
			if(dataAtual.isAfter(emprestimo.getDataPrevista())){
				
				MensagemUtil.addMensagemDeAviso("Aviso - ", "O empréstimo do livro não pode ser renovado "
								+ "pois a data prevista de devolução já passou.");
				
			}else{
				
				LocalDate novaData = emprestimo.getDataPrevista().plusDays(7);
				emprestimo.setDataPrevista(novaData);			
				emprestimoDao.atualizar(emprestimo);
				
				dataEmprestimoFormatada = emprestimo.getDataEmprestimo().format(formatador);
				dataPrevistaFormatada = emprestimo.getDataPrevista().format(formatador);
				
				livrosEmprestados = livroDao.listarLivrosEmprestados();
				
				MensagemUtil.addMensagemInformativa("Sucesso - ", "Renovação realizada com sucesso! "
								+ "A nova data para devolução é " + emprestimo.getDataPrevista().format(formatador));
				
			}
		}else{
			MensagemUtil.addMensagemDeAviso("Aviso - ", "Selecione um livro para renovar.");
		}
		
	}
	
	private void setarEmprestimo(){
		Livro livroSelecionado = getLivroSelecionado();
		Emprestimo emprestimoDoLivroSelecionado = getEmprestimoDoLivroSelecionado(livroSelecionado);
		emprestimo = emprestimoDoLivroSelecionado;
	}

	private Livro getLivroSelecionado(){
		for(Livro livro : livrosEmprestados){
			if(livro.getId() == idLivro){
				return livro;
			}
		}
		
		return null;
	}
	
	private Emprestimo getEmprestimoDoLivroSelecionado(Livro livroSelecionado){
		for(Emprestimo emprestimo : livroSelecionado.getEmprestimos()){
			if(emprestimo.getDataDevolucao() == null){
				return emprestimo;
			}
		}
		
		return null;
	}
	
	private void limparCampos(){
		emprestimo = new Emprestimo();
		dataEmprestimoFormatada = "";
		dataPrevistaFormatada = "";
	}
	
	public Integer getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}

	@Transacional
	public List<Livro> getLivrosEmprestados() {
		if(livrosEmprestados == null){
			livrosEmprestados = livroDao.listarLivrosEmprestados();
		}
		
		return livrosEmprestados;
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}
	
	public String getDataEmprestimoFormatada() {
		return dataEmprestimoFormatada;
	}

	public void setDataEmprestimoFormatada(String dataEmprestimoFormatada) {
		this.dataEmprestimoFormatada = dataEmprestimoFormatada;
	}

	public String getDataPrevistaFormatada() {
		return dataPrevistaFormatada;
	}

	public void setDataPrevistaFormatada(String dataPrevistaFormatada) {
		this.dataPrevistaFormatada = dataPrevistaFormatada;
	}
}
