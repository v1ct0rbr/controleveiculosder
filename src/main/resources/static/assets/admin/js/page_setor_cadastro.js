$(document).ready(function() {
	$('#ramal').inputFilter(function(value) {
		return /^\d*$/.test(value);
	});

	$("#submit_button").on('click', function(e) {
		e.preventDefault();
		submeterFormulario("#form_page", "Salvar item?");
	});

	$("#clear_form").on('click', function() {
		window.location.replace(curr_url + '/novo');
	});

});