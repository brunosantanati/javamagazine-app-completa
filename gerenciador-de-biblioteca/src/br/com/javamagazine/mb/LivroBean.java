package br.com.javamagazine.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.javamagazine.dao.AutorDao;
import br.com.javamagazine.dao.CategoriaDao;
import br.com.javamagazine.dao.EditoraDao;
import br.com.javamagazine.dao.LivroDao;
import br.com.javamagazine.entidades.Autor;
import br.com.javamagazine.entidades.Categoria;
import br.com.javamagazine.entidades.Editora;
import br.com.javamagazine.entidades.Livro;
import br.com.javamagazine.interceptadores.Transacional;

@Named
@SessionScoped
public class LivroBean implements Serializable {

	private static final long serialVersionUID = 1L;	
	@Inject
	private LivroDao livroDao;
	private Livro livro = new Livro();
	private List<Livro> livros;
	@Inject
	private CategoriaDao categoriaDao;
	private Integer idCategoria;
	@Inject
	private EditoraDao editoraDao;
	private Integer idEditora;
	@Inject
	private AutorDao autorDao;
	private Integer idAutor;
	private List<Autor> autores = new ArrayList<Autor>();

	@Transacional
	public void salvar(){
		
		Categoria categoria = categoriaDao.pesquisarPorId(idCategoria);
		livro.setCategoria(categoria);
		
		Editora editora = editoraDao.pesquisarPorId(idEditora);
		livro.setEditora(editora);
		
		livro.setAutores(autores);
		
		if(livro.getId() == null){
			livroDao.inserir(livro);
		}else{
			livroDao.atualizar(livro);
		}
		
		livro = new Livro();
		idCategoria = null;
		idEditora = null;
		autores = new ArrayList<Autor>();
		livros = livroDao.listarLivros();
	}
	
	public void alterar(Livro livro){
		this.livro = livro;
		idCategoria = livro.getCategoria().getId();
		idEditora = livro.getCategoria().getId();
		autores = livro.getAutores();
	}
	
	@Transacional
	public void remover(Livro livro){
		livroDao.remover(livro);
		livros = livroDao.listarLivros();
	}
	
	@Transacional
	public List<Livro> getLivros(){
		
		if(livros == null){
			livros = livroDao.listarLivros();
		}
		return livros;
	}

	public void adicionarAutor(){
		Autor autor = autorDao.pesquisarPorId(idAutor);
		autores.add(autor);
	}
	
	public void removerAutor(Autor autor){
		Autor autorASerExcluido = null;
		
		for(Autor autorDaVez : autores){
			if(autor.getId() == autorDaVez.getId())
				autorASerExcluido = autorDaVez;
		}
		
		autores.remove(autorASerExcluido);
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	public List<Autor> getAutores() {
		return autores;
	}

	public void setAutores(List<Autor> autores) {
		this.autores = autores;
	}
	
	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	
	public Integer getIdEditora() {
		return idEditora;
	}

	public void setIdEditora(Integer idEditora) {
		this.idEditora = idEditora;
	}

	public Integer getIdAutor() {
		return idAutor;
	}

	public void setIdAutor(Integer idAutor) {
		this.idAutor = idAutor;
	}
}
