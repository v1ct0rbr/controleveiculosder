$(document)
		.ready(
				function() {

					$('#data_all_itens')
							.DataTable(
									{
										"language" : {
											"url" : deros_context
													+ "assets/geral/locale/Portuguese-Brasil.json"
										},
										"paging" : true,
										"lengthChange" : false,
										"searching" : true,
										"ordering" : true,
										"info" : true,
										"autoWidth" : true

									});

					$('#form_page').validator();
					$("#submit_button").click(function(e) {
						e.preventDefault();
						submeterFormulario("#form_page", "Salvar item?");
					});

					$("#clear_form").click(function() {
						window.location.replace(curr_url);
					});
				});