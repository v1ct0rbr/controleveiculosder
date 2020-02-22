$(document)
		.ready(
				function() {

					$("#filtro_secretaria").change(function() {
						recarregarSetores($(this).val());
					});

					$("#filtro_secretaria").change();

					$("#reset_filtro").click(function() {
						customReset();
					});

					function recarregarSetores(idTipo) {

						$
								.get(
										deros_context
												+ "menu/rest/setores/listar", {
											term : idTipo
										})
								.done(
										function(dataObj) {
											$("#filtro_setor").html("");
											$("#filtro_setor")
													.html(
															'<option value="0" selected="selected">'
																	+ ($(
																			'#filtro_secretaria')
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
																				"#filtro_setor")
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
														$("#filtro_setor").val(
																$("#setor_id")
																		.val());
													} else {
														$("#filtro_setor").val(
																0);
													}
												}
											}
										});
					}

					function customReset() {
						$("#filtro_nome").val("");
						$("#filtro_secretaria").val("0");
						$("#filtro_setor").val("0");
					}

				});
