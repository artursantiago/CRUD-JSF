document.addEventListener("DOMContentLoaded", function(){ 
	//Variáveis Globais - HTML
	var campoMatricula = document.querySelector(".matriculaInput");
	var campoAno = document.querySelector(".anoInput");
	var campoNome = document.querySelector(".nomeInput");
	var confirmaBtn = document.querySelector(".confirmaBtn");
	var cancelaBtn = document.querySelector(".cancelaBtn");

	//Funções
	function validaMatricula(e){
	    var tecla = String.fromCharCode(e.which);
	    var regex = /[0-9]/;
	    if(! (tecla.match(regex)) || campoMatricula.value.length >= 15){
	        return false;
	    }
	}


	function validaAno(e){
	    var tecla = String.fromCharCode(e.which);
	    var regex = /[0-9]/;
	    if(! (tecla.match(regex)) || campoAno.value.length >= 4){
	        return false;
	    }
	}

	function validaNome(e) {
	    var tecla = String.fromCharCode(e.which);
	    var regex = /[a-zA-Zç ]/;
	    if(! (tecla.match(regex)) || campoNome.value.length >= 100){
	        return false;
	    }
	}

	function verificaDadosVazios(event) {
	    campoNome.value = campoNome.value.trim(); // Remove espaços desnecessários
	}

	//Rotina Principal
	campoAno.onkeypress = validaAno;
	campoMatricula.onkeypress = validaMatricula;
	campoNome.onkeypress = validaNome;
	confirmaBtn.addEventListener("click", verificaDadosVazios);

});
