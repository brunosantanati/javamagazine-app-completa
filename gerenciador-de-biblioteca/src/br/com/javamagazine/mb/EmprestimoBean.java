package br.com.javamagazine.mb;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.EmprestimoDao;
import br.com.javamagazine.dao.LeitorDao;
import br.com.javamagazine.dao.LivroDao;
import br.com.javamagazine.entidades.Emprestimo;
import br.com.javamagazine.entidades.FuncionarioBiblioteca;
import br.com.javamagazine.entidades.Leitor;
import br.com.javamagazine.entidades.Livro;
import br.com.javamagazine.interceptadores.Transacional;
import br.com.javamagazine.util.MensagemUtil;

@Named
@RequestScoped
public class EmprestimoBean {
	
	@Inject
	private EmprestimoDao emprestimoDao;
	private Emprestimo emprestimo = new Emprestimo();
	@Inject
	private LivroDao livroDao;
	private Integer idLivro;
	private List<Livro> livrosDisponiveis;
	@Inject
	private LeitorDao leitorDao;
	private Integer idLeitor;
	@Inject
	private UsuarioLogadoBean usuarioLogadoBean;
	String dataEmprestimoFormatada;
	String dataPrevistaFormatada;

	EmprestimoBean(){
		emprestimo.setDataEmprestimo(LocalDate.now());
		emprestimo.setDataPrevista(LocalDate.now().plusDays(7));
		
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		dataEmprestimoFormatada = emprestimo.getDataEmprestimo().format(formatador);
		dataPrevistaFormatada = emprestimo.getDataPrevista().format(formatador);
	}

	@Transacional
	public void realizarEmprestimo(){
		Livro livro = livroDao.pesquisarPorId(idLivro);
		Leitor leitor = leitorDao.pesquisarPorId(idLeitor);
		FuncionarioBiblioteca funcionarioBiblioteca = usuarioLogadoBean.getUsuario().getFuncionarioBiblioteca();
		
		emprestimo.setLivro(livro);
		livro.getEmprestimos().add(emprestimo);
		
		emprestimo.setLeitor(leitor);
		emprestimo.setFuncionarioBiblioteca(funcionarioBiblioteca);
		
		emprestimoDao.inserir(emprestimo);
		
		livrosDisponiveis = livroDao.listarLivrosDisponiveisParaEmprestimo();
		
		MensagemUtil.addMensagemInformativa("Sucesso - ", "Empréstimo realizado com sucesso!");
	}

	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	public void setEmprestimo(Emprestimo emprestimo) {
		this.emprestimo = emprestimo;
	}

	public Integer getIdLivro() {
		return idLivro;
	}

	public void setIdLivro(Integer idLivro) {
		this.idLivro = idLivro;
	}

	@Transacional
	public List<Livro> getLivrosDisponiveis() {
		if(livrosDisponiveis == null){
			livrosDisponiveis = livroDao.listarLivrosDisponiveisParaEmprestimo();
		}
		
		return livrosDisponiveis;
	}

	public Integer getIdLeitor() {
		return idLeitor;
	}

	public void setIdLeitor(Integer idLeitor) {
		this.idLeitor = idLeitor;
	}
	
	public String getDataEmprestimoFormatada() {
		return dataEmprestimoFormatada;
	}

	public String getDataPrevistaFormatada() {
		return dataPrevistaFormatada;
	}

}
