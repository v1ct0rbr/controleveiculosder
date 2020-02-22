$(document).ready(function() {
	$("#frm_clear").click(function() {
		window.location.replace($("#form_page").attr('data-url'));
	});
	$('#form_page').validator();

	$("#submit_button").click(function(e) {
		e.preventDefault();
		var validator = $("#form_page").data("bs.validator");
		validator.validate();
		if (!validator.hasErrors()) {
			bootbox.confirm({
				size : "small",
				message : "Trocar a senha?",
				buttons : {
					confirm : {
						label : 'Sim',
						className : 'btn-success submitable'
					},
					cancel : {
						label : 'Não',
						className : 'btn-danger'
					}
				},
				callback : function(result) {
					if (result) {
						$("#form_page").submit();
					}

				}
			})

		}
	});
});