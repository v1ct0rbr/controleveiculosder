$(document).ready(function() {
	$("#detalhes").maxlength({
		max : 200
	});
	$('#form_page').validator();
	$("#submeter_mensagem").click(function(e) {
		e.preventDefault();
		submeterFormulario("#form_page", "Enviar Notificação?");

	});
});
