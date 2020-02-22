$(document)
		.ready(
				function() {
					$("#veiculo_placa").mask("SSS-9999");

					$("#filtro_secretaria").change(function() {
						if ($(this).val() != 0)
							recarregarSetores($(this).val());
						else {
							zerarFiltroSetor();
						}
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
											zerarFiltroSetor();
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
																								+ item.nome
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
						$("#filtro_adesivo").val("");
						$("#filtro_placa").val("");
						$("#filtro_descricao").val("");
						$("#filtro_funcionario").val("");
						$("#filtro_secretaria").val("0");
						$("#filtro_setor").val("0");
						$("#filtro_tipoveiculo").val("0")

					}

					function zerarFiltroSetor() {
						$("#filtro_setor")
								.html(
										'<option value="0" selected="selected">'
												+ ($('#filtro_secretaria')
														.val() > 0 ? 'selecione abaixo'
														: 'Selecione a Secretaria primeiro')
												+ '</option>');
					}

				});
