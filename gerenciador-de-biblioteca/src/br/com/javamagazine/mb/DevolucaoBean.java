package br.com.javamagazine.mb;

import java.math.BigDecimal;
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
import br.com.javamagazine.util.FormatadorDeNumeros;
import br.com.javamagazine.util.MensagemUtil;

@Named
@RequestScoped
public class DevolucaoBean {
	
	@Inject
	private LivroDao livroDao;
	private Integer idLivro;
	private List<Livro> livrosEmprestados;
	@Inject
	private EmprestimoDao emprestimoDao;
	private Emprestimo emprestimo = new Emprestimo();
	private String dataEmprestimoFormatada;
	private String dataPrevistaFormatada;

	private String multa = FormatadorDeNumeros.formatarBigDecimalComoMoeda(new BigDecimal(0.0d));
	
	public void alteradoLivroSelecionado(){
		if(idLivro != null){
			setarEmprestimo();
			
			DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			dataEmprestimoFormatada = emprestimo.getDataEmprestimo().format(formatador);
			dataPrevistaFormatada = emprestimo.getDataPrevista().format(formatador);
			
			LocalDate dataAtual = LocalDate.now();
			if(dataAtual.isAfter(emprestimo.getDataPrevista())){
				multa = FormatadorDeNumeros.formatarBigDecimalComoMoeda(new BigDecimal(15.0d));
			}
		}else{
			limparCampos();
		}
	}

	@Transacional
	public void realizarDevolucao(){
		setarEmprestimo();
		emprestimo.setDataDevolucao(LocalDate.now());
		
		emprestimoDao.atualizar(emprestimo);
		
		livrosEmprestados = livroDao.listarLivrosEmprestados();
		
		limparCampos();
		
		MensagemUtil.addMensagemInformativa("Sucesso - ", "Devolução realizada com sucesso!");
	}
	
	private void setarEmprestimo(){
		Livro livroSelecionado = getLivroSelecionado();
		Emprestimo emprestimoDoLivroSelecionado = getEmprestimoDoLivroSelecionado(livroSelecionado);
		emprestimo = emprestimoDoLivroSelecionado;
	}

	private Livro getLivroSelecionado(){
		for(Livro livro : getLivrosEmprestados()){
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
		multa = FormatadorDeNumeros.formatarBigDecimalComoMoeda(new BigDecimal(0.0d));
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
	
	public String getMulta() {
		return multa;
	}

	public void setMulta(String multa) {
		this.multa = multa;
	}
}
