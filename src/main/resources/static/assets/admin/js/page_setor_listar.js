$(document).ready(function() {

	$("#reset_filtro").click(function() {
		customReset();
	});

	function customReset() {
		$("#filtro_codigo").val("");
		$("#filtro_nome").val("");
		$("#filtro_secretaria").val(0);

	}

});
