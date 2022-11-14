package tk.leooresende.redesocialmensagem.infra.dto;

import java.util.List;

public class UsuarioDto {
	private Integer id;
	private String username;
	private String nomeCompleto;
	private Integer publicacoes;
	private Integer seguidores;
	private Integer seguindo;
	private List<String> tipoDeUsuario;
	private Boolean ehSeguidoPeloUsuarioAutenticado;
	private Boolean segueOUsuarioAutenticado;
	private String dataDeRegistro;

	public UsuarioDto() {
	}

	public String getDataDeRegistro() {
		return dataDeRegistro;
	}

	public void setDataDeRegistro(String dataDeRegistro) {
		this.dataDeRegistro = dataDeRegistro;
	}

	public Integer getPublicacoes() {
		return publicacoes;
	}

	public void setPublicacoes(Integer publicacoes) {
		this.publicacoes = publicacoes;
	}

	public Integer getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(Integer seguidores) {
		this.seguidores = seguidores;
	}

	public Integer getSeguindo() {
		return seguindo;
	}

	public void setSeguindo(Integer seguindo) {
		this.seguindo = seguindo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public List<String> getTipoDeUsuario() {
		return tipoDeUsuario;
	}

	public void setTipoDeUsuario(List<String> tipoDeUsuario) {
		this.tipoDeUsuario = tipoDeUsuario;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEhSeguidoPeloUsuarioAutenticado() {
		return ehSeguidoPeloUsuarioAutenticado;
	}

	public Boolean getEhSeguidoPeloUsuarioAutenticado() {
		return ehSeguidoPeloUsuarioAutenticado;
	}

	public void setEhSeguidoPeloUsuarioAutenticado(Boolean ehSeguidoPeloUsuarioAutenticado) {
		this.ehSeguidoPeloUsuarioAutenticado = ehSeguidoPeloUsuarioAutenticado;
	}

	public Boolean getSegueOUsuarioAutenticado() {
		return segueOUsuarioAutenticado;
	}

	public void setSegueOUsuarioAutenticado(Boolean segueOUsuarioAutenticado) {
		this.segueOUsuarioAutenticado = segueOUsuarioAutenticado;
	}

	public void setEhSeguidoPeloUsuarioAutenticado(boolean ehSeguidoPeloUsuarioAutenticado) {
		this.ehSeguidoPeloUsuarioAutenticado = ehSeguidoPeloUsuarioAutenticado;
	}
}

