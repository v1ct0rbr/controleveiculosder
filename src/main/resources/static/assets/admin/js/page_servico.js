$(document)
		.ready(
				function() {

					$("#recarregar_niveis")
							.click(
									function() {
										// recarregarNiveis();
										var nivelId = $("#nivel_id").val();
										recarregarItensCombo(
												'/comum/utils/servico/niveisprioridade',
												"#nivelPrioridade",
												(nivelId != null && nivelId > 0 ? nivelId
														: "1"), false,
												"#overlay-nivel");

									});
					$("#recarregar_status").click(
							function() {
								recarregarItensCombo(
										'/comum/utils/servico/status',
										"#statusAtual", $("#statusatual_id")
												.val(), false,
										"#overlay-status");

							});

					$("#recarregar_setores").click(
							function() {
								recarregarItensCombo('/comum/utils/setores',
										"#setorServico", $("#setor_id").val(),
										true, "#overlay-setor");
							});
					$("#recarregar_tiposproblemas")
							.on(
									'click',
									function() {

										recarregarItensCombo(
												'/comum/utils/servico/tipoproblema',
												"#tipo_problema", $(
														"#tipo_problema_id")
														.val(), true,
												"#overlay-problema",
												"Tipo de Problema")
										setTimeout(
												'$("#tipo_problema").change();',
												800);

									});

					$("#recarregar_categoriaservico")
							.on(
									'click',
									function() {
										// recarregartiposServicos();
										recarregarItensCombo(
												'/comum/utils/servico/categoriaservico',
												"#CategoriaServico",
												$("#categoria_servico_id")
														.val(), true,
												"#overlay-tiposervico");
										setTimeout(
												"$('#form_page').validator('update')",
												800);
									});

					$("#recarregar_tiposservicos").on(
							'click',
							function() {
								// recarregartiposServicos();
								recarregarItensCombo(
										'/comum/utils/servico/tiposservico',
										"#tipoServico", $("#tiposervico_id")
												.val(), true,
										"#overlay-tiposervico");
								setTimeout(
										"$('#form_page').validator('update')",
										800);
							});

					$("#tipo_problema").change();

					$("#CategoriaServico").change();

					createDatatable("#data_saidas", 5, 0, "desc");
					createDatatable("#data_movimentacoes", 5, 2, "desc");
					createDatatable("#data_servicos", 5, 0, "desc");
					createDatatable("#data_mensagens", 5, 0, "desc");

					$('#form_page').validator();
					$("#submit_button").click(function(e) {
						e.preventDefault();
						$("#form_page").validator('update');
						submeterFormulario("#form_page", "Salvar serviço?");
					});

					registerSummernote('#descricao', '', 1000, function(max) {
						$('#maxContentPost1').text(max)
					});
					registerSummernote('#atividadesRealizadas', '', 512,
							function(max) {
								$('#maxContentPost2').text(max)
							});

					function formatRepo(repo) {

						var markup = "";
						if (repo.text) {
							markup += "<span>" + repo.text + "</span>";
						}
						return markup;
					}

					function formatRepoSelection(repo) {
						return repo.text;
					}

					$(".equipamentos-ajax")
							.select2(
									{
										allowClear : true,
										multiple : false,
										placeholder : '(Digite o tombamento)',
										language : "pt-BR",
										minimumInputLength : 4,
										ajax : {
											url : deros_context
													+ "/operador/equipamentos/listar",
											dataType : 'json',
											type : "GET",
											quietMillis : 50,
											data : function(params) {
												var queryParameters = {
													term : params.term
												}
												return queryParameters;
											},
											processResults : function(data) {
												return {
													results : $
															.map(
																	data,
																	function(
																			item) {
																		return {
																			text : item.tombamento
																					+ (item.tombamento_estado ? '/'
																							+ item.tombamento_estado
																							: '')
																					+ "-"
																					+ item.descricao,
																			id : item.id,
																			name : item.idSetor,
																			na_garantia : item.emPeriodoDeGarantia

																		}
																	})
												};
											},
											createSearchChoice : function(term,
													data) {
												if ($(data)
														.filter(
																function() {
																	return this.text
																			.localeCompare(term) === 0;
																}).length === 0) {
													// call $.post() to add
													// this term to the
													// server, receive back
													// id
													// return {id:id,
													// text:term}
													// or detect this
													// shiftiness and do it
													// below in the
													// on-change

													return {
														id : -1 + '/' + term,
														text : $.trim(term)
																+ ' (new tag)',
														isNew : true
													};
												}
											}
										},
										escapeMarkup : function(markup) {
											return markup;
										},
										templateResult : formatRepo,
										templateSelection : formatRepoSelection,
										minimumResultsForSearch : Infinity,

									});

					$(".funcionarios-ajax")
							.select2(
									{
										allowClear : true,
										multiple : false,
										placeholder : '(Digite o email do Cliente)',
										language : "pt-BR",
										minimumInputLength : 4,
										ajax : {
											url : deros_context
													+ "/operador/clientes/listar",
											dataType : 'json',
											type : "GET",
											quietMillis : 50,
											data : function(params) {
												var queryParameters = {
													term : params.term
												}
												return queryParameters;
											},
											processResults : function(data) {
												return {
													results : $
															.map(
																	data,
																	function(
																			item) {
																		return {
																			text : item.nome
																					+ (item.email != null ? "-"
																							+ item.email
																							: ""),
																			id : item.id

																		}
																	})
												};
											},
											createSearchChoice : function(term,
													data) {
												if ($(data)
														.filter(
																function() {
																	return this.text
																			.localeCompare(term) === 0;
																}).length === 0) {
													return {
														id : -1 + '/' + term,
														text : $.trim(term)
																+ ' (new tag)',
														isNew : true
													};
												}
											}
										},
										escapeMarkup : function(markup) {
											return markup;
										},
										templateResult : formatRepo,
										templateSelection : formatRepoSelection,
										minimumResultsForSearch : Infinity,

									});

					$("#clear_form").click(
							function() {
								window.location.replace(deros_context
										+ "/operador/servico/ordem/novo");
							});

					$('.equipamentos-ajax')
							.change(
									function() {
										var idSetor = $(".equipamentos-ajax")
												.select2('data')[0].name;

										$
												.getJSON(
														deros_context
																+ "/operador/equipamentos/getservicoaberto?term="
																+ $(this).val(),
														function(data) {
															if (data) {

																bootbox
																		.confirm({
																			size : "small",
																			message : "Existe uma ordem de serviço não finalizada para o Equipamento selecionado:<br /> ("
																					+ "<b>"
																					+ data.id
																					+ "</b>"
																					+ (data.descricao != null
																							&& $
																									.trim(data.descricao) != "" ? " - "
																							+ data.descricao
																							: "")
																					+ ") <br /> Deseja acessar o serviço?",
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
																			callback : function(
																					result) {
																				if (result) {
																					window.location
																							.replace(deros_context
																									+ "/operador/servico/ordem/"
																									+ data.id);
																				}

																			}
																		});
															}
														});

										var na_garantia = $(
												".equipamentos-ajax").select2(
												'data')[0].na_garantia;
										if (na_garantia == "true") {
											if ($("#obs_garantia").hasClass(
													'notvisible'))
												$("#obs_garantia").removeClass(
														'notvisible');

										} else {
											if (!$("#obs_garantia").hasClass(
													'notvisible')) {
												$("#obs_garantia").addClass(
														'notvisible');
											}
										}

										if (idSetor > 0) {
											$("#setorServico").val(idSetor);
											$("idSetorEquipamento")
													.val(idSetor);
										}
									});

					$(".funcionarios-ajax").change(function() {
						var selectedFunc = $(this).val();
						if (selectedFunc == null || selectedFunc == "") {
							$("#notificacaoCliente").prop("checked", false);
							$("#notificacaoCliente").prop("disabled", true);
						} else {
							$("#notificacaoCliente").prop("disabled", false);
						}
					});

					function recarregarProblemas(idTipo) {
						$("#overlay-problema").css('display', "block");
						$
								.get(
										deros_context
												+ "/operador/rest/problemas/listar",
										{
											term : idTipo
										})
								.done(
										function(dataObj) {
											$("#problema").html("");
											if (dataObj) {
												try {
													var i = 0;
													$
															.map(
																	dataObj,
																	function(
																			item) {
																		$(
																				"#problema")
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

													if ($("#problema_id").val() > 0) {
														$("#problema")
																.val(
																		$(
																				"#problema_id")
																				.val());
													}
													$("#overlay-problema").css(
															'display', "none");
												}
											}
											if (dataObj.length == 0) {
												$("#problema")
														.prepend(
																'<option value="0" selected="selected">Primeiro selecione uma categoria</option>');
											} else {
												$("#problema")
														.prepend(
																'<option value="0">Selecione o problema</option>');
											}

										});
					}

					function recarregarTipoServico(idCategoria) {
						$("#overlay-tiposervico").css('display', "block");
						$
								.get(
										deros_context
												+ "/comum/utils/servico/tipos_servico_by_categoria",
										{
											term : idCategoria
										})
								.done(
										function(dataObj) {
											$("#tipoServico").html("");
											if (dataObj) {
												try {
													var i = 0;
													$
															.map(
																	dataObj,
																	function(
																			item) {
																		$(
																				"#tipoServico")
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

													if ($("#tiposervico_id")
															.val() > 0) {
														$("#tipoServico")
																.val(
																		$(
																				"#tiposervico_id")
																				.val());
													}
													$("#overlay-tiposervico")
															.css('display',
																	"none");
												}
											}
											if (dataObj.length == 0) {
												$("#tipoServico")
														.prepend(
																'<option value="0" selected="selected">Primeiro selecione uma categoria</option>');
											} else {
												$("#tipoServico")
														.prepend(
																'<option value="0">Selecione o tipo de Serviço</option>');
											}

										});
					}

				});