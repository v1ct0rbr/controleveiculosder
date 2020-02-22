$(document)
		.ready(
				function() {

					$("#submit_button").on('click', function(e) {
						e.preventDefault();
						submeterFormulario("#form_page", "Salvar item?");
					});

					$("#clear_form").on('click', function() {
						window.location.replace(curr_url + '/novo');
					});

					$("#secretaria").change(function() {
						recarregarSetores($(this).val());
					});

					$("#secretaria").change();

					function recarregarSetores(secretariaId) {
						$
								.get(
										deros_context
												+ "menu/rest/setores/listar", {
											term : secretariaId
										})
								.done(
										function(dataObj) {
											$("#setor").html("");
											$("#setor")
													.html(
															'<option value="0" selected="selected">'
																	+ ($(
																			'#secretaria')
																			.val() > 0 ? 'selecione abaixo'
																			: 'Selecione a Secretaria')
																	+ '</option>');
											if (dataObj) {
												try {
													var i = 0;
													$
															.map(
																	dataObj,
																	function(
																			item) {
																		$(
																				"#setor")
																				.append(
																						'<option value="'
																								+ item.id
																								+ '">'
																								+ item.descricao
																								+ '</option>');
																	});
												} catch (e) {
													console.log(e);
													return false;
												} finally {
													if ($("#setor_id").val() > 0) {
														$("#setor").val(
																$("#setor_id")
																		.val());
													} else {
														$("#setor").val(0);
													}
												}
											}
										});
					}

				});
