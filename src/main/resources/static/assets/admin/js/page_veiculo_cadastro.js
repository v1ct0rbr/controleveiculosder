$(document)
		.ready(
				function() {

					$("#recarrega_secretarias").on('click', function() {
						recarregaSecretarias();
					});

					$("#recarrega_setores").on('click', function() {

						recarregarSetores($("#secretaria").val());
					});

					$("#secretaria").change(function() {
						if ($(this).val() != 0)
							recarregarSetores($(this).val());
						else {
							zerarFiltroSetor();
						}
					});

					$("#secretaria").change();

					$('#setor_telefone').inputFilter(function(value) {
						return /^\d*$/.test(value);
					});
					$("#placa").mask("SSS-9999");

					keyUpToUpper("#placa");

					$("#submit_button").on('click', function(e) {
						e.preventDefault();
						submeterFormulario("#form_page", "Salvar item?");
					});
					$("#submit_form_setor")
							.on(
									'click',
									function(e) {
										e.preventDefault();

										var data = {
											nome : $("#setor_descricao").val(),
											telefone : $("#setor_telefone")
													.val(),
											secretariaId : $(
													"#setor_secretaria").val()
										};

										$("#submit_form_setor").prop(
												"disabled", true);
										$
												.ajax({
													type : "POST",
													contentType : "application/json",
													traditional : true,
													url : deros_context
															+ "menu/rest/setores/save",
													data : JSON.stringify(data),
													// dataType : 'json',
													success : function(dados) {

														$("#submit_form_setor")
																.prop(
																		"disabled",
																		false);
														if (dados
																.indexOf('sucesso') >= 0) {
															alert('Novo setor cadastrado com sucesso');
															$(
																	"#setor_descricao")
																	.val("");
															$("#setor_telefone")
																	.val("");
															$(
																	"#setor_secretaria")
																	.val(0);
															$('#modal_setor')
																	.modal(
																			'hide');
															recarregarSetores($(
																	"#secretaria")
																	.val());

														}

													},
													error : function(e, status,
															error) {

														alert("Erro: "
																+ e.responseText);
														$("#submit_form_setor")
																.prop(
																		"disabled",
																		false);
													}
												});
									});

					$("#clear_form").on('click', function() {
						window.location.replace(curr_url + '/novo');
					});

					// $('.fixed-action-btn').floatingActionButton();
					$("#nome").on('keypress', function(e) {
						if (e.which == 13) {
							e.preventDefault();
							$(this).blur();
						}
					})

					$("#nome")
							.on(
									'blur',
									function() {

										if ($.trim($(this).val()).length > 3)
											$
													.ajax(
															{
																url : deros_context
																		+ 'menu/rest/funcionarios/listar?nome='
																		+ encodeURIComponent($(
																				this)
																				.val())
																		+ '&id='
																		+ encodeURIComponent($(
																				"#funcionarioId")
																				.val())
															})
													.then(
															function(data) {
																count = data.length;

																if (count > 0) {
																	$(
																			"#body_funcionarios")
																			.html(
																					"");
																	$
																			.each(
																					data,
																					function(
																							i,
																							value) {
																						$(
																								"#body_funcionarios")
																								.append(
																										"<tr><td>"
																												+ '<button id="seleciona_funcionario_'
																												+ i
																												+ '" data-dismiss="modal" class="seleciona_funcionario btn btn-default link" value="'
																												+ value.id
																												+ '">Selecione</button></td><td>'
																												+ value.nome
																												+ '</td><td>'
																												+ value.setorNome
																												+ "</td><td>"
																												+ value.secretariaNome
																												+ "</td></tr>");
																						if (i + 1 === count) {
																							carregarFuncionario();
																							 document.getElementById("seleciona_funcionario_0").focus();
																						
																						}

																					});

																	$(
																			"#funcionarios")
																			.modal();
																}

															});
									});

					$("#funcionarios").on('hidden.bs.modal', function() {
						$("#email").focus();
					});

					function recarregaSecretarias() {

						$("#secretaria").html(
								'<option value="">Selecione</option>');
						$
								.get(
										deros_context
												+ "menu/rest/secretarias/listar")
								.done(
										function(dataObj) {

											if (dataObj) {

												try {
													$
															.map(
																	dataObj,
																	function(
																			item) {
																		$(
																				"#secretaria")
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

													if ($("#secretaria_id")
															.val() > 0) {
														$("#secretaria")
																.val(
																		$(
																				"#secretaria_id")
																				.val());
													} else {
														$("#secretaria")
																.val("");
													}

												}
											}
										});
					}

					function recarregarSetores(idTipo) {

						$.get(deros_context + "menu/rest/setores/listar", {
							term : idTipo
						}).done(
								function(dataObj) {

									if (dataObj) {
										zerarFiltroSetor();
										try {
											var i = 0;
											$.map(dataObj, function(item) {

												$("#setor").append(
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
												$("#setor").val(
														$("#setor_id").val());
											} else {
												$("#setor").val(0);
											}
										}
									}
								});
					}

					function carregarFuncionario() {
						$(".seleciona_funcionario")
								.on(
										'click',
										function() {

											$("#funcionarioId").val(
													$(this).val());

											$
													.ajax(
															{
																url : deros_context
																		+ 'menu/rest/funcionarios/getbyid?id='
																		+ encodeURIComponent($(
																				"#funcionarioId")
																				.val())
															})
													.then(
															function(data) {

																if (data) {
																	$(
																			"#funcionarioId")
																			.val(
																					data.id);
																	$(
																			"#funcionario_id")
																			.val(
																					data.id);

																	$("#nome")
																			.val(
																					data.nome);
																	$("#email")
																			.val(
																					data.email);
																	$(
																			"#telefone")
																			.val(
																					data.celular);

																	$(
																			"#setor_id")
																			.val(
																					data.setorId);
																	$(
																			"#secretaria")
																			.val(
																					data.secretariaId);

																	$(
																			"#secretaria")
																			.change();

																}

															});
										});
					}
					function zerarFiltroSetor() {
						$("#setor")
								.html(
										'<option value="0" selected="selected">'
												+ ($('#secretaria').val() > 0 ? 'selecione abaixo'
														: 'Selecione a Secretaria primeiro')
												+ '</option>');
					}

				});
